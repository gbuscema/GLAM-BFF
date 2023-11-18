package com.glam.bff.dto.authentication.enums;

public enum StyleEnum {

    CASUAL("Casual"),
    PARTY("Party"),
    CLASSIC("Classic"),
    STREETWEAR("Streetwear"),
    SPORT("Sport");

    private final String value;

    StyleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
