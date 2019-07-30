package com.sample.api.model;


public abstract class ResponseStatus {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    protected String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
