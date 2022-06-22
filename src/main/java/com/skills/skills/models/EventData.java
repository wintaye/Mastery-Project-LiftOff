package com.skills.skills.models;

import com.skills.skills.models.event.Event;
import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.user.User;

import java.util.ArrayList;

public class EventData {

    public static ArrayList<Event> findBYColumnAndValue(String column, String value, Iterable<Event> allEvents){

        ArrayList<Event>eventResults = new ArrayList<>();

//if Stype=event or all
        if((column.equals("event")) || (column.equals("all"))){
//return all for Sterm=""
            if((value.toLowerCase().equals("")) && (column.equals("event"))) {
                return (ArrayList<Event>) allEvents;
            }
//Sterm="keyword/value" or ""
            else if((column.equals("event"))){
                    eventResults = findByValue(value, allEvents);
            return eventResults;
        }
//Stype=all Sterm="keyword/value
            else if((column.equals("all"))){
                eventResults = findByValue(value, allEvents);
                return eventResults;
            }
 }
        return eventResults;
    }

//
    public static String getFieldValue(Event event, String attributeName){
        String theValue;

        if(attributeName.equals("description")){
            theValue = event.getDescription();
        }else{
            theValue = event.getName();
        }
        return theValue;
    }

    //searches event repo for value/Sterm, adds to eventResults arraylist
    public static ArrayList<Event> findByValue(String value, Iterable<Event> allEvents){

        ArrayList<Event>eventResults = new ArrayList<>();

        for(Event event : allEvents){
            if(event.getName().toLowerCase().contains(value.toLowerCase())){
                eventResults.add(event);
            }else if(event.getDescription().toLowerCase().contains(value.toLowerCase())){
                eventResults.add(event);

            }
        }
        return eventResults;
    }
}
