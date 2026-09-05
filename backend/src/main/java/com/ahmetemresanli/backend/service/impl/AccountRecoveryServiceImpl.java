package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.dto.response.OperationResponse;
import com.ahmetemresanli.backend.entity.EmailVerificationToken;
import com.ahmetemresanli.backend.entity.PasswordResetToken;
import com.ahmetemresanli.backend.entity.User;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.EmailVerificationTokenRepository;
import com.ahmetemresanli.backend.repository.PasswordResetTokenRepository;
import com.ahmetemresanli.backend.repository.UserRepository;
import com.ahmetemresanli.backend.security.SecureTokenGenerator;
import com.ahmetemresanli.backend.service.IAccountRecoveryService;
import com.ahmetemresanli.backend.service.IMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import com.ahmetemresanli.backend.enums.EmailVerificationPurpose;
import com.ahmetemresanli.backend.service.IAuditLogService;

@Service
public class AccountRecoveryServiceImpl implements IAccountRecoveryService {
    private static final String GENERIC_FORGOT_MESSAGE = "If the address belongs to an account, password reset instructions have been sent";
    private final UserRepository users;
    private final PasswordResetTokenRepository resetTokens;
    private final EmailVerificationTokenRepository verificationTokens;
    private final PasswordEncoder passwordEncoder;
    private final IMailService mailService;
    private final String publicBaseUrl;
    private final boolean exposeTokens;
    private final IAuditLogService auditLogService;

    public AccountRecoveryServiceImpl(UserRepository users, PasswordResetTokenRepository resetTokens,
                                      EmailVerificationTokenRepository verificationTokens, PasswordEncoder passwordEncoder,
                                      IMailService mailService, @Value("${app.public-base-url:http://localhost:8080}") String publicBaseUrl,
                                      @Value("${app.tokens.expose-in-response:false}") boolean exposeTokens,
                                      IAuditLogService auditLogService) {
        this.users = users;
        this.resetTokens = resetTokens;
        this.verificationTokens = verificationTokens;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.publicBaseUrl = publicBaseUrl;
        this.exposeTokens = exposeTokens;
        this.auditLogService = auditLogService;
    }

    @Override
    @Transactional
    public OperationResponse forgotPassword(String email) {
        User user = users.findByEmailIgnoreCase(email.trim()).orElse(null);
        if (user == null) {
            user = users.findByRecoveryEmailIgnoreCase(email.trim())
                    .filter(User::isRecoveryEmailVerified).orElse(null);
        }
        if (user == null || !user.isActive()) return new OperationResponse(GENERIC_FORGOT_MESSAGE, null);
        String raw = SecureTokenGenerator.generate();
        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setTokenHash(SecureTokenGenerator.hash(raw));
        token.setExpiresAt(LocalDateTime.now().plusHours(1));
        resetTokens.save(token);
        auditLogService.record(token.getUser().getId(), "PASSWORD_RESET_REQUESTED", "User", token.getUser().getId(), "reset instructions issued");
        mailService.send(email.trim().toLowerCase(), "CareerMatch password reset",
                "Reset your password: " + publicBaseUrl + "/api/account-recovery/reset?token=" + raw);
        return new OperationResponse(GENERIC_FORGOT_MESSAGE, exposeTokens ? raw : null);
    }

    @Override
    @Transactional
    public OperationResponse issueRecoveryEmailVerification(Long userId) {
        User user = users.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getRecoveryEmail() == null || user.getRecoveryEmail().isBlank()) {
            throw new BusinessException("Recovery email is not configured");
        }
        if (user.isRecoveryEmailVerified()) return new OperationResponse("Recovery email is already verified", null);
        String raw = SecureTokenGenerator.generate();
        EmailVerificationToken token = new EmailVerificationToken(); token.setUser(user);
        token.setPurpose(EmailVerificationPurpose.RECOVERY); token.setTokenHash(SecureTokenGenerator.hash(raw));
        token.setExpiresAt(LocalDateTime.now().plusHours(24)); verificationTokens.save(token);
        mailService.send(user.getRecoveryEmail(), "Verify your CareerMatch recovery email",
                "Verify your recovery email: " + publicBaseUrl + "/api/auth/verify-email?token=" + raw);
        return new OperationResponse("Verification instructions have been sent", exposeTokens ? raw : null);
    }

    @Override
    @Transactional
    public OperationResponse resetPassword(String raw, String newPassword) {
        PasswordResetToken token = resetTokens.findByTokenHash(SecureTokenGenerator.hash(raw))
                .orElseThrow(() -> new BusinessException("Invalid or expired reset token"));
        validateToken(token.getUsedAt(), token.getExpiresAt(), "reset");
        token.getUser().setPasswordHash(passwordEncoder.encode(newPassword));
        token.setUsedAt(LocalDateTime.now());
        users.save(token.getUser());
        resetTokens.save(token);
        auditLogService.record(token.getUser().getId(), "PASSWORD_RESET_COMPLETED", "User", token.getUser().getId(), "password reset completed");
        return new OperationResponse("Password has been reset", null);
    }

    @Override
    @Transactional
    public OperationResponse issueEmailVerification(Long userId) {
        User user = users.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.isEmailVerified()) return new OperationResponse("Email is already verified", null);
        String raw = SecureTokenGenerator.generate();
        EmailVerificationToken token = new EmailVerificationToken();
        token.setUser(user);
        token.setPurpose(EmailVerificationPurpose.PRIMARY);
        token.setTokenHash(SecureTokenGenerator.hash(raw));
        token.setExpiresAt(LocalDateTime.now().plusHours(24));
        verificationTokens.save(token);
        mailService.send(user.getEmail(), "Verify your CareerMatch email",
                "Verify your email: " + publicBaseUrl + "/api/auth/verify-email?token=" + raw);
        return new OperationResponse("Verification instructions have been sent", exposeTokens ? raw : null);
    }

    @Override
    @Transactional
    public OperationResponse verifyEmail(String raw) {
        EmailVerificationToken token = verificationTokens.findByTokenHash(SecureTokenGenerator.hash(raw))
                .orElseThrow(() -> new BusinessException("Invalid or expired verification token"));
        validateToken(token.getUsedAt(), token.getExpiresAt(), "verification");
        if (token.getPurpose() == EmailVerificationPurpose.RECOVERY) token.getUser().setRecoveryEmailVerified(true);
        else token.getUser().setEmailVerified(true);
        token.setUsedAt(LocalDateTime.now());
        users.save(token.getUser());
        verificationTokens.save(token);
        return new OperationResponse("Email has been verified", null);
    }

    private void validateToken(LocalDateTime usedAt, LocalDateTime expiresAt, String kind) {
        if (usedAt != null) throw new BusinessException("This " + kind + " token has already been used");
        if (expiresAt.isBefore(LocalDateTime.now())) throw new BusinessException("This " + kind + " token has expired");
    }
}
