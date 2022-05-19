package com.skills.skills.models;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public enum Tag {

    WANT_TO_LEARN("Want to Learn"),
    WANT_TO_SHARE("Want to Share"),
    SKILLS_I_HAVE("Skills I Have");


    private final String displayName;

    Tag(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
