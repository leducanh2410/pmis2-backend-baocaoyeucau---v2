package com.pmis.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BarChartSeriesRequest {
    @NotNull
    @NotBlank
    private String[] columns;
    private String xCol;
    private Object[] categories;
    @NotNull
    @NotBlank
    private String dataExploitation;

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String getxCol() {
        return xCol;
    }

    public void setxCol(String xCol) {
        this.xCol = xCol;
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
