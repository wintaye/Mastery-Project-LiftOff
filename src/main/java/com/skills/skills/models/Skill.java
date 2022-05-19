package com.skills.skills.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Skill extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Size(max = 30, message = "Name must be 30 characters or less")
    private String name;

    @ManyToOne
    @NotNull(message = "Skill category is required")
    private SkillsCategory catName;

    private String skillTag;

    public Skill(String name, SkillsCategory catName, String skillTag) {
        this.name = name;
        this.catName = catName;
        this.skillTag = skillTag;
    }

    public Skill() {}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public SkillsCategory getCatName() { return catName; }

    public void setCatName(SkillsCategory catName) { this.catName = catName; }

    public String getSkillTag() { return skillTag; }

    public void setSkillTag(String skillTag) { this.skillTag = skillTag; }
}


