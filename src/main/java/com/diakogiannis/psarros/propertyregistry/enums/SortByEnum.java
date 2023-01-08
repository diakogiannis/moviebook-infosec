package com.diakogiannis.psarros.propertyregistry.enums;


import java.util.HashMap;
import java.util.Map;

public enum SortByEnum {
    LIKES(1),
    HATES(2),
    DATE(3),
    UNKNOWN(null);

    private static final Map<Integer, SortByEnum> BY_LABEL = new HashMap<>();

    static {
        for (SortByEnum e : values()) {
            BY_LABEL.put(e.type, e);
        }
    }

    private Integer type;

    SortByEnum(Integer type) {
        this.type = type;
    }

    public static SortByEnum valueOfLabel(Integer type) {
        return BY_LABEL.get(type);
    }

}
