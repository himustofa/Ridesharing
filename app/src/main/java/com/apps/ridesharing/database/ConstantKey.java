package com.apps.ridesharing.database;

public class ConstantKey {

    public final static String COLUMN_ID = "id";
    public final static String USER_LOGIN_KEY = "userLoginInfo";
    public final static String USER_IS_LOGGED_KEY = "isUserLoggedIn";
    public final static String USER_MOBILE_KEY = "userMobile";
    public final static String RIDER_LOGIN_KEY = "riderLoginInfo";
    public final static String RIDER_IS_LOGGED_KEY = "isRiderLoggedIn";
    public final static String RIDER_MOBILE_KEY = "riderMobile";

    //=======================| UserModel |=======================
    public final static String USER_TABLE_NAME = "users";
    public final static String USER_COLUMN1 = "user_mobile_number";
    public final static String USER_COLUMN2 = "user_full_name";
    public final static String USER_COLUMN3 = "user_email";
    public final static String USER_COLUMN4 = "user_birth_date";
    public final static String USER_COLUMN5 = "user_nid";
    public final static String USER_COLUMN6 = "user_gender";
    public final static String USER_COLUMN7 = "user_image_name";
    public final static String USER_COLUMN8 = "user_image_path";
    public final static String USER_COLUMN9 = "user_latitude";
    public final static String USER_COLUMN10 = "user_longitude";
    public final static String USER_COLUMN11 = "created_by_id";
    public final static String USER_COLUMN12 = "updated_by_id";
    public final static String USER_COLUMN13 = "created_at";

    protected final static String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + " (" + COLUMN_ID + " TEXT, " + USER_COLUMN1 + " TEXT, " + USER_COLUMN2 + " TEXT, " + USER_COLUMN3 + " TEXT, " + USER_COLUMN4 + " TEXT, " + USER_COLUMN5 + " TEXT, " + USER_COLUMN6 + " TEXT, " + USER_COLUMN7 + " TEXT, " + USER_COLUMN8 + " TEXT, " +  USER_COLUMN9 + " TEXT, " +  USER_COLUMN10 + " TEXT, " +  USER_COLUMN11 + " TEXT, " + USER_COLUMN12 + " TEXT, " + USER_COLUMN13 + " TEXT ) ";
    protected final static String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + " ";
    public final static String SELECT_USER_TABLE = "SELECT * FROM " + USER_TABLE_NAME;

    public final static String INSERT_USER_DATA1 = "INSERT INTO users (name, email, mobile, address, image, path, is_owner, created_by_id, updated_by_id, created_at) VALUES ('admin', 'mustofa2008@gmail.com', '01914141707', 'Dhaka Cantonment', '', '', 'Owner', 'created by kamal', '', '2018-09-18 00:05:30.729');";

    //=======================| RiderModel |=======================
    public final static String RIDER_TABLE_NAME = "riders";
    public final static String RIDER_COLUMN1 = "rider_mobile_number";
    public final static String RIDER_COLUMN2 = "rider_password";
    public final static String RIDER_COLUMN3 = "rider_full_name";
    public final static String RIDER_COLUMN4 = "rider_email";
    public final static String RIDER_COLUMN5 = "rider_birth_date";
    public final static String RIDER_COLUMN6 = "rider_nid";
    public final static String RIDER_COLUMN7 = "rider_gender";
    public final static String RIDER_COLUMN8 = "rider_district";
    public final static String RIDER_COLUMN9 = "rider_vehicle";
    public final static String RIDER_COLUMN10 = "rider_license";
    public final static String RIDER_COLUMN11 = "rider_vehicle_no";
    public final static String RIDER_COLUMN12 = "rider_image_name";
    public final static String RIDER_COLUMN13 = "rider_image_path";
    public final static String RIDER_COLUMN14 = "rider_latitude";
    public final static String RIDER_COLUMN15 = "rider_longitude";
    public final static String RIDER_COLUMN16 = "created_by_id";
    public final static String RIDER_COLUMN17 = "updated_by_id";
    public final static String RIDER_COLUMN18 = "created_at";

    protected final static String CREATE_RIDER_TABLE = "CREATE TABLE " + RIDER_TABLE_NAME + " (" + COLUMN_ID + " TEXT, " + RIDER_COLUMN1 + " TEXT, " + RIDER_COLUMN2 + " TEXT, " + RIDER_COLUMN3 + " TEXT, " + RIDER_COLUMN4 + " TEXT, " + RIDER_COLUMN5 + " TEXT, " + RIDER_COLUMN6 + " TEXT, " + RIDER_COLUMN7 + " TEXT, " + RIDER_COLUMN8 + " TEXT, " +  RIDER_COLUMN9 + " TEXT, " +  RIDER_COLUMN10 + " TEXT, " +  RIDER_COLUMN11 + " TEXT, " + RIDER_COLUMN12 + " TEXT, " + RIDER_COLUMN13 + " TEXT, " + RIDER_COLUMN14 + " TEXT, " + RIDER_COLUMN15 + " TEXT, " + RIDER_COLUMN16 + " TEXT, " + RIDER_COLUMN17 + " TEXT, " + RIDER_COLUMN18 + " TEXT ) ";
    protected final static String DROP_RIDER_TABLE = "DROP TABLE IF EXISTS " + RIDER_TABLE_NAME + " ";
    public final static String SELECT_RIDER_TABLE = "SELECT * FROM " + RIDER_TABLE_NAME;



}
