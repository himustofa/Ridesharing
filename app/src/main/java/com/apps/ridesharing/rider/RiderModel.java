package com.apps.ridesharing.rider;

public class RiderModel {

    private String riderId;
    private String riderMobileNumber;
    private String riderPassword;
    private String riderFullName;
    private String riderEmail;
    private String riderBirthDate;
    private String riderNid;
    private String riderGender;
    private String riderDistrict;
    private String riderVehicle;
    private String riderLicense;
    private String riderVehicleNo;
    private String riderImageName;
    private String riderImagePath;
    private String riderLatitude;
    private String riderLongitude ;
    private String createdById;
    private String updatedById;
    private String createdAt;

    public RiderModel(String riderId, String riderMobileNumber, String riderPassword, String riderFullName, String riderEmail, String riderBirthDate, String riderNid, String riderGender, String riderDistrict, String riderVehicle, String riderLicense, String riderVehicleNo, String riderImageName, String riderImagePath, String riderLatitude, String riderLongitude, String createdById, String updatedById, String createdAt) {
        this.riderId = riderId;
        this.riderMobileNumber = riderMobileNumber;
        this.riderPassword = riderPassword;
        this.riderFullName = riderFullName;
        this.riderEmail = riderEmail;
        this.riderBirthDate = riderBirthDate;
        this.riderNid = riderNid;
        this.riderGender = riderGender;
        this.riderDistrict = riderDistrict;
        this.riderVehicle = riderVehicle;
        this.riderLicense = riderLicense;
        this.riderVehicleNo = riderVehicleNo;
        this.riderImageName = riderImageName;
        this.riderImagePath = riderImagePath;
        this.riderLatitude = riderLatitude;
        this.riderLongitude = riderLongitude;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
    }

    public RiderModel(String riderId, String riderMobileNumber, String riderFullName, String riderEmail, String riderBirthDate, String riderNid, String riderGender, String riderDistrict, String riderVehicle, String riderLicense, String riderVehicleNo, String riderImageName, String riderImagePath, String riderLatitude, String riderLongitude) {
        this.riderId = riderId;
        this.riderMobileNumber = riderMobileNumber;
        this.riderFullName = riderFullName;
        this.riderEmail = riderEmail;
        this.riderBirthDate = riderBirthDate;
        this.riderNid = riderNid;
        this.riderGender = riderGender;
        this.riderDistrict = riderDistrict;
        this.riderVehicle = riderVehicle;
        this.riderLicense = riderLicense;
        this.riderVehicleNo = riderVehicleNo;
        this.riderImageName = riderImageName;
        this.riderImagePath = riderImagePath;
        this.riderLatitude = riderLatitude;
        this.riderLongitude = riderLongitude;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getRiderMobileNumber() {
        return riderMobileNumber;
    }

    public void setRiderMobileNumber(String riderMobileNumber) {
        this.riderMobileNumber = riderMobileNumber;
    }

    public String getRiderPassword() {
        return riderPassword;
    }

    public void setRiderPassword(String riderPassword) {
        this.riderPassword = riderPassword;
    }

    public String getRiderFullName() {
        return riderFullName;
    }

    public void setRiderFullName(String riderFullName) {
        this.riderFullName = riderFullName;
    }

    public String getRiderEmail() {
        return riderEmail;
    }

    public void setRiderEmail(String riderEmail) {
        this.riderEmail = riderEmail;
    }

    public String getRiderBirthDate() {
        return riderBirthDate;
    }

    public void setRiderBirthDate(String riderBirthDate) {
        this.riderBirthDate = riderBirthDate;
    }

    public String getRiderNid() {
        return riderNid;
    }

    public void setRiderNid(String riderNid) {
        this.riderNid = riderNid;
    }

    public String getRiderGender() {
        return riderGender;
    }

    public void setRiderGender(String riderGender) {
        this.riderGender = riderGender;
    }

    public String getRiderDistrict() {
        return riderDistrict;
    }

    public void setRiderDistrict(String riderDistrict) {
        this.riderDistrict = riderDistrict;
    }

    public String getRiderVehicle() {
        return riderVehicle;
    }

    public void setRiderVehicle(String riderVehicle) {
        this.riderVehicle = riderVehicle;
    }

    public String getRiderLicense() {
        return riderLicense;
    }

    public void setRiderLicense(String riderLicense) {
        this.riderLicense = riderLicense;
    }

    public String getRiderVehicleNo() {
        return riderVehicleNo;
    }

    public void setRiderVehicleNo(String riderVehicleNo) {
        this.riderVehicleNo = riderVehicleNo;
    }

    public String getRiderImageName() {
        return riderImageName;
    }

    public void setRiderImageName(String riderImageName) {
        this.riderImageName = riderImageName;
    }

    public String getRiderImagePath() {
        return riderImagePath;
    }

    public void setRiderImagePath(String riderImagePath) {
        this.riderImagePath = riderImagePath;
    }

    public String getRiderLatitude() {
        return riderLatitude;
    }

    public void setRiderLatitude(String riderLatitude) {
        this.riderLatitude = riderLatitude;
    }

    public String getRiderLongitude() {
        return riderLongitude;
    }

    public void setRiderLongitude(String riderLongitude) {
        this.riderLongitude = riderLongitude;
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
