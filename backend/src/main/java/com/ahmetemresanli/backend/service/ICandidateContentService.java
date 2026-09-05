package com.ahmetemresanli.backend.service;
import com.ahmetemresanli.backend.dto.request.*; import com.ahmetemresanli.backend.dto.response.*; import java.util.List;
public interface ICandidateContentService {
 ProjectResponse addProject(Long candidateId, ProjectRequest r); List<ProjectResponse> projects(Long candidateId); void deleteProject(Long candidateId,Long id);
 CertificateResponse addCertificate(Long candidateId, CertificateRequest r); List<CertificateResponse> certificates(Long candidateId); void deleteCertificate(Long candidateId,Long id);
 LanguageResponse createLanguage(String name); List<LanguageResponse> languages(); LanguageResponse addLanguage(Long candidateId,CandidateLanguageRequest r); List<LanguageResponse> candidateLanguages(Long candidateId);
 FavoriteResponse favorite(Long candidateId,Long jobId); List<FavoriteResponse> favorites(Long candidateId); void unfavorite(Long candidateId,Long jobId);
}
