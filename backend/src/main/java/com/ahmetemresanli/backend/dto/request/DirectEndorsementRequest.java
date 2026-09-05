package com.ahmetemresanli.backend.dto.request;
import com.ahmetemresanli.backend.enums.ReferenceRelation; import jakarta.validation.constraints.*;
public record DirectEndorsementRequest(@NotNull ReferenceRelation relation,@Size(max=2000) String comment) { }
