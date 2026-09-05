package com.ahmetemresanli.backend.controller;
import com.ahmetemresanli.backend.dto.response.*; import org.springframework.http.ResponseEntity;
public interface ICompanyDataController { ResponseEntity<CompanyReviewsResponse> reviews(Long id); ResponseEntity<CompanyNewsResponse> news(Long id); }
