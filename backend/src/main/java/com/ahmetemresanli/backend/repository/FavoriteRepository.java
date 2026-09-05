package com.ahmetemresanli.backend.repository;
import com.ahmetemresanli.backend.entity.Favorite; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface FavoriteRepository extends JpaRepository<Favorite,Long> { boolean existsByCandidateProfileIdAndJobPostingId(Long c,Long j); Optional<Favorite> findByCandidateProfileIdAndJobPostingId(Long c,Long j); List<Favorite> findByCandidateProfileIdOrderByCreatedAtDesc(Long id); }
