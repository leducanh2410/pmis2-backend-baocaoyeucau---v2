package com.pmis.models.model.chart.style;

public class Style {
    private String fontSize;
    private String fontWeight;
    private String fontFamily;
    private String color;

    public Style() {
    }

    public Style(String fontSize, String fontWeight, String fontFamily, String color) {
        this.fontSize = fontSize;
        this.fontWeight = fontWeight;
        this.fontFamily = fontFamily;
        this.color = color;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
