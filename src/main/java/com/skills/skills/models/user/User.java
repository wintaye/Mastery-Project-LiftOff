package com.skills.skills.models.user;

import com.skills.skills.models.AbstractEntity;
import com.skills.skills.models.event.Event;
import com.skills.skills.models.skill.Skill;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

   // @Column(name = "username")
    @NotNull
    private String username;

    //@Column(name = "password")
    @NotNull
    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private UserProfile userProfile;

    @OneToMany(mappedBy = "id")
    private final List<Message> messages = new ArrayList<>();

    @ManyToMany
    private final List<Skill> skills = new ArrayList<>();

    @ManyToMany
    private final List<Event> events = new ArrayList<>();


    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public User(String username, String password, UserProfile userProfile) {
        this(username, password);
        this.userProfile = userProfile;
    }

    public User() {}

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public boolean isMatchingPassword(String password) { return encoder.matches(password, pwHash); }

    public UserProfile getUserProfile() { return userProfile; }

    public void setUserProfile(UserProfile userProfile) { this.userProfile = userProfile; }

    public String getPwHash() { return pwHash; }

    public List<Skill> getSkills() { return skills; }

    public List<Event> getEvents() { return events; }

    public void addSkillsToProfile (Skill skill){ this.skills.add(skill); }

    public void addEventToProfile(Event event) { this.events.add(event); }

    public List<Message> getMessages () {return messages; }

    public void addMessageToInbox (Message message) { this.messages.add(message); }


}
