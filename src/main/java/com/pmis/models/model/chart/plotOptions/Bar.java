package com.pmis.models.model.chart.plotOptions;

import com.pmis.models.model.chart.dataLabels.DataLabels;

public class Bar {
    private boolean horizontal;
    private DataLabels dataLabels;

    public Bar() {
    }

    public Bar(boolean horizontal, DataLabels dataLabels) {
        this.horizontal = horizontal;
        this.dataLabels = dataLabels;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public DataLabels getDataLabels() {
        return dataLabels;
    }

    public void setDataLabels(DataLabels dataLabels) {
        this.dataLabels = dataLabels;
    }
}
