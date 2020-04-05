package com.positivemind.newsapp.utils.exception;

import androidx.annotation.StringRes;

public class AppException {

    @StringRes
    private int errorResourceTag;
    private int statusCode;
    private String errorMessage;


    public AppException(@StringRes int errorResourceTag)
    {
        this.errorResourceTag = errorResourceTag;
    }

    public AppException(@StringRes int errorResourceTag, int statusCode)
    {
        this.errorResourceTag =errorResourceTag;
        this.statusCode =statusCode;
    }


    public AppException(String message, int statusCode)
    {
        this.errorMessage = message;
        this.statusCode =statusCode;
    }


    public AppException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorResourceTag() {
        return errorResourceTag;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
