package com.glam.bff.dto.authentication.enums;

public enum LivingLocationEnum {

    TORINO("Torino"),
    MILANO("Milano"),
    BARI("Bari");

    private final String value;

    LivingLocationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
