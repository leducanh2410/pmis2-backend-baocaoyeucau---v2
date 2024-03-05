package com.pmis.models.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class S_Organization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orgid;

	private String orgName;

	private int ord;

	private String loaiHinh;
	
	@JsonIgnore
	private String urlWsQlkt;
	
	@JsonIgnore
	private String ws_username;
	
	@JsonIgnore
	private String ws_password;

	public S_Organization() {
	}

	public S_Organization(String orgid, String orgName, Integer ord,String _loaiHinh) {
		this.orgid = orgid;
		this.orgName = orgName;
		this.ord = ord;
		this.loaiHinh = _loaiHinh;
	}

	public S_Organization(String orgid, String orgName, Integer ord) {
		this.orgid = orgid;
		this.orgName = orgName;
		this.ord = ord;
	}
	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public String getLoaiHinh() {
		return loaiHinh;
	}

	public void setLoaiHinh(String loaiHinh) {
		this.loaiHinh = loaiHinh;
	}

	public String getUrlWsQlkt() {
		return urlWsQlkt;
	}

	public void setUrlWsQlkt(String urlWsQlkt) {
		this.urlWsQlkt = urlWsQlkt;
	}

	public String getWs_username() {
		return ws_username;
	}

	public void setWs_username(String ws_username) {
		this.ws_username = ws_username;
	}

	public String getWs_password() {
		return ws_password;
	}

	public void setWs_password(String ws_password) {
		this.ws_password = ws_password;
	}
	
	

}
