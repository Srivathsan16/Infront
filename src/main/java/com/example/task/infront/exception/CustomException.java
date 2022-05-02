package com.example.task.infront.exception;

public class CustomException extends Exception {

    private String errorMessage;

    private CustomErrors customErrors;

    public CustomErrors getCustomErrors() {
        return customErrors;
    }

    public void setCustomErrors(CustomErrors customErrors) {
        this.customErrors = customErrors;
    }


    public CustomException() {
        super();
    }

    public CustomException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public CustomException(CustomErrors customErrors) {
        this.customErrors = customErrors;
        this.errorMessage = customErrors.getErrorMessage();
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setMessageCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
