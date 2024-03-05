package com.pmis.payload.response;

import java.util.List;

public class DashboardResponse {
    private String dashboardId;
    private String layout;
    private  String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private List<DashboardChartOptionsResponse> dashboardChartOptionsResponses;

    public DashboardResponse(String dashboardId, String layout, List<DashboardChartOptionsResponse> dashboardChartOptionsResponses) {
        this.dashboardId = dashboardId;
        this.layout = layout;
        this.dashboardChartOptionsResponses = dashboardChartOptionsResponses;
    }

    public DashboardResponse(String dashboardId, String layout, String name,  List<DashboardChartOptionsResponse> dashboardChartOptionsResponses) {
        this.dashboardId = dashboardId;
        this.layout = layout;
        this.name = name;
        this.dashboardChartOptionsResponses = dashboardChartOptionsResponses;
    }public DashboardResponse() {
    }

    public String getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(String dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public List<DashboardChartOptionsResponse> getDashboardChartOptionsResponses() {
        return dashboardChartOptionsResponses;
    }

    public void setDashboardChartOptionsResponses(List<DashboardChartOptionsResponse> dashboardChartOptionsResponses) {
        this.dashboardChartOptionsResponses = dashboardChartOptionsResponses;
    }
}
