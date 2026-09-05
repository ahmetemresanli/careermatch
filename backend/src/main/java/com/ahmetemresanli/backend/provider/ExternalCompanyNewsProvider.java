package com.ahmetemresanli.backend.provider;
import com.ahmetemresanli.backend.dto.response.*; import com.ahmetemresanli.backend.entity.Company;
import org.slf4j.*; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component; import org.springframework.web.client.RestClient; import org.springframework.web.util.UriComponentsBuilder;
import java.time.OffsetDateTime; import java.util.*;

@Component
public class ExternalCompanyNewsProvider implements CompanyNewsProvider {
 private static final Logger log=LoggerFactory.getLogger(ExternalCompanyNewsProvider.class); private final boolean enabled;private final String key;private final RestClient client=RestClient.create();
 public ExternalCompanyNewsProvider(@Value("${app.company-data.enabled:false}") boolean enabled,@Value("${app.company-data.news-api-key:}") String key){this.enabled=enabled;this.key=key;}
 @Override @SuppressWarnings("unchecked") public CompanyNewsResponse news(Company company){if(!enabled||key.isBlank())return unavailable(company.getId());try{
  String uri=UriComponentsBuilder.fromUriString("https://newsapi.org/v2/everything").queryParam("q",company.getName()).queryParam("sortBy","publishedAt").queryParam("pageSize",5).queryParam("apiKey",key).build().encode().toUriString();
  Map<String,Object> body=client.get().uri(uri).retrieve().body(Map.class);List<CompanyNewsItemResponse> result=new ArrayList<>();for(Map<String,Object>a:(List<Map<String,Object>>)(body==null?List.of():body.getOrDefault("articles",List.of()))){Map<String,Object>s=(Map<String,Object>)a.getOrDefault("source",Map.of());String date=Objects.toString(a.get("publishedAt"),null);result.add(new CompanyNewsItemResponse(Objects.toString(a.get("title"),null),Objects.toString(s.get("name"),null),Objects.toString(a.get("url"),null),date==null?null:OffsetDateTime.parse(date)));}return new CompanyNewsResponse(company.getId(),result,true);
 }catch(RuntimeException ex){log.warn("Company news provider failed for company {}",company.getId());return unavailable(company.getId());}}
 private CompanyNewsResponse unavailable(Long id){return new CompanyNewsResponse(id,List.of(),false);}
}
