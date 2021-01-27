package com.viesca.csvprocessor.constants;

import java.util.Arrays;

public enum CsvFieldType {
    STRING("String"),
    NUMBER("Number"),
    PHONE_NUMBER("Phone Number"),
    EMAIL("Email Address");

    private final String displayName;

    private CsvFieldType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CsvFieldType fromString(String text) {
        return Arrays.stream(values())
                .filter(csvFieldType -> csvFieldType.displayName.equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }
}
