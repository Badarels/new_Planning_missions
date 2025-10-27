package com.businesscenterservices.businesscenterservices.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();
}

@Data
class Violation {
    private final String fieldName;
    private final String message;

    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
} 