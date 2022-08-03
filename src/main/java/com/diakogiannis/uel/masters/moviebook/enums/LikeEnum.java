package com.diakogiannis.uel.masters.moviebook.enums;


import java.util.HashMap;
import java.util.Map;

public enum LikeEnum {
    LIKE("like"),
    HATE("hate"),
    UNDO("undo");

    private static final Map<String, LikeEnum> BY_LABEL = new HashMap<>();

    static {
        for (LikeEnum e : values()) {
            BY_LABEL.put(e.type, e);
        }
    }

    private String type;

    LikeEnum(String type) {
        this.type = type;
    }

    public static LikeEnum valueOfLabel(String type) {
        return BY_LABEL.get(type);
    }

}
