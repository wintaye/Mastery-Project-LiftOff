package com.skills.skills.controllers;


import com.skills.skills.data.EventRepository;
import com.skills.skills.data.SkillsRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.EventData;
import com.skills.skills.models.SkillData;
import com.skills.skills.models.UserData;
import com.skills.skills.models.event.Event;
import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("search")
public class SearchController {

    static HashMap<String, String> columnChoices = new HashMap<>();

    public SearchController() {

        columnChoices.put("all", "All");
        columnChoices.put("username", "User");
        columnChoices.put("skill", "Skill");
        columnChoices.put("event", "Event");
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private EventRepository eventRepository;

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

    @GetMapping("")
    public String search(Model model, HttpSession session){
        User user = getUserFormSession(session);
        if(user == null){
            return "index";
        }
        model.addAttribute("user", user);
        model.addAttribute("columns", columnChoices);

        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);

        Iterable<User> users;
        Iterable<Skill> skills;
        Iterable<Event> events;

        //if searchType == "all" && searchTerm != "", return all objects in repos
        if(searchTerm.toLowerCase().equals("") && searchType.equals("all")){
            users = userRepository.findAll();
            skills = skillsRepository.findAll();
            events = eventRepository.findAll();
        }
        //all other conditions
        else {
            users = UserData.findByColumnAndValue(searchType,searchTerm,userRepository.findAll());
            skills = SkillData.findBYColumnAndValue(searchType,searchTerm,skillsRepository.findAll());
            events = EventData.findBYColumnAndValue(searchType,searchTerm,eventRepository.findAll());
        }
        
        model.addAttribute("columns",columnChoices);
        model.addAttribute("title","Users with " + columnChoices.get(searchType) + " : " + searchTerm);
        model.addAttribute("users", users);
        model.addAttribute("title", "Skill with " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("skills", skills);
        model.addAttribute("title", "Event with " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("events", events);
        return "search";
    }



}

