package com.ho.blogt.exception;

import com.ho.blogt.enums.ErrorCodeAndMsg;

public class HttpException extends RuntimeException{
    private static final long serialVersionUID = -6370612186038915645L;

    private final ErrorCodeAndMsg response;

    public HttpException(ErrorCodeAndMsg response) {
        this.response = response;
    }
    public ErrorCodeAndMsg getResponse() {
        return response;
    }
}
