package com.skills.skills.service;

import com.skills.skills.data.MessagesRepository;
import com.skills.skills.models.user.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessagesRepository messagesRepository;

    public List<Message> findAllMessages(){
        List<Message> allMessages = new ArrayList<>();
        for(Message message : messagesRepository.findAll() ){
            allMessages.add(message);
        }
        return  allMessages;

    }
//
//    public List<Message> findMessagesWithSorting(Timestamp timestamp){
//        return messagesRepository.findAll(Sort.by(timestamp));
//    }
}
