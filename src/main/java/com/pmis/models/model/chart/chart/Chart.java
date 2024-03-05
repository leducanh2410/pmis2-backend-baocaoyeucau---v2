package com.pmis.models.model.chart.chart;

public class Chart {
    private String width;
    private String type;
    private Animation animations;

    public Chart(String width, String type, Animation animations) {
        this.width = width;
        this.type = type;
        this.animations = animations;
    }

    public Chart() {
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Animation getAnimations() {
        return animations;
    }

    public void setAnimations(Animation animations) {
        this.animations = animations;
    }
}
