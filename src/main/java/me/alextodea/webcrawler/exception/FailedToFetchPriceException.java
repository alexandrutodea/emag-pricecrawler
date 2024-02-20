package me.alextodea.webcrawler.exception;

public class FailedToFetchPriceException extends RuntimeException {
    public FailedToFetchPriceException() {
        super("Failed to fetch price from URL");
    }
}
