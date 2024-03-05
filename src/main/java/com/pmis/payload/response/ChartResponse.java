package com.pmis.payload.response;

import java.util.List;
import java.util.Objects;

public class ChartResponse {
    private String name;
    private List<Object> data;

    public ChartResponse() {
    }

    public ChartResponse(String name, List<Object> data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
