package com.ahmetemresanli.backend.service.impl;
import com.ahmetemresanli.backend.dto.response.*; import com.ahmetemresanli.backend.entity.Company; import com.ahmetemresanli.backend.exception.ResourceNotFoundException; import com.ahmetemresanli.backend.provider.*; import com.ahmetemresanli.backend.repository.CompanyRepository; import com.ahmetemresanli.backend.service.ICompanyDataService;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import java.time.*; import java.util.concurrent.ConcurrentHashMap;

@Service
public class CompanyDataServiceImpl implements ICompanyDataService {
 private record Entry<T>(T value,Instant expires){} private final CompanyRepository companies;private final CompanyReviewProvider reviews;private final CompanyNewsProvider news;private final Duration ttl;private final ConcurrentHashMap<Long,Entry<CompanyReviewsResponse>> reviewCache=new ConcurrentHashMap<>();private final ConcurrentHashMap<Long,Entry<CompanyNewsResponse>> newsCache=new ConcurrentHashMap<>();
 public CompanyDataServiceImpl(CompanyRepository companies,CompanyReviewProvider reviews,CompanyNewsProvider news,@Value("${app.company-data.cache-minutes:30}") long minutes){this.companies=companies;this.reviews=reviews;this.news=news;this.ttl=Duration.ofMinutes(Math.max(1,minutes));}
 @Override public CompanyReviewsResponse reviews(Long id){Entry<CompanyReviewsResponse> e=reviewCache.get(id);if(valid(e))return e.value();CompanyReviewsResponse value=reviews.reviews(company(id));reviewCache.put(id,new Entry<>(value,Instant.now().plus(ttl)));return value;}
 @Override public CompanyNewsResponse news(Long id){Entry<CompanyNewsResponse> e=newsCache.get(id);if(valid(e))return e.value();CompanyNewsResponse value=news.news(company(id));newsCache.put(id,new Entry<>(value,Instant.now().plus(ttl)));return value;}
 private boolean valid(Entry<?> e){return e!=null&&e.expires().isAfter(Instant.now());}private Company company(Long id){return companies.findById(id).orElseThrow(()->new ResourceNotFoundException("Company not found"));}
}
