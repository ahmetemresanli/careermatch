package com.ahmetemresanli.backend.dto.response;
import com.ahmetemresanli.backend.enums.LanguageProficiency;
public record LanguageResponse(Long id,String name,LanguageProficiency proficiency) { }
