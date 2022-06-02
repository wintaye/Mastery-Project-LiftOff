package com.skills.skills.controllers;


import com.skills.skills.data.UserRepository;
import com.skills.skills.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    UserRepository userRepository;

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
    @GetMapping("about")
    public String displayAboutUs (Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        model.addAttribute("title","About Us");
        return "/about";
    }


}
