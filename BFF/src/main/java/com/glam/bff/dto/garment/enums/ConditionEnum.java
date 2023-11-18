package com.glam.bff.dto.garment.enums;

/**
 * Used for main and second color
 */
public enum ConditionEnum {

    NEW("New"),
    EXCELLENT_CONDITION("Excellent-Condition"),
    FEW_TIMES("Used-Few-Times"),
    MANY_TIMES("Used-Many-Times"),
    EXHAUSTED("Exhausted");

    private final String value;

    ConditionEnum(String value) {
		this.value = value;
    }

    public String getValue() {
        return value;
    }
}
