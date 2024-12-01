package com.kutaysendil.siteManagement.enums;


public enum PropertyType {
    APARTMENT("Daire"),
    SHOP("Dükkan"),
    OFFICE("Ofis"),
    PARKING("Otopark");

    private final String displayName;

    PropertyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}