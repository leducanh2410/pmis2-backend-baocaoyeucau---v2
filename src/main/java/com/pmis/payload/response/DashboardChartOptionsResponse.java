package com.pmis.payload.response;

import com.pmis.models.model.chart.ChartOptions;

public class DashboardChartOptionsResponse {
    private String layout;
    private ChartOptions chartOptions;

    public DashboardChartOptionsResponse(String layout, ChartOptions chartOptions) {
        this.layout = layout;
        this.chartOptions = chartOptions;
    }

    public DashboardChartOptionsResponse() {
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public ChartOptions getChartOptions() {
        return chartOptions;
    }

    public void setChartOptions(ChartOptions chartOptions) {
        this.chartOptions = chartOptions;
    }
}
