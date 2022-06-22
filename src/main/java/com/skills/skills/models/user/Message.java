package com.skills.skills.models.user;

import com.skills.skills.models.AbstractEntity;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;


@Entity
public class Message extends AbstractEntity {

    @NotBlank(message = "Message subject is required")
    @Size(max = 250, message = "Subject line must be 250 characters or less")
    private String subject;

    @NotBlank(message = "Message required.")
    @Size(max = 1000, message = "Message must be 1000 characters or less")
    private String body;

    private Timestamp timestamp;

    @NotNull
    private int recipientId;

    @NotNull
    private int senderId;

    @NotNull
    private String senderUsername;

    @NotNull
    private String recipientUsername;

    public Message(Timestamp timestamp, String subject, String body, int recipientId, String recipientUsername, int senderId, String senderUsername) {
        this.timestamp = timestamp;
        this.subject = subject;
        this.body = body;
        this.recipientId = recipientId;
        this.recipientUsername = recipientUsername;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
    }

    public Message() {}

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public Timestamp getTimestamp() { return timestamp; }

    public void setTimestamp (Timestamp timestamp) { this.timestamp = timestamp; }

    public int getRecipient() { return recipientId; }

    public void setRecipient(User recipient) { this.recipientId = recipient.getId(); }

    public int getSender() { return senderId; }

    public void setSender(User sender) { this.senderId = sender.getId(); }

    public String getSenderUsername(){ return senderUsername; }

    public void setSenderUsername (String senderUsername){ this.senderUsername = senderUsername; }

    public String getRecipientUsername() { return recipientUsername; }

    public void setRecipientUsername(String recipientUsername) { this.recipientUsername = recipientUsername; }

    public static Comparator<Message> compareByTimeStamp = new Comparator<Message>() {
        @Override
        public int compare(Message o1, Message o2) {
            return o1.timestamp.compareTo(o2.timestamp);
        }
    };

}

