package com.apps.ridesharing.user;

public class UserModel {

    private String userId;
    private String userName;
    private String userRelation;
    private String userOccupation;
    private String userEmail;
    private String userMobile;
    private String userNid;
    private String userAddress;
    private String userImage;
    private String userImagePath;
    private String isUserOwner;
    private String createdById;
    private String updatedById;
    private String createdAt;

    public UserModel(String userId, String userName, String userRelation, String userOccupation, String userEmail, String userMobile, String userNid, String userAddress, String userImage, String userImagePath, String isUserOwner, String createdById, String updatedById, String createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.userRelation = userRelation;
        this.userOccupation = userOccupation;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.userNid = userNid;
        this.userAddress = userAddress;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
        this.isUserOwner = isUserOwner;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
    }

    public UserModel(String userName, String userRelation, String userOccupation, String userEmail, String userMobile, String userNid, String userAddress, String userImage, String userImagePath, String isUserOwner, String createdById, String updatedById, String createdAt) {
        this.userName = userName;
        this.userRelation = userRelation;
        this.userOccupation = userOccupation;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.userNid = userNid;
        this.userAddress = userAddress;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
        this.isUserOwner = isUserOwner;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
    }

    public UserModel(String userId, String userName, String userRelation, String userOccupation, String userEmail, String userMobile, String userNid, String userAddress, String userImage, String userImagePath, String isUserOwner) {
        this.userId = userId;
        this.userName = userName;
        this.userRelation = userRelation;
        this.userOccupation = userOccupation;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.userNid = userNid;
        this.userAddress = userAddress;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
        this.isUserOwner = isUserOwner;
    }

    public UserModel(String userName, String userRelation, String userOccupation, String userEmail, String userMobile, String userNid, String userAddress, String userImage, String userImagePath, String isUserOwner) {
        this.userName = userName;
        this.userRelation = userRelation;
        this.userOccupation = userOccupation;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.userNid = userNid;
        this.userAddress = userAddress;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
        this.isUserOwner = isUserOwner;
    }

    public UserModel(String userName, String userMobile, String isUserOwner) {
        this.userName = userName;
        this.userMobile = userMobile;
        this.isUserOwner = isUserOwner;
    }

    public UserModel(String userId, String userName, String userMobile, String isUserOwner) {
        this.userId = userId;
        this.userName = userName;
        this.userMobile = userMobile;
        this.isUserOwner = isUserOwner;
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

    public String getUserRelation() {
        return userRelation;
    }

    public void setUserRelation(String userRelation) {
        this.userRelation = userRelation;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserNid() {
        return userNid;
    }

    public void setUserNid(String userNid) {
        this.userNid = userNid;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public String getIsUserOwner() {
        return isUserOwner;
    }

    public void setIsUserOwner(String isUserOwner) {
        this.isUserOwner = isUserOwner;
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
