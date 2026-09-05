package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.UserSecurityAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserSecurityAnswerRepository extends JpaRepository<UserSecurityAnswer, Long> {
    Optional<UserSecurityAnswer> findByUserIdAndQuestionId(Long userId, Long questionId);
}
