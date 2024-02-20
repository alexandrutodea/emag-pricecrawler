package me.alextodea.webcrawler.exception;

public class InvalidProductUrlException extends RuntimeException {
    public InvalidProductUrlException() {
        super("The product URL is invalid");
    }
}
