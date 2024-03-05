package com.pmis.payload.response;

import com.pmis.models.model.FunctionGrant;

import java.util.List;

public class UserInfoResponse extends AppResponse {

    private String userId;
    private String userName;
    private String descript;
    private List<String> roles;
    private List<FunctionGrant> fgrant;

    public UserInfoResponse(int status, String message) {
        super(status, message);
    }

    public UserInfoResponse(String userId, String userName, String descript, List<String> roles, List<FunctionGrant> fgrant, int status, String message) {
        super(status, message);
        this.userId = userId;
        this.userName = userName;
        this.descript = descript;
        this.roles = roles;
        this.fgrant = fgrant;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<FunctionGrant> getFgrant() {
        return fgrant;
    }

    public void setFgrant(List<FunctionGrant> fgrant) {
        this.fgrant = fgrant;
    }

}
