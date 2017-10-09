package com.gpsolutions.todolist.controller;

import com.gpsolutions.todolist.util.NotFoundException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public Error handleNotFound(HttpServletRequest req, Exception e) {
        log.info(e.toString());
        return new Error(req, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        HttpMessageNotReadableException.class})
    public Error handleBindingError(HttpServletRequest req, Exception e) {
        log.info(e.toString());
        return new Error(req, e);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Error handleDataIntegrityViolation(HttpServletRequest req, Exception e) {
        log.info(e.toString());
        return new Error(req, e);
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ResourceAccessException.class)
    public Error handleRestClientException(HttpServletRequest req, Exception e) {
        log.info(e.toString());
        return new Error(req, e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Error handleUnexpectedError(HttpServletRequest req, Exception e) {
        log.info(e.toString());
        e.printStackTrace();
        return new Error(req, e);
    }

    public static class Error {

        private final String url;
        private final String error;
        private final String message;

        public Error(HttpServletRequest req, Exception e) {
            this.url = req.getRequestURL().toString();
            this.error = e.getClass().getSimpleName();
            this.message = e.getMessage();
        }

        public String getUrl() {
            return url;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }

}
