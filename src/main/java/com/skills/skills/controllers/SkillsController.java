package com.skills.skills.controllers;

import com.skills.skills.data.SkillsCategoryRepository;
import com.skills.skills.data.SkillsRepository;
import com.skills.skills.data.TagRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.Skill;
import com.skills.skills.models.Tag;
import com.skills.skills.models.User;
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
    public TagRepository tagRepository;

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


    //responds to request at skills/create?userId=##
    @GetMapping("skills/create/{userId}")
    public String createNewSkill (@PathVariable Integer userId, Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        model.addAttribute("title", "Create New Skill");
        model.addAttribute(new Skill());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("categories", skillsCategoryRepository.findAll());
        return  "skills/create";
    }

    @PostMapping("skills/create/{userId}")
    public String processNewSkill(@PathVariable Integer userId, HttpSession session, Model model, @ModelAttribute @Valid Skill newSkill, Errors errors) {
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
         // save new skill
        skillsRepository.save(newSkill);
        //add skill to user
        currentUser.addSkillsToProfile(newSkill);
        //re-save user to update
        userRepository.save(currentUser);
        model.addAttribute("skills", currentUser.getSkills());
        return "redirect:/users/profile";
    }

    @GetMapping("skills/delete/{userId}")
    public String displayDeleteSkillForm(Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        model.addAttribute("skills", skillsRepository.findAll());
        return "skills/delete";
    }

    @PostMapping("skills/delete/{userId}")
    public String processDeleteSkillForm(@RequestParam (required = false) int [] skillIds, HttpSession session, Model model, @PathVariable Integer userId){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        if(skillIds != null){
            for(int id : skillIds){
                skillsRepository.deleteById(id);
            }
        }
        userRepository.save(currentUser);
        model.addAttribute("skills", currentUser.getSkills());
        return "redirect:/users/profile";
    }
}



