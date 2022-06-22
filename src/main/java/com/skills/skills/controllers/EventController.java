package com.skills.skills.controllers;

import com.skills.skills.data.EventRepository;
import com.skills.skills.data.SkillsCategoryRepository;
import com.skills.skills.data.TagRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.event.Event;
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
@RequestMapping("events")
public class EventController {

    @Autowired
    public EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public TagRepository tagRepository;

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

    //responds to request at events/create?userId=##
    @GetMapping("create/{userId}")
    public String createNewEvent (@PathVariable Integer userId, Model model, HttpSession session){
        User user = getUserFormSession(session);
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        model.addAttribute("user", currentUser);
        model.addAttribute(new Event());
        model.addAttribute("categories", skillsCategoryRepository.findAll());
        return  "events/create";
    }

    @PostMapping("create/{userId}")
    public String processNewEvent(@PathVariable Integer userId, HttpSession session,
                                  Model model, @ModelAttribute @Valid Event newEvent,
                                  Errors errors) {

        //find user
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();

        //validate event inputs
        if (errors.hasErrors()){
            model.addAttribute("categories", skillsCategoryRepository.findAll());
            return "events/create";
        }

        // save new event
        eventRepository.save(newEvent);

        //add event to user
        currentUser.addCreatorEventToProfile(newEvent);
        //re-save user to update
        userRepository.save(currentUser);
        model.addAttribute("creatorEvents", currentUser.getCreatorEvents());
        model.addAttribute("guestEvents", currentUser.getGuestEvents());
        return "redirect:/users/profile";
    }

    @GetMapping("delete/{userId}")
    public String displayDeleteEventForm(Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }
    @PostMapping("delete/{userId}")
    public String processDeleteEventForm(@RequestParam (required = false) int [] eventIds, HttpSession session, Model model, @PathVariable Integer userId){
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        if(eventIds != null){
            for(int id : eventIds){
                eventRepository.deleteById(id);
            }
        }
        userRepository.save(currentUser);
        model.addAttribute("events", currentUser.getCreatorEvents());
        return "redirect:/users/profile";
    }

    @GetMapping("edit/id={userId}&event={eventId}")
    public String editEvent (@PathVariable Integer userId, @PathVariable Integer eventId, Model model, HttpSession session){
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        Optional<Event> event = eventRepository.findById(eventId);
        Event currentEvent = event.get();
        model.addAttribute("user", currentUser.getUserProfile());
        model.addAttribute("event", currentEvent);
        model.addAttribute("cats", skillsCategoryRepository.findAll());
        return "events/edit";
    }
    @PostMapping("edit/id={userId}&event={eventId}")
    public String processEditEvent (@PathVariable Integer userId, @PathVariable Integer eventId,
                                    HttpSession session, Model model, @ModelAttribute @Valid Event event){
        Optional<User> result = userRepository.findById(userId);
        User currentUser = result.get();
        Optional<Event> oldEvent = eventRepository.findById(eventId);
        Event currentEvent = oldEvent.get();

        currentEvent.setName(event.getName());
        currentEvent.setDescription(event.getDescription());
        currentEvent.setCatName(event.getCatName());


        eventRepository.save(currentEvent);
        userRepository.save(currentUser);
        model.addAttribute("skills", currentUser.getSkills());
        model.addAttribute("creatorEvents", currentUser.getCreatorEvents());
        model.addAttribute("guestEvents", currentUser.getGuestEvents());
        return "redirect:/users/profile";
    }

    //responds to request at events/id={userId}&event={eventId}
    @GetMapping("event_details/id={userId}&event={eventId}")
    public String rsvpToEvent (@PathVariable Integer eventId, @PathVariable Integer userId, Model model, HttpSession session){
        User user = getUserFormSession(session);
        Optional<Event> event = eventRepository.findById(eventId);
        Event currentEvent = event.get();
        String eventCheck = new String();
        if(user.getGuestEvents().contains(currentEvent)){
            eventCheck = "guest";
        } else if(user.getCreatorEvents().contains(currentEvent)){
            eventCheck = "creator";
        }
        model.addAttribute("eventCheck", eventCheck);
        model.addAttribute("user", user);
        model.addAttribute("event", currentEvent);
        return "events/event_details";
    }

    @PostMapping("event_details/id={userId}&event={eventId}")
    public String processRSVPToEvent (@PathVariable Integer eventId, @PathVariable Integer userId, Model model, HttpSession session){
        User user = getUserFormSession(session);
        model.addAttribute("user", user);

        Optional<Event> result1 = eventRepository.findById(eventId);
        Event currentEvent = result1.get();
        if(user.getGuestEvents().contains(currentEvent) == false){
            user.addGuestEventToProfile(currentEvent);
        }else if(user.getGuestEvents().contains(currentEvent) != false){
            user.removeGuestEventFromProfile(currentEvent);
        }
        userRepository.save(user);
        model.addAttribute("skills", user.getSkills());
        model.addAttribute("creatorEvents", user.getCreatorEvents());
        model.addAttribute("guestEvents", user.getGuestEvents());
        return "redirect:/users/profile";
    }

}