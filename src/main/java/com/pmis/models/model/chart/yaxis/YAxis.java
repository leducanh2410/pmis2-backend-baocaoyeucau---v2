package com.pmis.models.model.chart.yaxis;

import com.pmis.models.model.chart.title.Title;

public class YAxis {
    private Title title;

    public YAxis(Title title) {
        this.title = title;
    }

    public YAxis() {
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}
