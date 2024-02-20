package me.alextodea.webcrawler.exception;

public class FailedToRetrieveSearchResultsException extends RuntimeException {
    public FailedToRetrieveSearchResultsException() {
        super("Failed to retrieve search results");
    }
}
