package com.devopsbuddy.backend.service;

import com.devopsbuddy.web.domain.frontend.FeedbackPOJO;
import org.springframework.mail.SimpleMailMessage;

/**
 * Contract for email service.
 */
public interface EmailService {

    /**
     * Sends an email with the content in the Feedback Pojo.
     * @param feedbackPojo The feedback Pojo
     */
    public void sendFeedbackEmail(FeedbackPOJO feedbackPojo);

    /**
     * Sends an email with the content of the Simple Mail Message object.
     * @param message The object containing the email content
     */
    public void sendGenericEmailMessage(SimpleMailMessage message);
}