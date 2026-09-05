package com.ahmetemresanli.backend.repository;
import com.ahmetemresanli.backend.entity.Project; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ProjectRepository extends JpaRepository<Project,Long> { List<Project> findByCandidateProfileIdOrderByCreatedAtDesc(Long id); }
