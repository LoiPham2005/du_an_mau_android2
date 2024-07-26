package poly.edu.vn.du_an_mau_ph49806.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import poly.edu.vn.du_an_mau_ph49806.database.DBHelper;
import poly.edu.vn.du_an_mau_ph49806.model.LoaiSach;

public class LoaiSachDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    public static final String TABLE_NAME = "LoaiSach";
    public static final String COLUMN_ID = "maLoai";
    public static final String COLUMN_TENLOAI = "tenLoai";

    public LoaiSachDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Insert
    public long insertLoaiSach(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENLOAI, loaiSach.getTenLoai());
        return db.insert(TABLE_NAME, null, values);
    }

    // Update
    public int updateLoaiSach(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENLOAI, loaiSach.getTenLoai());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(loaiSach.getMaLoai())});
    }

    // Delete
    public int deleteLoaiSach(int maLoai) {
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(maLoai)});
    }

    // Get All
    public List<LoaiSach> getAllLoaiSach() {
        List<LoaiSach> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                loaiSach.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TENLOAI)));
                list.add(loaiSach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Get by ID
    public LoaiSach getLoaiSachByID(int maLoai) {
        LoaiSach loaiSach = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(maLoai)});
        if (cursor.moveToFirst()) {
            loaiSach = new LoaiSach();
            loaiSach.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            loaiSach.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TENLOAI)));
        }
        cursor.close();
        return loaiSach;
    }
}
