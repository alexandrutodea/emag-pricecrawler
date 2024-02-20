package me.alextodea.webcrawler.exception;

public class FailedToParseImageException extends RuntimeException {
    public FailedToParseImageException() {
        super("Failed to fetch price from URL");
    }
}
