package com.pmis.models.entity;

import com.pmis.constant.enumerate.EChartType;
import jakarta.persistence.*;

@Entity
@Table(name = "BCTM_BIEUDO")
public class Chart {
    @Id
    @Column(name = "MA_BIEUDO")
    private String id;

    @Column(name = "USERID")
    private String userId;

    @Column(name = "MO_TA")
    private String moTa;

    @Column(name = "MA_LOAI_BIEUDO")
    @Enumerated(EnumType.STRING)
    private EChartType chartType;

    @Column(name = "CAU_HINH_HIEN_THI")
    private String cauHinhHienThi;

    @Column(name = "MA_DULIEU")
    private String maDuLieu;

    @Column(name = "CHIEU")
    private String chieu;

    @Column(name = "TEN_COT")
    private String tenCot;

    @Column(name = "HIEN")
    private String hien;

    @Column(name = "TRUC")
    private String truc;

    @Column(name = "DON_VI")
    private String donVi;

    @Column(name = "MAU_SAC")
    private String mauSac;

    @Column(name = "STT_COT")
    private String sttCot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public EChartType getChartType() {
        return chartType;
    }

    public void setChartType(EChartType chartType) {
        this.chartType = chartType;
    }

    public String getCauHinhHienThi() {
        return cauHinhHienThi;
    }

    public void setCauHinhHienThi(String cauHinhHienThi) {
        this.cauHinhHienThi = cauHinhHienThi;
    }

    public String getMaDuLieu() {
        return maDuLieu;
    }

    public void setMaDuLieu(String maDuLieu) {
        this.maDuLieu = maDuLieu;
    }

    public String getChieu() {
        return chieu;
    }

    public void setChieu(String chieu) {
        this.chieu = chieu;
    }

    public String getTenCot() {
        return tenCot;
    }

    public void setTenCot(String tenCot) {
        this.tenCot = tenCot;
    }

    public String getHien() {
        return hien;
    }

    public void setHien(String hien) {
        this.hien = hien;
    }

    public String getTruc() {
        return truc;
    }

    public void setTruc(String truc) {
        this.truc = truc;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getSttCot() {
        return sttCot;
    }

    public void setSttCot(String sttCot) {
        this.sttCot = sttCot;
    }
}
