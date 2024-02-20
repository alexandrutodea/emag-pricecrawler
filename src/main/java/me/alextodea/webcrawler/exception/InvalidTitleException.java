package me.alextodea.webcrawler.exception;

public class InvalidTitleException extends RuntimeException {
    public InvalidTitleException() {
        super("The page title is invalid");
    }
}
