package pl.michalzadrozny.familyrecipes.service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}