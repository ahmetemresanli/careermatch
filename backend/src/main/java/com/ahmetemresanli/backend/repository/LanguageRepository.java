package com.ahmetemresanli.backend.repository;
import com.ahmetemresanli.backend.entity.Language; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface LanguageRepository extends JpaRepository<Language,Long> { Optional<Language> findByNameIgnoreCase(String name); List<Language> findByActiveTrueOrderByNameAsc(); }
