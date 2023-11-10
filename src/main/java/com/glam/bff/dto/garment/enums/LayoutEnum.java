package com.glam.bff.dto.garment.enums;

/**
 * Used for Dress, Skirt and Jumpsuit
 */
public enum LayoutEnum {

    ONE_PIECE("One-Piece"),
    GENERIC("Generic");

    private final String value;

    LayoutEnum(String value) {
		this.value = value;
    }

    public String getValue() {
        return value;
    }
}
