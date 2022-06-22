package com.skills.skills.models.event;

import com.skills.skills.models.AbstractEntity;
import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.skill.SkillsCategory;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Event extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Size(max = 250, message = "Name must be 250 characters or less")
    private String name;

    @NotBlank(message ="Say something about your event!")
    @Size(max = 500, message = "Description too long.")
    private String description;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email. Try again.")

    private String email;

    @ManyToOne
    @NotNull(message = "Skill category is required")
    private SkillsCategory catName;

    @NotNull
    private int creatorId;

    public Event(String name, SkillsCategory catName, String description, String email, int creatorId) {
        this.name = name;
        this.catName = catName;
        this.description = description;
        this.email = email;
        this.creatorId = creatorId;
    }

    public Event() {}

    public String getName() {  return name; }

    public void setName(String name) { this.name = name; }

    public void setCatName(SkillsCategory catName) { this.catName = catName; }

    public SkillsCategory getCatName() { return catName; }

    public String getCatNameString(SkillsCategory skillsCategory) { return skillsCategory.getCatName(); }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public int getCreatorId(Event event) { return creatorId; }

    public void setCreatorId(int creatorId) { this.creatorId = creatorId; }


}
