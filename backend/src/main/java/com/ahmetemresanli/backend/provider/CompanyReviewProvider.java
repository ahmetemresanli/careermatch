package com.ahmetemresanli.backend.provider;
import com.ahmetemresanli.backend.dto.response.CompanyReviewsResponse; import com.ahmetemresanli.backend.entity.Company;
public interface CompanyReviewProvider { CompanyReviewsResponse reviews(Company company); }
