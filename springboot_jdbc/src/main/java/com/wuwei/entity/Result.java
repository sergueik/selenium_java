package com.wuwei.entity;

import java.io.Serializable;

public class Result implements Serializable {

    private static final long serialVersionUID = 7086445730263059369L;
    
    private int status;
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
