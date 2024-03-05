package com.pmis.payload.response.service;

import com.pmis.payload.response.*;

public class ExecServiceResponse extends AppResponse {

    private Object data;

    public ExecServiceResponse(int status, String message) {
        super(status, message);
    }

    public ExecServiceResponse(Object data, int status, String message) {
        super(status, message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
