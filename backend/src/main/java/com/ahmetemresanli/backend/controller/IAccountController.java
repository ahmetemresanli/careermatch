package com.ahmetemresanli.backend.controller;
import com.ahmetemresanli.backend.dto.response.OperationResponse; import org.springframework.http.ResponseEntity;
public interface IAccountController { ResponseEntity<OperationResponse> resendPrimaryVerification(); ResponseEntity<OperationResponse> verifyRecoveryEmail(); }
