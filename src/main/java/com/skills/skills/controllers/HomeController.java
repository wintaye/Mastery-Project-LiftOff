package com.skills.skills.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("")
public class HomeController {

    //HomePage
  //@GetMapping
    public String displayHomepage(Model model){
       model.addAttribute("title","What is SkillShare");
        return "index";
   }

}
