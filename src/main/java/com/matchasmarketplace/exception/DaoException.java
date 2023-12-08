package com.matchasmarketplace.exception;

public class DaoException extends RuntimeException{
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Exception exception) {
        super(message, exception);
    }
}
