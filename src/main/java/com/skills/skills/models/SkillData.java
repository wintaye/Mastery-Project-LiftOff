package com.skills.skills.models;

import com.skills.skills.models.skill.Skill;
import com.skills.skills.models.user.User;

import java.util.ArrayList;

public class SkillData {

    public static ArrayList<Skill> findBYColumnAndValue(String column, String value, Iterable<Skill> allSkills){

        ArrayList<Skill>skillResults = new ArrayList<>();

//if Stype=skill or all
        if((column.equals("skill")) || (column.equals("all"))){
//return all for Sterm=""
            if((value.toLowerCase().equals("")) && (column.equals("skill"))) {
                return (ArrayList<Skill>) allSkills;
            }
//Sterm="keyword/value" or ""
            else if((column.equals("skill"))){
                skillResults = findByValue(value, allSkills);
                return skillResults;
            }
 //Stype=all Sterm="keyword/value
            else if((column.equals("all"))){
                skillResults = findByValue(value, allSkills);
                return skillResults;
            }
        }

        return skillResults;
    }

    public static String getFieldValue(Skill skill, String attributeName) {
        String theValue;

        if(attributeName.equals("skill")){
            theValue = skill.getName();
        }else{
            theValue = skill.getCatName().toString();
        }

        return theValue;

    }

    public static ArrayList<Skill> findByValue(String value, Iterable<Skill> allSkills){

        ArrayList<Skill>skillResults = new ArrayList<>();

        for(Skill skill : allSkills){
            if(skill.getName().toLowerCase().contains(value.toLowerCase())){
                skillResults.add(skill);

            }else if(skill.getCatNameString(skill).toLowerCase().contains(value.toLowerCase())){
                skillResults.add(skill);
            }
        }
        return skillResults;
    }

}