package com.pmis.models.model.chart.title;

import com.pmis.models.model.chart.style.Style;

public class Title {
    private String text;
    private String align;
    private Integer margin;
    private Integer offsetX;
    private Integer offsetY;
    private Boolean floating;
    private Style style;
    private String position;

    public Title() {
    }

    public Title(
            String text,
            String align,
            Integer margin,
            Integer offsetX,
            Integer offsetY,
            Boolean floating,
            Style style,
            String position
    ) {
        this.text = text;
        this.align = align;
        this.margin = margin;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.floating = floating;
        this.style = style;
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    public Integer getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(Integer offsetX) {
        this.offsetX = offsetX;
    }

    public Integer getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(Integer offsetY) {
        this.offsetY = offsetY;
    }

    public Boolean getFloating() {
        return floating;
    }

    public void setFloating(Boolean floating) {
        this.floating = floating;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
