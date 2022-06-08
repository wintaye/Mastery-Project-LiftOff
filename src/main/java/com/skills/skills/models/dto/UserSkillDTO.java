package com.skills.skills.models.dto;

import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.user.User;

import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class UserSkillDTO {

        @NotNull
        private Skill newSkill;

        @NotNull
        private User currentUser;

        @ManyToMany
        private final List<Skill> skills = new ArrayList<>();

        public UserSkillDTO() {}

        public User getUser() { return currentUser; }

        public void setUser(User user) { this.currentUser = currentUser; }

        public Skill getNewSkill() { return newSkill; }

        public void setNewSkill(Skill newSkill) { this.newSkill = newSkill; }

        public List<Skill> getSkills() { return skills; }

        public void addSkillsToUser(Skill newSkill){ this.skills.add(newSkill); }
}
