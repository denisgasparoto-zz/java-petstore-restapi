package com.denisgasparoto.restapi.petstore.exception;

public class RegisterNotFoundException extends RuntimeException {

    private static final float serialVersionUID = 1L;

    public RegisterNotFoundException(String message) {
        super(message);
    }
}
