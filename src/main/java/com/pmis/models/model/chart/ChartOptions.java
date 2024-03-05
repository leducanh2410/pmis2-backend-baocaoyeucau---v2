package com.pmis.models.model.chart;

import com.pmis.models.model.chart.chart.Chart;
import com.pmis.models.model.chart.title.Title;

import java.util.List;

public class ChartOptions {
    private Chart chart;
    private Object series;
    private Object labels;
    private Title title;
    private Object xAxis;
    private Object yAxis;
    private List<String> colors;
    private Object plotOptions;
    private Object legend;
    private Object dataLabels;
    private Object markers;

    public ChartOptions(
            Chart chart,
            Object series,
            Object labels,
            Title title,
            Object xAxis,
            Object yAxis,
            List<String> colors,
            Object plotOptions,
            Object legend,
            Object dataLabels,
            Object markers
    ) {
        this.chart = chart;
        this.series = series;
        this.labels = labels;
        this.title = title;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.colors = colors;
        this.plotOptions = plotOptions;
        this.legend = legend;
        this.dataLabels = dataLabels;
        this.markers = markers;
    }

    public ChartOptions() {
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Object getSeries() {
        return series;
    }

    public void setSeries(Object series) {
        this.series = series;
    }

    public Object getLabels() {
        return labels;
    }

    public void setLabels(Object labels) {
        this.labels = labels;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Object getxAxis() {
        return xAxis;
    }

    public void setxAxis(Object xAxis) {
        this.xAxis = xAxis;
    }

    public Object getyAxis() {
        return yAxis;
    }

    public void setyAxis(Object yAxis) {
        this.yAxis = yAxis;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public Object getPlotOptions() {
        return plotOptions;
    }

    public void setPlotOptions(Object plotOptions) {
        this.plotOptions = plotOptions;
    }

    public Object getLegend() {
        return legend;
    }

    public void setLegend(Object legend) {
        this.legend = legend;
    }

    public Object getDataLabels() {
        return dataLabels;
    }

    public void setDataLabels(Object dataLabels) {
        this.dataLabels = dataLabels;
    }

    public Object getMarkers() {
        return markers;
    }

    public void setMarkers(Object markers) {
        this.markers = markers;
    }
}
