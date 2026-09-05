package com.ahmetemresanli.backend.dto.response;

import java.math.BigDecimal;

public record SimilarJobResponse(Long jobPostingId, String title, Long companyId, String companyName, BigDecimal score) { }
