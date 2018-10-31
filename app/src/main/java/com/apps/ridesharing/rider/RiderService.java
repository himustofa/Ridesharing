package com.apps.ridesharing.rider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.apps.ridesharing.database.ConstantKey;
import com.apps.ridesharing.database.SQLiteDAO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class RiderService {

    private SQLiteDAO dao;
    private ArrayList<RiderModel> arrayList;
    private RiderModel riderModel;

    public RiderService(Context context) {
        arrayList = new ArrayList<>();
        dao = new SQLiteDAO(context);
    }

    //Adding single object
    public long addData(RiderModel model){
        final ContentValues values = new ContentValues();
        values.put(ConstantKey.COLUMN_ID, model.getRiderId());
        values.put(ConstantKey.RIDER_COLUMN1, model.getRiderMobileNumber());
        values.put(ConstantKey.RIDER_COLUMN2, model.getRiderPassword());
        values.put(ConstantKey.RIDER_COLUMN3, model.getRiderFullName());
        values.put(ConstantKey.RIDER_COLUMN4, model.getRiderEmail());
        values.put(ConstantKey.RIDER_COLUMN5, model.getRiderBirthDate());
        values.put(ConstantKey.RIDER_COLUMN6, model.getRiderNid());
        values.put(ConstantKey.RIDER_COLUMN7, model.getRiderGender());
        values.put(ConstantKey.RIDER_COLUMN8, model.getRiderDistrict());
        values.put(ConstantKey.RIDER_COLUMN9, model.getRiderVehicle());
        values.put(ConstantKey.RIDER_COLUMN10, model.getRiderLicense());
        values.put(ConstantKey.RIDER_COLUMN11, model.getRiderVehicleNo());
        values.put(ConstantKey.RIDER_COLUMN12, model.getRiderImageName());
        values.put(ConstantKey.RIDER_COLUMN13, model.getRiderImagePath());
        values.put(ConstantKey.RIDER_COLUMN14, model.getRiderLatitude());
        values.put(ConstantKey.RIDER_COLUMN15, model.getRiderLongitude());
        values.put(ConstantKey.RIDER_COLUMN16, "created by kamal");
        values.put(ConstantKey.RIDER_COLUMN17, "");
        values.put(ConstantKey.RIDER_COLUMN18, String.valueOf(new Timestamp(System.currentTimeMillis())));

        return dao.addData(ConstantKey.RIDER_TABLE_NAME, values);
    }

    //Getting all objects
    public ArrayList<RiderModel> getAllData(){
        arrayList = new ArrayList<>();
        Cursor cursor = dao.getAllData(ConstantKey.SELECT_RIDER_TABLE);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(ConstantKey.COLUMN_ID));
                String col1 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN1));
                String col2 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN2));
                String col3 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN3));
                String col4 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN4));
                String col5 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN5));
                String col6 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN6));
                String col7 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN7));
                String col8 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN8));
                String col9 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN9));
                String col10 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN10));
                String col11 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN11));
                String col12 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN12));
                String col13 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN13));
                String col14 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN14));
                String col15 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN15));
                String col16 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN16));
                String col17 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN17));
                String col18 = cursor.getString(cursor.getColumnIndex(ConstantKey.RIDER_COLUMN18));

                RiderModel model = new RiderModel(id,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12,col13,col14,col15,col16,col17,col18);
                arrayList.add(model);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

    //Deleting single object
    public long deleteDataById(String id) {
        return dao.deleteDataById(ConstantKey.RIDER_TABLE_NAME, id);
    }

    //Updating single object
    public long updateDataById(RiderModel model, String id) {
        ContentValues values = new ContentValues();
        values.put(ConstantKey.COLUMN_ID, model.getRiderId());
        values.put(ConstantKey.RIDER_COLUMN1, model.getRiderMobileNumber());
        values.put(ConstantKey.RIDER_COLUMN2, model.getRiderPassword());
        values.put(ConstantKey.RIDER_COLUMN3, model.getRiderFullName());
        values.put(ConstantKey.RIDER_COLUMN4, model.getRiderEmail());
        values.put(ConstantKey.RIDER_COLUMN5, model.getRiderBirthDate());
        values.put(ConstantKey.RIDER_COLUMN6, model.getRiderNid());
        values.put(ConstantKey.RIDER_COLUMN7, model.getRiderGender());
        values.put(ConstantKey.RIDER_COLUMN8, model.getRiderDistrict());
        values.put(ConstantKey.RIDER_COLUMN9, model.getRiderVehicle());
        values.put(ConstantKey.RIDER_COLUMN10, model.getRiderLicense());
        values.put(ConstantKey.RIDER_COLUMN11, model.getRiderVehicleNo());
        values.put(ConstantKey.RIDER_COLUMN12, model.getRiderImageName());
        values.put(ConstantKey.RIDER_COLUMN13, model.getRiderImagePath());
        values.put(ConstantKey.RIDER_COLUMN14, model.getRiderLatitude());
        values.put(ConstantKey.RIDER_COLUMN15, model.getRiderLongitude());
        values.put(ConstantKey.RIDER_COLUMN16, "created by kamal");
        values.put(ConstantKey.RIDER_COLUMN17, "");
        values.put(ConstantKey.RIDER_COLUMN18, String.valueOf(new Timestamp(System.currentTimeMillis())));

        return dao.updateById(ConstantKey.RIDER_TABLE_NAME, values, id);
    }

    //Updating single object
    public long updateByMobile(RiderModel model, String mobile) {
        ContentValues values = new ContentValues();
        values.put(ConstantKey.COLUMN_ID, model.getRiderId());
        values.put(ConstantKey.RIDER_COLUMN1, model.getRiderMobileNumber());
        values.put(ConstantKey.RIDER_COLUMN2, model.getRiderPassword());
        values.put(ConstantKey.RIDER_COLUMN3, model.getRiderFullName());
        values.put(ConstantKey.RIDER_COLUMN4, model.getRiderEmail());
        values.put(ConstantKey.RIDER_COLUMN5, model.getRiderBirthDate());
        values.put(ConstantKey.RIDER_COLUMN6, model.getRiderNid());
        values.put(ConstantKey.RIDER_COLUMN7, model.getRiderGender());
        values.put(ConstantKey.RIDER_COLUMN8, model.getRiderDistrict());
        values.put(ConstantKey.RIDER_COLUMN9, model.getRiderVehicle());
        values.put(ConstantKey.RIDER_COLUMN10, model.getRiderLicense());
        values.put(ConstantKey.RIDER_COLUMN11, model.getRiderVehicleNo());
        values.put(ConstantKey.RIDER_COLUMN12, model.getRiderImageName());
        values.put(ConstantKey.RIDER_COLUMN13, model.getRiderImagePath());
        values.put(ConstantKey.RIDER_COLUMN14, model.getRiderLatitude());
        values.put(ConstantKey.RIDER_COLUMN15, model.getRiderLongitude());
        values.put(ConstantKey.RIDER_COLUMN16, "created by kamal");
        values.put(ConstantKey.RIDER_COLUMN17, "");
        values.put(ConstantKey.RIDER_COLUMN18, String.valueOf(new Timestamp(System.currentTimeMillis())));

        return dao.updateByMobile(ConstantKey.RIDER_TABLE_NAME, values, ConstantKey.RIDER_COLUMN1, mobile);
    }

    //Getting all objects
    public RiderModel getDataById(String id){
        Cursor cursor = dao.getDataById(ConstantKey.RIDER_TABLE_NAME, id);
        if(cursor.getCount() > 0 && cursor != null) {
            cursor.moveToFirst();
            riderModel = new RiderModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15),cursor.getString(16),cursor.getString(17),cursor.getString(18));
        }
        return riderModel;
    }

    //Getting all objects
    public RiderModel getDataByMobile(String mobile){
        Cursor cursor = dao.getDataByMobile(ConstantKey.RIDER_TABLE_NAME, ConstantKey.RIDER_COLUMN1, mobile);
        if(cursor.getCount() > 0 && cursor != null) {
            cursor.moveToFirst();
            riderModel = new RiderModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15),cursor.getString(16),cursor.getString(17),cursor.getString(18));
        }
        return riderModel;
    }

}
