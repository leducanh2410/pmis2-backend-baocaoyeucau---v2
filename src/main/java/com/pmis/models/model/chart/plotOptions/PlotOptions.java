package com.pmis.models.model.chart.plotOptions;

public class PlotOptions {
    private Bar bar;

    public PlotOptions(Bar bar) {
        this.bar = bar;
    }

    public PlotOptions() {
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }
}
