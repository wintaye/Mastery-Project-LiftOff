package com.skills.skills.controllers;

import com.skills.skills.data.SkillsCategoryRepository;
import com.skills.skills.data.SkillsRepository;
import com.skills.skills.data.TagRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.event.Event;
import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.Tag;
import com.skills.skills.models.skill.SkillsCategory;
import com.skills.skills.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("skills")
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
    @GetMapping("create/{userId}")
    public String createNewSkill (@PathVariable Integer userId, Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        model.addAttribute(new Skill());
        model.addAttribute(new Tag());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("categories", skillsCategoryRepository.findAll());
        return  "skills/create";
    }

    @PostMapping("create/{userId}")
    public String processNewSkill(@PathVariable Integer userId, HttpSession session,
                                  Model model, @ModelAttribute @Valid Skill newSkill,
                                  Errors errors) {

        //find user
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();

        //validate skill inputs
        if (errors.hasErrors()){
            model.addAttribute("tags", tagRepository.findAll());
            model.addAttribute("categories", skillsCategoryRepository.findAll());
            return "skills/create";
        }

        // save new skill
        skillsRepository.save(newSkill);

        //add skill to user
        currentUser.addSkillsToProfile(newSkill);

        //re-save user to update
        userRepository.save(currentUser);

        //send re-saved user back through model
//        model.addAttribute("skills", currentUser.getSkills());
        return "redirect:/users/profile";
    }

    @GetMapping("delete/{userId}")
    public String displayDeleteSkillForm(Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        model.addAttribute("skills", skillsRepository.findAll());
        return "skills/delete";
    }

    @PostMapping("delete/{userId}")
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

    //responds to request at skills/id={userId}&event={skillId}
    @GetMapping("skill_details/id={userId}&skill={skillId}")
    public String getSkillFromSearchResults (@PathVariable Integer skillId, @PathVariable Integer userId, Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<Skill> skill = skillsRepository.findById(skillId);
        Skill currentSkill = skill.get();
        String skillCheck = new String();
        if(user.getSkills().contains(currentSkill)){
            skillCheck = "hasSkill";
        }
        model.addAttribute("skillCheck", skillCheck);
        model.addAttribute("user", user);
        model.addAttribute("skill", currentSkill);
        return "skills/skill_details";
    }

    @PostMapping("skill_details/id={userId}&skill={skillId}")
    public String processSkillFromSearchResults (@PathVariable Integer skillId, @PathVariable Integer userId, Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<Skill> result = skillsRepository.findById(skillId);
        Skill currentSkill = result.get();
        if(user.getSkills().contains(currentSkill) == false){
            user.addSkillsToProfile(currentSkill);
        }else if(user.getSkills().contains(currentSkill) != false){
            user.removeSkillsFromProfile(currentSkill);
        }
        userRepository.save(user);
        model.addAttribute("skills", user.getSkills());
        model.addAttribute("creatorEvents", user.getCreatorEvents());
        model.addAttribute("guestEvents", user.getGuestEvents());
        return "redirect:/users/profile";
    }
}



