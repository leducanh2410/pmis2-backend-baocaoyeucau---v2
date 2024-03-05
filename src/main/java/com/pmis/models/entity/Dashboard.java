package com.pmis.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BCTM_DASHBOARD")
public class Dashboard {
    @Id
    @Column(name = "MA_DASHBOARD")
    private String id;

    @Column(name = "LAYOUT")
    private String layout;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "LST_CHARTS")
    private String charts;

    @Column(name = "POSITION")
    private String position;
    @Column(name = "NAME")
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCharts() {
        return charts;
    }

    public void setCharts(String charts) {
        this.charts = charts;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
