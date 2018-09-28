package com.jp.springbootgradleredisweb.api.rest;

import java.io.Serializable;

public class JsonRes implements Serializable {

    private String message;
    private Object data;

    public JsonRes() {
    }

    public JsonRes(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
