package com.ahmetemresanli.backend.repository;
import com.ahmetemresanli.backend.entity.Certificate; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface CertificateRepository extends JpaRepository<Certificate,Long> { List<Certificate> findByCandidateProfileIdOrderByCreatedAtDesc(Long id); }
