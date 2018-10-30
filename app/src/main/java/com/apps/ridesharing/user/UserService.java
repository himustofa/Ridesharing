package com.apps.ridesharing.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.apps.ridesharing.database.ConstantKey;
import com.apps.ridesharing.database.SQLiteDAO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class UserService {

    private SQLiteDAO dao;
    private ArrayList<UserModel> arrayList;
    private UserModel userModel;

    public UserService(Context context) {
        arrayList = new ArrayList<>();
        dao = new SQLiteDAO(context);
    }

    //Adding single object
    public long addData(UserModel model){
        final ContentValues values = new ContentValues();
        values.put(ConstantKey.COLUMN_ID, model.getUserId());
        values.put(ConstantKey.USER_COLUMN1, model.getUserMobileNumber());
        values.put(ConstantKey.USER_COLUMN2, model.getUserFullName());
        values.put(ConstantKey.USER_COLUMN3, model.getUserEmail());
        values.put(ConstantKey.USER_COLUMN4, model.getUserBirthDate());
        values.put(ConstantKey.USER_COLUMN5, model.getUserNid());
        values.put(ConstantKey.USER_COLUMN6, model.getUserGender());
        values.put(ConstantKey.USER_COLUMN7, model.getUserImageName());
        values.put(ConstantKey.USER_COLUMN8, model.getUserImagePath());
        values.put(ConstantKey.USER_COLUMN9, model.getUserLatitude());
        values.put(ConstantKey.USER_COLUMN10, model.getUserLongitude());
        values.put(ConstantKey.USER_COLUMN11, "created by kamal");
        values.put(ConstantKey.USER_COLUMN12, "");
        values.put(ConstantKey.USER_COLUMN13, String.valueOf(new Timestamp(System.currentTimeMillis())));

        return dao.addData(ConstantKey.USER_TABLE_NAME, values);
    }

    //Getting all objects
    public ArrayList<UserModel> getAllData(){
        arrayList = new ArrayList<>();
        Cursor cursor = dao.getAllData(ConstantKey.SELECT_USER_TABLE);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(ConstantKey.COLUMN_ID));
                String col1 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN1));
                String col2 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN2));
                String col3 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN3));
                String col4 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN4));
                String col5 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN5));
                String col6 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN6));
                String col7 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN7));
                String col8 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN8));
                String col9 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN9));
                String col10 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN10));
                String col11 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN11));
                String col12 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN12));
                String col13 = cursor.getString(cursor.getColumnIndex(ConstantKey.USER_COLUMN13));

                UserModel model = new UserModel(id,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12,col13);
                arrayList.add(model);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

    //Deleting single object
    public long deleteDataById(String id) {
        return dao.deleteDataById(ConstantKey.USER_TABLE_NAME, id);
    }

    //Updating single object
    public long updateDataById(UserModel model, String id) {
        ContentValues values = new ContentValues();
        values.put(ConstantKey.COLUMN_ID, model.getUserId());
        values.put(ConstantKey.USER_COLUMN1, model.getUserMobileNumber());
        values.put(ConstantKey.USER_COLUMN2, model.getUserFullName());
        values.put(ConstantKey.USER_COLUMN3, model.getUserEmail());
        values.put(ConstantKey.USER_COLUMN4, model.getUserBirthDate());
        values.put(ConstantKey.USER_COLUMN5, model.getUserNid());
        values.put(ConstantKey.USER_COLUMN6, model.getUserGender());
        values.put(ConstantKey.USER_COLUMN7, model.getUserImageName());
        values.put(ConstantKey.USER_COLUMN8, model.getUserImagePath());
        values.put(ConstantKey.USER_COLUMN9, model.getUserLatitude());
        values.put(ConstantKey.USER_COLUMN10, model.getUserLongitude());
        values.put(ConstantKey.USER_COLUMN11, "created by kamal");
        values.put(ConstantKey.USER_COLUMN12, "");
        values.put(ConstantKey.USER_COLUMN13, String.valueOf(new Timestamp(System.currentTimeMillis())));

        return dao.updateById(ConstantKey.USER_TABLE_NAME, values, id);
    }

    //Updating single object
    public long updateByMobile(UserModel model, String mobile) {
        ContentValues values = new ContentValues();
        values.put(ConstantKey.COLUMN_ID, model.getUserId());
        values.put(ConstantKey.USER_COLUMN1, model.getUserMobileNumber());
        values.put(ConstantKey.USER_COLUMN2, model.getUserFullName());
        values.put(ConstantKey.USER_COLUMN3, model.getUserEmail());
        values.put(ConstantKey.USER_COLUMN4, model.getUserBirthDate());
        values.put(ConstantKey.USER_COLUMN5, model.getUserNid());
        values.put(ConstantKey.USER_COLUMN6, model.getUserGender());
        values.put(ConstantKey.USER_COLUMN7, model.getUserImageName());
        values.put(ConstantKey.USER_COLUMN8, model.getUserImagePath());
        values.put(ConstantKey.USER_COLUMN9, model.getUserLatitude());
        values.put(ConstantKey.USER_COLUMN10, model.getUserLongitude());
        values.put(ConstantKey.USER_COLUMN11, "created by kamal");
        values.put(ConstantKey.USER_COLUMN12, "");
        values.put(ConstantKey.USER_COLUMN13, String.valueOf(new Timestamp(System.currentTimeMillis())));

        return dao.updateByMobile(ConstantKey.USER_TABLE_NAME, values, ConstantKey.USER_COLUMN1, mobile);
    }

    //Getting all objects
    public UserModel getDataById(String id){
        Cursor cursor = dao.getDataById(ConstantKey.USER_TABLE_NAME, id);
        if(cursor.getCount() > 0 && cursor != null) {
            cursor.moveToFirst();
            userModel = new UserModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13));
        }
        return userModel;
    }

    //Getting all objects
    public UserModel getDataByMobile(String mobile){
        Cursor cursor = dao.getDataByMobile(ConstantKey.USER_TABLE_NAME, ConstantKey.USER_COLUMN1, mobile);
        if(cursor.getCount() > 0 && cursor != null) {
            cursor.moveToFirst();
            userModel = new UserModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13));
        }
        return userModel;
    }

}
