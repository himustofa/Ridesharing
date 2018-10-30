package com.apps.ridesharing.database;

public class ConstantKey {

    public final static String COLUMN_ID = "id";
    public final static String USER_LOGIN_KEY = "loginInfo";
    public final static String USER_IS_LOGGED_KEY = "isLoggedIn";
    public final static String USER_MOBILE_KEY = "userMobile";

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



}
