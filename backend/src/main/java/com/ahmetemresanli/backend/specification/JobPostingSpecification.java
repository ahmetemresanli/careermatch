package com.ahmetemresanli.backend.specification;

import com.ahmetemresanli.backend.entity.JobPosting;
import com.ahmetemresanli.backend.enums.EmploymentType;
import com.ahmetemresanli.backend.enums.JobLevel;
import com.ahmetemresanli.backend.enums.JobStatus;
import com.ahmetemresanli.backend.enums.WorkModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JobPostingSpecification {

    public static Specification<JobPosting> filter(
            String keyword,
            String city,
            WorkModel workModel,
            EmploymentType employmentType,
            JobLevel jobLevel,
            BigDecimal minimumSalary
    ) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Sadece yayındaki ilanlar
            predicates.add(
                    criteriaBuilder.equal(
                            root.get("status"),
                            JobStatus.PUBLISHED
                    )
            );

            // Keyword
            if (keyword != null && !keyword.isBlank()) {

                String searchKeyword =
                        "%" + keyword.trim().toLowerCase() + "%";

                Predicate titlePredicate =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("title")
                                ),
                                searchKeyword
                        );

                Predicate descriptionPredicate =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("description")
                                ),
                                searchKeyword
                        );

                predicates.add(
                        criteriaBuilder.or(
                                titlePredicate,
                                descriptionPredicate
                        )
                );
            }

            // City
            if (city != null && !city.isBlank()) {

                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(
                                        root.get("city")
                                ),
                                city.trim().toLowerCase()
                        )
                );
            }

            // Work Model
            if (workModel != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("workModel"),
                                workModel
                        )
                );
            }

            // Employment Type
            if (employmentType != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("employmentType"),
                                employmentType
                        )
                );
            }

            // Job Level
            if (jobLevel != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("jobLevel"),
                                jobLevel
                        )
                );
            }

            // Minimum Salary
            if (minimumSalary != null) {

                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("maximumSalary"), minimumSalary),
                        criteriaBuilder.and(criteriaBuilder.isNull(root.get("maximumSalary")),
                                criteriaBuilder.greaterThanOrEqualTo(root.get("minimumSalary"), minimumSalary))
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
