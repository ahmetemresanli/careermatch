package com.ahmetemresanli.backend.controller;
import com.ahmetemresanli.backend.dto.request.*; import com.ahmetemresanli.backend.dto.response.*; import org.springframework.http.ResponseEntity; import java.util.List;
public interface ICandidateContentController {
 ResponseEntity<ProjectResponse> addProject(Long c,ProjectRequest r); ResponseEntity<List<ProjectResponse>> projects(Long c); ResponseEntity<Void> deleteProject(Long c,Long id);
 ResponseEntity<CertificateResponse> addCertificate(Long c,CertificateRequest r); ResponseEntity<List<CertificateResponse>> certificates(Long c); ResponseEntity<Void> deleteCertificate(Long c,Long id);
 ResponseEntity<LanguageResponse> createLanguage(LanguageRequest r); ResponseEntity<List<LanguageResponse>> languages(); ResponseEntity<LanguageResponse> addLanguage(Long c,CandidateLanguageRequest r); ResponseEntity<List<LanguageResponse>> candidateLanguages(Long c);
 ResponseEntity<FavoriteResponse> favorite(Long c,Long j); ResponseEntity<List<FavoriteResponse>> favorites(Long c); ResponseEntity<Void> unfavorite(Long c,Long j);
}
