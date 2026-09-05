package com.ahmetemresanli.backend.dto.response;
import java.math.BigDecimal; import java.util.List;
public record CompanyReviewsResponse(Long companyId, BigDecimal rating, Integer reviewCount,
                                     List<CompanyReviewResponse> reviews, boolean available) { }
