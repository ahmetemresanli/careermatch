package com.ahmetemresanli.backend.dto.response;
import java.util.List;
public record CompanyNewsResponse(Long companyId,List<CompanyNewsItemResponse> articles,boolean available) { }
