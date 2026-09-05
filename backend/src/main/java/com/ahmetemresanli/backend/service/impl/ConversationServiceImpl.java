package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.entity.CandidateProfile;
import com.ahmetemresanli.backend.entity.Company;
import com.ahmetemresanli.backend.entity.Conversation;
import com.ahmetemresanli.backend.exception.BusinessException;
import com.ahmetemresanli.backend.exception.ResourceNotFoundException;
import com.ahmetemresanli.backend.repository.CandidateProfileRepository;
import com.ahmetemresanli.backend.repository.CompanyRepository;
import com.ahmetemresanli.backend.repository.ConversationRepository;
import com.ahmetemresanli.backend.service.IConversationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConversationServiceImpl
        implements IConversationService {

    private final ConversationRepository conversationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final CompanyRepository companyRepository;

    public ConversationServiceImpl(
            ConversationRepository conversationRepository,
            CandidateProfileRepository candidateProfileRepository,
            CompanyRepository companyRepository
    ) {
        this.conversationRepository = conversationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public Conversation createConversation(
            Long candidateProfileId,
            Long companyId
    ) {

        CandidateProfile candidateProfile =
                candidateProfileRepository
                        .findById(candidateProfileId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate profile not found"
                                )
                        );

        Company company =
                companyRepository
                        .findById(companyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found"
                                )
                        );

        if (!company.isActive()) {
            throw new BusinessException(
                    "Cannot create a conversation with an inactive company"
            );
        }

        /*
         * Aynı candidate + company arasında
         * daha önce conversation açılmış mı?
         */
        return conversationRepository
                .findByCandidateProfileIdAndCompanyId(
                        candidateProfileId,
                        companyId
                )
                .map(existingConversation -> {

                    /*
                     * Önceden kapatılmışsa tekrar aktif hale getir.
                     */
                    if (!existingConversation.isActive()) {

                        existingConversation.setActive(true);
                        existingConversation.touch();

                        return conversationRepository.save(
                                existingConversation
                        );
                    }

                    /*
                     * Zaten aktif conversation varsa
                     * yenisini oluşturmuyoruz.
                     */
                    return existingConversation;
                })
                .orElseGet(() -> {

                    Conversation conversation =
                            new Conversation();

                    conversation.setCandidateProfile(
                            candidateProfile
                    );

                    conversation.setCompany(
                            company
                    );

                    conversation.setActive(true);

                    return conversationRepository.save(
                            conversation
                    );
                });
    }

    @Override
    public Conversation getConversationById(
            Long conversationId
    ) {

        return conversationRepository
                .findById(conversationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Conversation not found"
                        )
                );
    }

    @Override
    public List<Conversation>
    getConversationsByCandidate(
            Long candidateProfileId
    ) {

        if (!candidateProfileRepository
                .existsById(candidateProfileId)) {

            throw new ResourceNotFoundException(
                    "Candidate profile not found"
            );
        }

        return conversationRepository
                .findByCandidateProfileIdOrderByUpdatedAtDesc(
                        candidateProfileId
                );
    }

    @Override
    public List<Conversation>
    getConversationsByCompany(
            Long companyId
    ) {

        if (!companyRepository.existsById(companyId)) {

            throw new ResourceNotFoundException(
                    "Company not found"
            );
        }

        return conversationRepository
                .findByCompanyIdOrderByUpdatedAtDesc(
                        companyId
                );
    }

    @Override
    @Transactional
    public Conversation deactivateConversation(
            Long conversationId
    ) {

        Conversation conversation =
                getConversationById(conversationId);

        if (!conversation.isActive()) {
            throw new BusinessException(
                    "Conversation is already inactive"
            );
        }

        conversation.setActive(false);

        return conversationRepository.save(
                conversation
        );
    }
}