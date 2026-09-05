package com.ahmetemresanli.backend.provider;

import com.ahmetemresanli.backend.dto.response.*; import com.ahmetemresanli.backend.entity.Company;
import org.slf4j.*; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component; import org.springframework.web.client.RestClient;
import java.math.BigDecimal; import java.util.*;

@Component
public class GoogleCompanyReviewProvider implements CompanyReviewProvider {
 private static final Logger log=LoggerFactory.getLogger(GoogleCompanyReviewProvider.class); private final boolean enabled; private final String key; private final RestClient client=RestClient.create();
 public GoogleCompanyReviewProvider(@Value("${app.company-data.enabled:false}") boolean enabled,@Value("${app.company-data.google-api-key:}") String key){this.enabled=enabled;this.key=key;}
 @Override @SuppressWarnings("unchecked") public CompanyReviewsResponse reviews(Company company){
  if(!enabled||key.isBlank())return unavailable(company.getId());
  try{Map<String,Object> body=client.post().uri("https://places.googleapis.com/v1/places:searchText").header("X-Goog-Api-Key",key).header("X-Goog-FieldMask","places.rating,places.userRatingCount,places.reviews").body(Map.of("textQuery",company.getName()+" "+Objects.toString(company.getCity(),""))).retrieve().body(Map.class);
   List<Map<String,Object>> places=body==null?List.of():(List<Map<String,Object>>)body.getOrDefault("places",List.of());if(places.isEmpty())return unavailable(company.getId());Map<String,Object> p=places.getFirst();
   BigDecimal rating=p.get("rating")==null?null:new BigDecimal(p.get("rating").toString());Integer count=p.get("userRatingCount")==null?null:Integer.valueOf(p.get("userRatingCount").toString());List<CompanyReviewResponse> result=new ArrayList<>();
   for(Map<String,Object> r:(List<Map<String,Object>>)p.getOrDefault("reviews",List.of())){Map<String,Object>a=(Map<String,Object>)r.getOrDefault("authorAttribution",Map.of());Map<String,Object>t=(Map<String,Object>)r.getOrDefault("text",Map.of());result.add(new CompanyReviewResponse(Objects.toString(a.get("displayName"),null),r.get("rating")==null?null:Integer.valueOf(r.get("rating").toString()),Objects.toString(t.get("text"),null)));}
   return new CompanyReviewsResponse(company.getId(),rating,count,result,true);
  }catch(RuntimeException ex){log.warn("Company review provider failed for company {}",company.getId());return unavailable(company.getId());}
 }
 private CompanyReviewsResponse unavailable(Long id){return new CompanyReviewsResponse(id,null,null,List.of(),false);}
}
