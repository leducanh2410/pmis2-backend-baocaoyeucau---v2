package com.pmis.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "BCTM_CAU_HINH_KET_NOI")
public class ConnectionConfig {
    @Id
    @Column(name = "MA_KETNOI")
    private String MAKETNOI;

    @Column(name = "TEN_KETNOI")
    @NotNull
    @NotBlank
    private String TENKETNOI;

    @Column(name = "IP")
    @NotNull
    @NotBlank
    private String IP;

    @Column(name = "PORT")
    @NotNull
    @NotBlank
    private String PORT;

    @Column(name = "ORGID")
    private String ORGID;

    @Column(name = "TEN_DANG_NHAP")
    @NotNull
    @NotBlank
    private String TENDANGNHAP;

    @Column(name = "MAT_KHAU")
    @NotNull
    @NotBlank
    private String MATKHAU;

    @Column(name = "TEN_CSDL")
    @NotNull
    @NotBlank
    private String TENCSDL;

    @Column(name = "USER_CR_ID", updatable = false)
    private String USERCRID;

    @Column(name = "USER_CR_DTIME", updatable = false)
    private Date USERCRDTIME;

    @Column(name = "USER_MDF_ID")
    private String USERMDFID;

    @Column(name = "USER_MDF_DTIME")
    private Date USERMDFDTIME;

    public String getMAKETNOI() {
        return MAKETNOI;
    }

    public void setMAKETNOI(String MAKETNOI) {
        this.MAKETNOI = MAKETNOI;
    }

    public String getTENKETNOI() {
        return TENKETNOI;
    }

    public void setTENKETNOI(String TENKETNOI) {
        this.TENKETNOI = TENKETNOI;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPORT() {
        return PORT;
    }

    public void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public String getORGID() {
        return ORGID;
    }

    public void setORGID(String ORGID) {
        this.ORGID = ORGID;
    }

    public String getTENDANGNHAP() {
        return TENDANGNHAP;
    }

    public void setTENDANGNHAP(String TENDANGNHAP) {
        this.TENDANGNHAP = TENDANGNHAP;
    }

    public String getMATKHAU() {
        return MATKHAU;
    }

    public void setMATKHAU(String MATKHAU) {
        this.MATKHAU = MATKHAU;
    }

    public String getTENCSDL() {
        return TENCSDL;
    }

    public void setTENCSDL(String TENCSDL) {
        this.TENCSDL = TENCSDL;
    }

    public String getUSERCRID() {
        return USERCRID;
    }

    public void setUSERCRID(String USERCRID) {
        this.USERCRID = USERCRID;
    }

    public Date getUSERCRDTIME() {
        return USERCRDTIME;
    }

    public void setUSERCRDTIME(Date USERCRDTIME) {
        this.USERCRDTIME = USERCRDTIME;
    }

    public String getUSERMDFID() {
        return USERMDFID;
    }

    public void setUSERMDFID(String USERMDFID) {
        this.USERMDFID = USERMDFID;
    }

    public Date getUSERMDFDTIME() {
        return USERMDFDTIME;
    }

    public void setUSERMDFDTIME(Date USERMDFDTIME) {
        this.USERMDFDTIME = USERMDFDTIME;
    }
}
