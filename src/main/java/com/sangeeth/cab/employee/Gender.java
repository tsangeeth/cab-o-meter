package com.sangeeth.cab.employee;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE('M'),
    FEMALE('F');

    private final char value;

    Gender(char value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return Character.toString(value);
    }

    @Override
    public String toString() {
        return value();
    }

    public static Gender convert(String value) {
        return switch (value.charAt(0)) {
            case 'M' -> MALE;
            case 'F' -> FEMALE;
            default -> throw new IllegalArgumentException("Invalid gender: " + value);
        };
    }
}
