package com.skills.skills.controllers;

import com.skills.skills.data.MessagesRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.Tag;
import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class MessageController {

    @Autowired
    MessagesRepository messagesRepository;

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

    @GetMapping("/users/profile/inbox")
    public String displayInbox (HttpSession session, Model model) {
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        model.addAttribute("messages", user.getMessages());
        return "users/profile/inbox";


    }

}
