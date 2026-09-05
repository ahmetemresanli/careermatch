package com.ahmetemresanli.backend.service;
import com.ahmetemresanli.backend.dto.response.*;
public interface ICompanyDataService { CompanyReviewsResponse reviews(Long companyId); CompanyNewsResponse news(Long companyId); }
