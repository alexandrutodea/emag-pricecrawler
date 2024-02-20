package me.alextodea.webcrawler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handlemethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return "Sorry, that was not quite right: " + exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolation(ConstraintViolationException exception) {
        return "Sorry, that was not quite right: " + exception.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidProductUrlException.class)
    public String handleInvalidUrlException(InvalidProductUrlException invalidProductUrlException) {
        return invalidProductUrlException.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTitleException.class)
    public String handleInvalidUrlException(InvalidTitleException invalidTitleException) {
        return invalidTitleException.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FailedToFetchPriceException.class)
    public String handleInvalidUrlException(FailedToFetchPriceException failedToFetchPriceException) {
        return failedToFetchPriceException.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FailedToParseImageException.class)
    public String handleFailedToParseImageException(FailedToParseImageException failedToParseImageException) {
        return failedToParseImageException.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleFailedToParseImageException(ProductNotFoundException productNotFoundException) {
        return productNotFoundException.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FailedToRetrieveSearchResultsException.class)
    public String handleFailedToParseHtmlException(FailedToRetrieveSearchResultsException failedToRetrieveSearchResultsException) {
        return failedToRetrieveSearchResultsException.getMessage();
    }

}
