package com.sample.api.model;

import javax.servlet.http.HttpServletResponse;


public class ResponseError extends ResponseStatus {

    private String error;

    public ResponseError() {
    }

    public ResponseError(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    public ResponseError(String error, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        this.error = error;
        setStatus(ERROR);
    }

    public ResponseError(String error, HttpServletResponse response, int errorCode) {
        response.setStatus(errorCode);
        this.error = error;
        setStatus(ERROR);
    }

    @Deprecated
    public ResponseError(String error) {
        this.error = error;
        setStatus(ERROR);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
