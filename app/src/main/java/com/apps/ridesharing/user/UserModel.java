package com.apps.ridesharing.user;

public class UserModel {

    private String userId;
    private String userMobileNumber;
    private String userFullName;
    private String userEmail;
    private String userBirthDate;
    private String userNid;
    private String userGender;
    private String userImageName;
    private String userImagePath;
    private String userLatitude;
    private String userLongitude ;
    private String createdById;
    private String updatedById;
    private String createdAt;

    public UserModel(String userId, String userMobileNumber, String userFullName, String userEmail, String userBirthDate, String userNid, String userGender, String userImageName, String userImagePath, String userLatitude, String userLongitude, String createdById, String updatedById, String createdAt) {
        this.userId = userId;
        this.userMobileNumber = userMobileNumber;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userBirthDate = userBirthDate;
        this.userNid = userNid;
        this.userGender = userGender;
        this.userImageName = userImageName;
        this.userImagePath = userImagePath;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
    }

    public UserModel(String userId, String userMobileNumber, String userFullName, String userEmail, String userBirthDate, String userNid, String userGender, String userImageName, String userImagePath, String userLatitude, String userLongitude) {
        this.userId = userId;
        this.userMobileNumber = userMobileNumber;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userBirthDate = userBirthDate;
        this.userNid = userNid;
        this.userGender = userGender;
        this.userImageName = userImageName;
        this.userImagePath = userImagePath;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(String userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    public String getUserNid() {
        return userNid;
    }

    public void setUserNid(String userNid) {
        this.userNid = userNid;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserImageName() {
        return userImageName;
    }

    public void setUserImageName(String userImageName) {
        this.userImageName = userImageName;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(String updatedById) {
        this.updatedById = updatedById;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
