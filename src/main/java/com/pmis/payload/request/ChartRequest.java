package com.pmis.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChartRequest {
    @NotNull
    @NotBlank
    private String[] lines;
    @NotNull
    @NotBlank
    private String xCoordinate;
    @NotNull
    @NotBlank
    private Object[] categories;
    @NotNull
    @NotBlank
    private String dataExploitation;

    public String[] getLines() {
        return lines;
    }

    public void setLines(String[] lines) {
        this.lines = lines;
    }

    public String getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(String xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Object[] getCategories() {
        return categories;
    }

    public void setCategories(Object[] categories) {
        this.categories = categories;
    }

    public String getDataExploitation() {
        return dataExploitation;
    }

    public void setDataExploitation(String dataExploitation) {
        this.dataExploitation = dataExploitation;
    }
}
