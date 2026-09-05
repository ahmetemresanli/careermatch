package com.ahmetemresanli.backend.provider;
import com.ahmetemresanli.backend.dto.response.CompanyNewsResponse; import com.ahmetemresanli.backend.entity.Company;
public interface CompanyNewsProvider { CompanyNewsResponse news(Company company); }
