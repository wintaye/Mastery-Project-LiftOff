package com.skills.skills.controllers;

import com.skills.skills.data.UserRepository;
import com.skills.skills.models.User;
import com.skills.skills.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String displayUsers(Model model, HttpSession session){
        User user= authenticationController.getUserFormSession(session);
        List<User> users = new ArrayList<>();
        users.add(user);
        model.addAttribute("Users", userRepository.findAll());
        return "users/index";
    }

    //logged in profile page
    @GetMapping("view/{userId}")
    public String viewUser(@PathVariable int userId, HttpSession session, Model model){

        User currentUser = authenticationController.getUserFormSession(session);
        Optional<User> user = userRepository.findById(userId);
        User newUser = user.get();
        model.addAttribute("skills", newUser.getSkills());

        if(user.isEmpty()){
           // return null;
            return "redirect:/users/";
        }
        if(currentUser.getId() != user.get().getId()){
            return "redirect:/users";
        }else {
            model.addAttribute("user", currentUser);
        }

       //return user.get();
        return "users/view";
    }
    @GetMapping("edit/{userId}")
    public String editUser(@PathVariable Integer userId, Model model, HttpSession session){
        User userLoggedIn = authenticationController.getUserFormSession(session);
        Optional<User> getUser = userRepository.findById(userId);

        if (getUser.isEmpty()){
            return "redirect:/users/";
        }
        User currentUser = getUser.get();
        if(userLoggedIn.getId() != userId){
            return "redirect:/users/";
        }
        model.addAttribute("user", currentUser.getUserProfile());
        return "users/edit";
    }
    @PostMapping("edit/{userId}")
    public String processEdit(@ModelAttribute @Valid UserProfile user, Errors errors,
                              @PathVariable Integer userId, HttpSession session, Model model){

        if(errors.hasErrors()) {
            model.addAttribute("user", user);
            return "users/edit";
        }
        User userLoggedIn = authenticationController.getUserFormSession(session);
        Optional<User> getUser = userRepository.findById(userId);
        if(getUser.isEmpty()) {
            return "redirect:/users/";
        }
        User currentUser = getUser.get();
        if(userLoggedIn.getId() != userId) {
                return "redirect:/users/";

    }
        if(currentUser.getUserProfile() != user) {
            currentUser.getUserProfile().setFirstName(user.getFirstName());
            currentUser.getUserProfile().setLastName(user.getLastName());
            currentUser.getUserProfile().setEmail(user.getEmail());
            currentUser.getUserProfile().setPhoneNumber(user.getPhoneNumber());
            currentUser.getUserProfile().setZipCode(user.getZipCode());
        }

        userRepository.save(currentUser);
        return "redirect:/users/view/" + userId;
    }


}
