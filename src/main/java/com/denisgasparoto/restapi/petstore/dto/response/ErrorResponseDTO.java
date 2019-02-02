package com.denisgasparoto.restapi.petstore.dto.response;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponseDTO {

    private List<String> errors;

    public static ErrorResponseDTO buildError(List<String> errorsList) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrors(errorsList);

        return errorResponseDTO;
    }

    public static ErrorResponseDTO withError(String error) {
        List<String> errorsList = new ArrayList<>();
        errorsList.add(error);

        return buildError(errorsList);
    }

    public static ErrorResponseDTO withError(List<FieldError> errors) {
        List<String> errorsList = new ArrayList<>();
        errors.forEach(error -> errorsList
                .add(String.format("%s: %s", error.getField(), error.getDefaultMessage())));

        return buildError(errorsList);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
