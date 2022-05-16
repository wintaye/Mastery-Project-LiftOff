package com.skills.skills.controllers;

import com.skills.skills.data.SkillsCategoryRepository;
import com.skills.skills.data.SkillsRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.Skill;
import com.skills.skills.models.User;
import com.skills.skills.models.dto.UserSkillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("")
public class SkillsController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    SkillsRepository skillsRepository;

    @Autowired
    public SkillsCategoryRepository skillsCategoryRepository;

    private static final String userSessionKey = "user";

    public User getUserFormSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }


    //responds to requests at skills/create?userId=##
    @GetMapping("skills/create/{userId}")
    public String createNewSkill (@PathVariable Integer userId, Model model){
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        model.addAttribute("title", "Create New Skill");
        model.addAttribute(new Skill());
        model.addAttribute("categories", skillsCategoryRepository.findAll());
        return  "skills/create";
    }

    @PostMapping("skills/create/{userId}")
    public String processNewSkill(@PathVariable Integer userId, HttpSession session, Model model, @ModelAttribute @Valid Skill newSkill, Errors errors) {

        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
         // save new skill
        skillsRepository.save(newSkill);
        //add skill to user
        currentUser.addSkillsToProfile(newSkill);
        //resave user to update
        userRepository.save(currentUser);
        model.addAttribute("skills", currentUser.getSkills());
        return "redirect:/users/view/" + userId;
    }

}

