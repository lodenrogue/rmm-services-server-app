package com.arkvis.error;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ErrorUtils {

    private ErrorUtils() {
    }

    public static List<String> getMissingFields(Errors errors) {
        List<String> missingFields = new ArrayList<>();
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                missingFields.add(error.getField());
            }
        }
        return missingFields;
    }

}
