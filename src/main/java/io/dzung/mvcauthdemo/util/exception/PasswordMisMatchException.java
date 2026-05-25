package io.dzung.mvcauthdemo.util.exception;

public class PasswordMisMatchException extends Throwable {
    private static final long serialVersionUID = 1L;

    public PasswordMisMatchException() {
        super("Confirm password doesn't match");
    }
}
