package com.ahmetemresanli.backend.repository;

import com.ahmetemresanli.backend.entity.SecurityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Long> {
    List<SecurityQuestion> findByActiveTrueOrderByIdAsc();
}
