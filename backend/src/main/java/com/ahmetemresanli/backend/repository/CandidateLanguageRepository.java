package com.ahmetemresanli.backend.repository;
import com.ahmetemresanli.backend.entity.CandidateLanguage; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface CandidateLanguageRepository extends JpaRepository<CandidateLanguage,Long> { boolean existsByCandidateProfileIdAndLanguageId(Long c,Long l); List<CandidateLanguage> findByCandidateProfileId(Long id); }
