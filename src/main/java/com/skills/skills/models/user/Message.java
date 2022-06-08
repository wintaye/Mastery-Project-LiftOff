package com.skills.skills.models.user;

import com.skills.skills.models.AbstractEntity;
import jdk.jfr.Timestamp;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Time;

@Entity
public class Message extends AbstractEntity {

    @NotBlank(message = "Message subject is required")
    @Size(max = 250, message = "Subject line must be 100 characters or less")
    private String messageSubject;

    @NotBlank(message = "Message required.")
    @Size(max = 250, message = "Message must be 1000 characters or less")
    private String messageBody;

    @Timestamp
    private Timestamp timestamp;

    @NotBlank
    private int recipientId;

    @NotBlank
    private int senderId;

    public Message(String messageSubject, String messageBody, Timestamp timestamp, int recipientId, int senderId) {
        this.messageSubject = messageSubject;
        this.messageBody = messageBody;
        this.timestamp = timestamp;
        this.recipientId = recipientId;
        this.senderId = senderId;
    }

    public Message() {}

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getRecipient() {
        return recipientId;
    }

    public void setRecipient(User recipient) {
        this.recipientId = recipient.getId();
    }

    public int getSender() {
        return senderId;
    }

    public void setSender(User sender) {
        this.senderId = sender.getId();
    }
}
