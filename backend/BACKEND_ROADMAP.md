# CareerMatch Backend Roadmap

Son doğrulama: 2026-09-05

- AŞAMA 0 — Project Setup: COMPLETE
- AŞAMA 1 — User / Account: COMPLETE
- AŞAMA 2 — Candidate Profile: COMPLETE
- AŞAMA 3 — Company: COMPLETE
- AŞAMA 4 — Skills: COMPLETE
- AŞAMA 5 — Job Posting: COMPLETE
- AŞAMA 6 — Job Search: COMPLETE
- AŞAMA 7 — Resume + Application: COMPLETE
- AŞAMA 8 — DTO + Validation + Exception Handling: COMPLETE
- AŞAMA 9 — Spring Security + JWT: COMPLETE
- AŞAMA 10 — Business Rules + Authorization: COMPLETE
- AŞAMA 11 — Matching Engine: COMPLETE
- AŞAMA 12 — Bidirectional Recommendations: COMPLETE
- AŞAMA 13 — Education + Experience: COMPLETE
- AŞAMA 14 — Verification: COMPLETE
- AŞAMA 15 — Reference + Skill Endorsement: COMPLETE
- AŞAMA 16 — Interview: COMPLETE
- AŞAMA 17 — Messaging: COMPLETE
- AŞAMA 18 — Notification: COMPLETE
- AŞAMA 19 — Spring Mail: COMPLETE
- AŞAMA 20 — Account Recovery: COMPLETE
- AŞAMA 21 — Similar Jobs: COMPLETE
- AŞAMA 22 — Admin: COMPLETE
- AŞAMA 23 — Audit Log: COMPLETE
- AŞAMA 24 — Company Reviews + News: COMPLETE

## Teknik kararlar

- Mevcut katmanlı mimari, entity/alan isimleri ve controller/service interface + implementation düzeni korundu.
- Veritabanı için destructive migration eklenmedi; Hibernate `ddl-auto=update` davranışı korundu.
- Kimlik doğrulama stateless JWT, parola ve güvenlik cevabı saklama BCrypt ile uygulanır.
- Reset ve doğrulama tokenlarının yalnızca SHA-256 özeti veritabanında saklanır; tokenlar tek kullanımlı ve sürelidir.
- Development ortamında JWT secret sağlanmazsa geçici anahtar üretilir. Production ortamında `JWT_SECRET` zorunlu işletim yapılandırmasıdır.
- SMTP ve dış şirket verisi sağlayıcıları yapılandırılmadığında graceful degradation uygulanır; uygulama ayağa kalkmaya devam eder.
- Job similarity kalıcı tablo oluşturmadan servis seviyesinde hesaplanır; candidate-job match modelinden ayrı tutulur.
- Experience match puanı, doğrulanmış deneyim varsa yalnızca doğrulanmış kayıtları; yoksa geriye uyumluluk için mevcut deneyimleri kullanır.
- External company review/news cevapları iç DTO'lara dönüştürülür ve kısa süreli belleki cache kullanılır.
- Entegrasyon testi gerçek PostgreSQL verisini etkilememek için H2 PostgreSQL uyumluluk modunda çalışır.

## Kapsam dışı

- AŞAMA 25 — Tests: kullanıcı talebi gereği yeni kapsamlı test paketi bu çalışmanın dışında; mevcut testler çalıştırılır.
- AŞAMA 26 — Docker / Deployment: kullanıcı talebi gereği kapsam dışı.
