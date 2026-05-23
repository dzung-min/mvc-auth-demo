package io.dzung.mvcauthdemo.exception;

public class PasswordMisMatchException extends Throwable {
    private static PasswordMisMatchException instance;

    private PasswordMisMatchException() {
        super("Confirm password doesn't match");
    }

    public static PasswordMisMatchException getInstance() {
        if (instance == null)
            instance = new PasswordMisMatchException();
        return instance;
    }
}
