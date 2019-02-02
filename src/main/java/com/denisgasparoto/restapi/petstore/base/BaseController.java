package com.denisgasparoto.restapi.petstore.base;

import com.denisgasparoto.restapi.petstore.dto.response.ErrorResponseDTO;
import com.denisgasparoto.restapi.petstore.exception.BusinessException;
import com.denisgasparoto.restapi.petstore.exception.RegisterNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Exception.class})
    public ErrorResponseDTO handleException(Exception e) {
        return ErrorResponseDTO.withError("Erro inesperado ao executar a operação: " + e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RegisterNotFoundException.class})
    public ErrorResponseDTO handleRegisterNotFoundException(RegisterNotFoundException e) {
        return ErrorResponseDTO.withError(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BusinessException.class})
    public ErrorResponseDTO handleBusinessException(BusinessException e) {
        return ErrorResponseDTO.withError(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ErrorResponseDTO.withError(e.getBindingResult().getFieldErrors());
    }
}
