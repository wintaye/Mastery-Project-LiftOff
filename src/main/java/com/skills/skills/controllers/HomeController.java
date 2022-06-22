package com.skills.skills.controllers;


import com.skills.skills.data.EventRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.Tag;
import com.skills.skills.models.event.Event;
import com.skills.skills.models.user.Message;
import com.skills.skills.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

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

    private static final String userSessionKey = "user";

    @GetMapping("")
    public String displayHomepage(Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        model.addAttribute("title","What is SkillShare");
        return "index";
    }

    @GetMapping("home")
    public String displayHome (Model model, HttpSession session){
        User user = getUserFormSession(session);
        List<Event> classes = new ArrayList<>();
        for(Event event : eventRepository.findAll() ){
            classes.add(event);
        }
        Collections.shuffle(classes);
        List<Event> homeClassList = new ArrayList<>();

        model.addAttribute("user", user);
        model.addAttribute("events",classes);
        return "/home";
    }

    @GetMapping("about")
    public String displayAboutUs (Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        model.addAttribute("title","About Us");
        return "/about";
    }

}
