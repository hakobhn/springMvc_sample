package com.sample.api.model;


public class ResponseSuccess extends ResponseStatus {

    protected Object rspObject;

    public ResponseSuccess() {
        setStatus(SUCCESS);
    }

    public ResponseSuccess(Object respObject) {
        this();
        setRspObject(respObject);
    }

    public Object getRspObject() {
        return rspObject;
    }

    public void setRspObject(Object rspObject) {
        this.rspObject = rspObject;
    }
}
