package com.pmis.models.model;

import java.util.Date;
import java.util.List;

public class Q_User {

    private String userid;

    private String username;

    private String password;

    private String descript;

    private String mobile;

    private String email;

    private String officeid;

    private String userid_domain;

    private String domainid;

    private Boolean enable;

    private String userCrId;

    private Date userCrDate;

    private String userMdfId;

    private Date userMdfDate;

    private List<Q_Role> roles;

    private String evnidUserName;

    private String orgid;// Don vi cua nguoi dung

    private String orgid_client_view;// Don vi nguoi dung xem du lieu

    private String loaiHinhDonVi;// Dung cho mobile

    private String tenCongTy;

    public Q_User() {
    }

    public Q_User(String userid, String username, String password) {
        this.userid = userid;
        this.username = username;
        this.password = password;
    }

    public Q_User(String userid, String username, String password, String descript, String mobile, String email,
                  String officeid, String userid_domain, String domainid, Boolean enable, String userCrId, Date userCrDate,
                  String userMdfId, Date userMdfDate, String orgid, String evnidUserName, List<Q_Role> roles) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.descript = descript;
        this.mobile = mobile;
        this.email = email;
        this.officeid = officeid;
        this.userid_domain = userid_domain;
        this.domainid = domainid;
        this.enable = enable;
        this.userCrId = userCrId;
        this.userCrDate = userCrDate;
        this.userMdfId = userMdfId;
        this.userMdfDate = userMdfDate;
        this.evnidUserName = evnidUserName;
        this.orgid = orgid;
        this.roles = roles;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeid() {
        return officeid;
    }

    public void setOfficeid(String officeid) {
        this.officeid = officeid;
    }

    public String getUserid_domain() {
        return userid_domain;
    }

    public void setUserid_domain(String userid_domain) {
        this.userid_domain = userid_domain;
    }

    public String getDomainid() {
        return domainid;
    }

    public void setDomainid(String domainid) {
        this.domainid = domainid;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getUserCrId() {
        return userCrId;
    }

    public void setUserCrId(String userCrId) {
        this.userCrId = userCrId;
    }

    public Date getUserCrDate() {
        return userCrDate;
    }

    public void setUserCrDate(Date userCrDate) {
        this.userCrDate = userCrDate;
    }

    public String getUserMdfId() {
        return userMdfId;
    }

    public void setUserMdfId(String userMdfId) {
        this.userMdfId = userMdfId;
    }

    public Date getUserMdfDate() {
        return userMdfDate;
    }

    public void setUserMdfDate(Date userMdfDate) {
        this.userMdfDate = userMdfDate;
    }

    public List<Q_Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Q_Role> roles) {
        this.roles = roles;
    }

    public String getEvnidUserName() {
        return evnidUserName;
    }

    public void setEvnidUserName(String evnidUserName) {
        this.evnidUserName = evnidUserName;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgid_client_view() {
        return orgid_client_view;
    }

    public void setOrgid_client_view(String orgid_client_view) {
        this.orgid_client_view = orgid_client_view;
    }

    public String getLoaiHinhDonVi() {
        return loaiHinhDonVi;
    }

    public void setLoaiHinhDonVi(String loaiHinhDonVi) {
        this.loaiHinhDonVi = loaiHinhDonVi;
    }

    public String getTenCongTy() {
        return tenCongTy;
    }

    public void setTenCongTy(String tenCongTy) {
        this.tenCongTy = tenCongTy;
    }
}
