package com.ahmetemresanli.backend.dto.response;
import java.time.OffsetDateTime;
public record CompanyNewsItemResponse(String title,String source,String url,OffsetDateTime publishedAt) { }
