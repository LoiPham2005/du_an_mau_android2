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
import poly.edu.vn.du_an_mau_ph49806.model.PhieuMuon;

public class PhieuMuonDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    public static final String TABLE_NAME = "PhieuMuon";
    public static final String COLUMN_ID = "maPM";
    public static final String COLUMN_MATT = "maTT";
    public static final String COLUMN_MATV = "maTV";
    public static final String COLUMN_MASACH = "maSach";
    public static final String COLUMN_TIENTHUE = "tienThue";
    public static final String COLUMN_NGAY = "ngay";
    public static final String COLUMN_TRASACH = "traSach";

    public PhieuMuonDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Insert
    public long insertPhieuMuon(String maTT, int maTV, int maSach, int tienThue, String ngay, int traSach) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATT, maTT);
        values.put(COLUMN_MATV, maTV);
        values.put(COLUMN_MASACH, maSach);
        values.put(COLUMN_TIENTHUE, tienThue);
        values.put(COLUMN_NGAY, ngay);
        values.put(COLUMN_TRASACH, traSach);
        return db.insert(TABLE_NAME, null, values);
    }

    // Update
    public int updatePhieuMuon(int maPM, String maTT, int maTV, int maSach, int tienThue, String ngay, int traSach) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATT, maTT);
        values.put(COLUMN_MATV, maTV);
        values.put(COLUMN_MASACH, maSach);
        values.put(COLUMN_TIENTHUE, tienThue);
        values.put(COLUMN_NGAY, ngay);
        values.put(COLUMN_TRASACH, traSach);
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(maPM)});
    }

    // Delete
    public int deletePhieuMuon(int maPM) {
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(maPM)});
    }

    // Get All
    public List<PhieuMuon> getAllPhieuMuon() {
        List<PhieuMuon> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaPM(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                phieuMuon.setMaTT(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATT)));
                phieuMuon.setMaTV(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MATV)));
                phieuMuon.setMaSach(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MASACH)));
                phieuMuon.setTienThue(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TIENTHUE)));
                phieuMuon.setNgay(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY)));
                phieuMuon.setTraSach(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRASACH)));
                list.add(phieuMuon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Get by ID
    public PhieuMuon getPhieuMuonByID(int maPM) {
        PhieuMuon phieuMuon = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(maPM)});
        if (cursor.moveToFirst()) {
            phieuMuon = new PhieuMuon();
            phieuMuon.setMaPM(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            phieuMuon.setMaTT(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATT)));
            phieuMuon.setMaTV(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MATV)));
            phieuMuon.setMaSach(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MASACH)));
            phieuMuon.setTienThue(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TIENTHUE)));
            phieuMuon.setNgay(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY)));
            phieuMuon.setTraSach(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRASACH)));
        }
        cursor.close();
        return phieuMuon;
    }
}
