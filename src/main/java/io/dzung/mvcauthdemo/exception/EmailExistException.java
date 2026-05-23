package io.dzung.mvcauthdemo.exception;

public class EmailExistException extends Throwable {
    private static EmailExistException instance;

    private EmailExistException() {
        super("Email is already in use");
    }

    public static EmailExistException getInstance() {
        if (instance == null) {
            instance = new EmailExistException();
        }
        return instance;
    }
}