package com.cjh.common.shared.vo;

public class LoginUserSession {

    private String  userId;
    private String  userName;
    private String  userBizCode;
    private String  userBizName;
    private String  loginDateTime;
    private String  UserRole;
    private String  UserRoleName;
    private String  departmentId;
    private String  departmentName;
    private String departmentIdL1;
    private String departmentNameL1;
    private String  ipAddress;

    public String getLoginDateTime() {
        return loginDateTime;
    }
    public void setLoginDateTime(String loginDateTime) {
        this.loginDateTime = loginDateTime;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserBizCode() {
        return userBizCode;
    }
    public void setUserBizCode(String userBizCode) {
        this.userBizCode = userBizCode;
    }
	public String getUserBizName() {
        return userBizName;
    }
    public void setUserBizName(String userBizName) {
        this.userBizName = userBizName;
    }
    public String getUserRole() {
        return UserRole;
    }
    public void setUserRole(String userRole) {
        UserRole = userRole;
    }
    public String getUserRoleName() {
        return UserRoleName;
    }
    public void setUserRoleName(String userRoleName) {
        UserRoleName = userRoleName;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getDepartmentIdL1() {
        return departmentIdL1;
    }
    public void setDepartmentIdL1(String departmentIdL1) {
        this.departmentIdL1 = departmentIdL1;
    }
    public String getDepartmentNameL1() {
        return departmentNameL1;
    }
    public void setDepartmentNameL1(String departmentNameL1) {
        this.departmentNameL1 = departmentNameL1;
    }
}
