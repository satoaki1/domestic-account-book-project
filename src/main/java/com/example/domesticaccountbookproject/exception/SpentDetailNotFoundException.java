package com.example.domesticaccountbookproject.exception;

public class SpentDetailNotFoundException extends RuntimeException {

    private static final Long serialVersionSID = 1L;

    public SpentDetailNotFoundException(Long usageId) {
        super("Spent Detail " + usageId + " does not exist. Search another information.");
    }
}
