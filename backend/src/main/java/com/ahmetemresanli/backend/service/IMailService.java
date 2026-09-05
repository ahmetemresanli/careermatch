package com.ahmetemresanli.backend.service;

public interface IMailService {
    void send(String to, String subject, String body);
}
