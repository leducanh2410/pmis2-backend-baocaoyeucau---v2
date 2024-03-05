package com.pmis.models.model.chart.chart;

public class Animation {
    private boolean enabled;

    public Animation(boolean enabled) {
        this.enabled = enabled;
    }

    public Animation() {
        this.enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
