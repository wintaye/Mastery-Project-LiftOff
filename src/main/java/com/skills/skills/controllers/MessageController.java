package com.skills.skills.controllers;

import com.skills.skills.data.EventRepository;
import com.skills.skills.data.MessagesRepository;
import com.skills.skills.data.UserRepository;
import com.skills.skills.models.MessageData;
import com.skills.skills.models.Tag;
import com.skills.skills.models.event.Event;
import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.user.Message;
import com.skills.skills.models.user.User;
import com.skills.skills.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class MessageController {

    @Autowired
    MessagesRepository messagesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    private MessageService service;

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

    @GetMapping("/events/event_message/event={eventId}")
    public String displayEventMessage (@PathVariable Integer eventId, HttpSession session, Model model) {
        User user = getUserFormSession(session);
        Optional<Event> result = eventRepository.findById(eventId);
        Event currentEvent = result.get();
        Optional<User> eventCreatorUser = userRepository.findById(currentEvent.getCreatorId(currentEvent));
        User creatorUser = eventCreatorUser.get();
        model.addAttribute("creator", creatorUser);
        model.addAttribute("message", new Message());
        model.addAttribute("user", user);
        model.addAttribute("event", currentEvent);

        return "events/event_message";
    }

    @PostMapping("/events/event_message/event={eventId}")
    public String processEventMessage (@PathVariable Integer eventId, Model model,
                                       HttpSession session,  @ModelAttribute @Valid Message newMessage,
                                       Errors errors){

        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<Event> result = eventRepository.findById(eventId);
        Event currentEvent = result.get();
        Optional<User> eventCreatorUser = userRepository.findById(currentEvent.getCreatorId(currentEvent));
        User creatorUser = eventCreatorUser.get();

        if (errors.hasErrors()){
            model.addAttribute("creator", creatorUser);
            model.addAttribute("user", user);
            model.addAttribute("event", currentEvent);
            return "events/event_message";
        }

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date date = ts;
        newMessage.setTimestamp(ts);
        messagesRepository.save(newMessage);
//        userRepository.save(user);
//        userRepository.save(creatorUser);
        model.addAttribute("skills", user.getSkills());
        model.addAttribute("creatorEvents", user.getCreatorEvents());
        model.addAttribute("guestEvents", user.getGuestEvents());
        return "redirect:/users/profile";
    }

    @GetMapping("/users/inbox")
    public String displayInbox (HttpSession session, Model model) {
        User user = getUserFormSession(session);

        List<Message> sent = MessageData.usersInboxSent(user, messagesRepository.findAll());
        List<Message> received = MessageData.usersInboxReceived(user, messagesRepository.findAll());
        List<Message> allMessages = MessageData.allUsersMessages(user, service.findAllMessages());
        model.addAttribute("user", user);
        model.addAttribute("sentMessages", sent);
        model.addAttribute("receivedMessages", received);
        model.addAttribute("allMessages", allMessages);
        return "users/inbox";
    }

    @GetMapping("users/compose/{messageId}")
    public String composeMessage (HttpSession session, Model model, @PathVariable Integer messageId) {
        User user = getUserFormSession(session);
        Optional<Message> result = messagesRepository.findById(messageId);
        Message currentMessage = result.get();

        Optional<User> recipientUser = userRepository.findById(currentMessage.getSender());
        User currentRecipient = recipientUser.get();

        model.addAttribute("earlierMessage", currentMessage);
        model.addAttribute("recipient", currentRecipient);
        model.addAttribute("message", new Message());
        model.addAttribute("user", user);
        return "users/compose";
    }

    @PostMapping("users/compose/{messageId}")
    public String processNewMessage (HttpSession session, Model model, @ModelAttribute @Valid Message newMessage,
                                     Errors errors, @PathVariable Integer messageId) {

        User user = getUserFormSession(session);
        model.addAttribute("user", user);
        Optional<Message> result = messagesRepository.findById(messageId);
        Message currentMessage = result.get();
        Optional<User> recipientUser = userRepository.findById(currentMessage.getSender());
        User currentRecipient = recipientUser.get();

        if (errors.hasErrors()){
            model.addAttribute("earlierMessage", currentMessage);
            model.addAttribute("recipient", currentRecipient);
            return "users/compose";
        }
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date date = ts;
        newMessage.setTimestamp(ts);
        messagesRepository.save(newMessage);


        model.addAttribute("skills", user.getSkills());
        model.addAttribute("creatorEvents", user.getCreatorEvents());
        model.addAttribute("guestEvents", user.getGuestEvents());
        return "redirect:/users/profile";
    }
//
//    @GetMapping("users/deleteMessages/{messageId}")
//    public String displayDeleteMessageForm(Model model, HttpSession session){
//        User user = getUserFormSession(session);
//        List<Message> allMessages = MessageData.allUsersMessages(user, messagesRepository.findAll());
//        model.addAttribute("user", user);
//        model.addAttribute("allMessages", allMessages);
//        return "users/deleteMessages";
//    }
//    @PostMapping("/users/inbox")
//    public String displayInbox (HttpSession session, Model model, @RequestParam int messageId) {
//        User user = getUserFormSession(session);
//        messagesRepository.deleteById(messageId);
//        List<Message> sent = MessageData.usersInboxSent(user, messagesRepository.findAll());
//        List<Message> received = MessageData.usersInboxReceived(user, messagesRepository.findAll());
//        List<Message> allMessages = MessageData.allUsersMessages(user, messagesRepository.findAll());
//        model.addAttribute("user", user);
//        model.addAttribute("sentMessages", sent);
//        model.addAttribute("receivedMessages", received);
//        model.addAttribute("allMessages", allMessages);
//        return "users/inbox";
//    }

}
