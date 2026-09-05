package com.ahmetemresanli.backend.entity;

import com.ahmetemresanli.backend.enums.LanguageProficiency;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter; import lombok.NoArgsConstructor;

@Entity @Table(name="candidate_languages", uniqueConstraints=@UniqueConstraint(columnNames={"candidate_profile_id","language_id"}))
@Getter @Setter @NoArgsConstructor
public class CandidateLanguage {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="candidate_profile_id", nullable=false) private CandidateProfile candidateProfile;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="language_id", nullable=false) private Language language;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=30) private LanguageProficiency proficiency;
}
