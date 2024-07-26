package poly.edu.vn.du_an_mau_ph49806.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import poly.edu.vn.du_an_mau_ph49806.database.DBHelper;
import poly.edu.vn.du_an_mau_ph49806.model.ThanhVien;

public class ThanhVienDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    public static final String TABLE_NAME = "ThanhVien";
    public static final String COLUMN_ID = "maTV";
    public static final String COLUMN_HOTEN = "hoTen";
    public static final String COLUMN_NAMSINH = "namSinh";

    public ThanhVienDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Insert
    public long insertThanhVien(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEN, thanhVien.getHoTen());
        values.put(COLUMN_NAMSINH, thanhVien.getNamSinh());
        return db.insert(TABLE_NAME, null, values);
    }

    // Update
    public int updateThanhVien(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEN, thanhVien.getHoTen());
        values.put(COLUMN_NAMSINH, thanhVien.getNamSinh());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(thanhVien.getMaTV())});
    }

    // Delete
    public boolean deleteThanhVien(int maTV) {
        long check = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(maTV)});
        return check != -1;
    }

    // Get All
    public ArrayList<ThanhVien> getAllThanhVien() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThanhVien" , null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            }while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    // Get by ID
    public ThanhVien getThanhVienByID(int maTV) {
        ThanhVien thanhVien = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(maTV)});
        if (cursor.moveToFirst()) {
            thanhVien = new ThanhVien();
            thanhVien.setMaTV(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            thanhVien.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOTEN)));
            thanhVien.setNamSinh(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMSINH)));
        }
        cursor.close();
        return thanhVien;
    }
}
