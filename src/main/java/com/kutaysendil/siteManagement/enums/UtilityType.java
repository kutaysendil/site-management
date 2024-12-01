package com.kutaysendil.siteManagement.enums;


public enum UtilityType {
    WATER("Su"),
    ELECTRICITY("Elektrik"),
    GAS("Doğalgaz");

    private final String displayName;

    UtilityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}