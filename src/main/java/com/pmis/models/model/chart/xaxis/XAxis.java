package com.pmis.models.model.chart.xaxis;

import com.pmis.models.model.chart.title.Title;

public class XAxis {
    private Object categories;
    private String type;
    private Object title;

    public XAxis(Object categories, String type, Object title) {
        this.categories = categories;
        this.type = type;
        this.title = title;
    }

    public XAxis() {
    }

    public Object getCategories() {
        return categories;
    }

    public void setCategories(Object categories) {
        this.categories = categories;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }
}
