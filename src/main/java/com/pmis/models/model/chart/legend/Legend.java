package com.pmis.models.model.chart.legend;

public class Legend {
    private boolean show;
    private String position;
    private String horizontalAlign;

    public Legend() {
    }

    public Legend(boolean show, String position, String horizontalAlign) {
        this.show = show;
        this.position = position;
        this.horizontalAlign = horizontalAlign;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHorizontalAlign() {
        return horizontalAlign;
    }

    public void setHorizontalAlign(String horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }
}
