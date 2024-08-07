package poly.edu.vn.du_an_mau_ph49806.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import poly.edu.vn.du_an_mau_ph49806.database.DBHelper;
import poly.edu.vn.du_an_mau_ph49806.model.Sach;

public class SachDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    public static final String TABLE_NAME = "Sach";
    public static final String COLUMN_ID = "maSach";
    public static final String COLUMN_TENSACH = "tenSach";
    public static final String COLUMN_GIATHUE = "giaThue";
    public static final String COLUMN_MALOAI = "maLoai";

    public SachDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Insert
    public long insertSach(Sach sach) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENSACH, sach.getTenSach());
        values.put(COLUMN_GIATHUE, sach.getGiaThue());
        values.put(COLUMN_MALOAI, sach.getMaLoai());
        return db.insert(TABLE_NAME, null, values);
    }

    // Update
    public int updateSach(Sach sach) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENSACH, sach.getTenSach());
        values.put(COLUMN_GIATHUE, sach.getGiaThue());
        values.put(COLUMN_MALOAI, sach.getMaLoai());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(sach.getMaSach())});
    }

    // Delete
    public int deleteSach(int maSach) {
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(maSach)});
    }

    // Get All
    public List<Sach> getAllSach() {
        List<Sach> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                sach.setTenSach(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TENSACH)));
                sach.setGiaThue(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GIATHUE)));
                sach.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MALOAI)));
                list.add(sach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Thêm phương thức này vào SachDAO để lấy tất cả các loại sách
    public List<String> getAllLoaiSach() {
        List<String> list = new ArrayList<>();
        String selectQuery = "SELECT tenLoai FROM LoaiSach";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndexOrThrow("tenLoai")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public String getLoaiSachName(int maLoai) {
        String tenLoaiSach = "";
        // Truy vấn tên loại sách từ database dựa trên maLoai
//        SQLiteDatabase db = this.getReadableDatabase();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tenLoai FROM LoaiSach WHERE maLoai=?", new String[]{String.valueOf(maLoai)});
        if (cursor.moveToFirst()) {
            tenLoaiSach = cursor.getString(0);
        }
        cursor.close();
        return tenLoaiSach;
    }

    public int getMaLoaiByName(String tenLoai) {
        int maLoai = -1;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maLoai FROM LoaiSach WHERE tenLoai=?", new String[]{tenLoai});
        if (cursor.moveToFirst()) {
            maLoai = cursor.getInt(0);
        }
        cursor.close();
        return maLoai;
    }
}
