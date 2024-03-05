package com.pmis.models.model.chart.dataLabels;

public class DataLabels {
    private boolean enabled;
    private String position;

    public DataLabels() {
        this.enabled = false;
    }

    public DataLabels(boolean enabled, String position) {
        this.enabled = enabled;
        this.position = position;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
