package io.dzung.mvcauthdemo.exception;

public class EmailExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailExistException() {
        super("Email is already in use");
    }
}