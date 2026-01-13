package com.example.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    // -- Standard exceptions --

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Erro de validação");
        problem.setDetail("Um ou mais campos estão inválidos");
        problem.setProperty("path", request.getRequestURI());

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        problem.setProperty("errors", errors);

        return problem;
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request
            ){

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Violação de integridade de dados");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("path", request.getRequestURI());

        return problemDetail;
    }


    // -- Custom exceptions --

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ProblemDetail handleUsernameAlreadyExists(
            UsernameAlreadyExistsException ex,
            HttpServletRequest request){

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Nome de usuário já existente");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("path", request.getRequestURI());

        return problemDetail;
    }
}
