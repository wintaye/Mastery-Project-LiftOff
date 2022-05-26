package com.skills.skills.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.List;

public enum Category {

    DANCE("Dance"),
    LANGUAGE("Language"),
    ARTS("Arts");

    private final String catName;

    Category(String catName) {
        this.catName = catName;
    }

    public String getCategoryName(){
        return catName;
    }
}
