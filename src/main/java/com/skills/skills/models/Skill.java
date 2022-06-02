package com.skills.skills.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Size(max = 30, message = "Name must be 30 characters or less")
    public String name;

    @ManyToOne
    @NotNull(message = "Skill category is required")
    private SkillsCategory catName;

    @ManyToOne
    @NotNull
    public Tag tagName;

    public Skill(String name, SkillsCategory catName, Tag tagName) {
        this.name = name;
        this.catName = catName;
        this.tagName = tagName;
    }

    public Skill() {}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getTagId(Tag tag) { return tag.getId(); }

    public SkillsCategory getCatName() { return catName; }

    public void setCatName(SkillsCategory catName) { this.catName = catName; }

    public Tag getTagName() { return tagName; }

    public void setTagName (Tag tagName) { this.tagName = tagName; }

}


