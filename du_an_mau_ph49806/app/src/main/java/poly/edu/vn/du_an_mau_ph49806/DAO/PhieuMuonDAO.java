package poly.edu.vn.du_an_mau_ph49806.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import poly.edu.vn.du_an_mau_ph49806.database.DBHelper;
import poly.edu.vn.du_an_mau_ph49806.model.PhieuMuon;
import poly.edu.vn.du_an_mau_ph49806.model.Top10;

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
    public long insertPhieuMuon(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATT, phieuMuon.getMaTT());
        values.put(COLUMN_MATV, phieuMuon.getMaTV());
        values.put(COLUMN_MASACH, phieuMuon.getMaSach());
        values.put(COLUMN_TIENTHUE, phieuMuon.getTienThue());
        values.put(COLUMN_NGAY, phieuMuon.getNgay());
        values.put(COLUMN_TRASACH, phieuMuon.getTraSach());
        return db.insert(TABLE_NAME, null, values);
    }

    // Update
    public int updatePhieuMuon(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATT, phieuMuon.getMaTT());
        values.put(COLUMN_MATV, phieuMuon.getMaTV());
        values.put(COLUMN_MASACH, phieuMuon.getMaSach());
        values.put(COLUMN_TIENTHUE, phieuMuon.getTienThue());
        values.put(COLUMN_NGAY, phieuMuon.getNgay());
        values.put(COLUMN_TRASACH, phieuMuon.getTraSach());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(phieuMuon.getMaPM())});
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

    public List<String> getAllThanhVien() {
        List<String> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT hoTen FROM ThanhVien", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndexOrThrow("hoTen")));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<String> getAllSach() {
        List<String> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tenSach FROM Sach", null);
        if (cursor.moveToFirst()) {
            do {
//                list.add(cursor.getString(cursor.getColumnIndexOrThrow("tenSach")));
                list.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        return list;
    }

    public int getThanhVienByName(String thanhVien) {
        int idThanhVien = -1;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maTV FROM ThanhVien WHERE hoTen=?", new String[]{thanhVien});
        if (cursor.moveToFirst()) {
            idThanhVien = cursor.getInt(0);
        }
        cursor.close();
        return idThanhVien;
    }

    public int getSachByName(String tenSach) {
        int idSach = -1;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maSach FROM Sach WHERE tenSach=?", new String[]{tenSach});
        if (cursor.moveToFirst()) {
            idSach = cursor.getInt(0);
        }
        cursor.close();
        return idSach;
    }

    public int getSachPriceByName(String tenSach) {
        int giaThue = -1; // Giá mặc định nếu không tìm thấy sách
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT giaThue FROM Sach WHERE tenSach=?", new String[]{tenSach});
        if (cursor.moveToFirst()) {
            giaThue = cursor.getInt(cursor.getColumnIndexOrThrow("giaThue"));
        }
        cursor.close();
        return giaThue;
    }

    public int getSachPriceById(int maSach) {
        int giaThue = -1; // Giá mặc định nếu không tìm thấy sách
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT giaThue FROM Sach WHERE maSach=?", new String[]{String.valueOf(maSach)});
        if (cursor.moveToFirst()) {
            giaThue = cursor.getInt(cursor.getColumnIndexOrThrow("giaThue"));
        }
        cursor.close();
        return giaThue;
    }

    public String getThanhvienByName(int maTV) {
        String thanhVien = "";
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT hoTen FROM ThanhVien WHERE maTV=?", new String[]{String.valueOf(maTV)});
        if (cursor.moveToNext()){
            thanhVien = cursor.getString(0);
        }
        cursor.close();

        return thanhVien;
    }

    public String getTenSachByName(int maSach) {
        String tenSach = "";
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tenSach FROM Sach  WHERE maSach=?", new String[]{String.valueOf(maSach)});
        if (cursor.moveToNext()){
            tenSach = cursor.getString(0);
        }
        cursor.close();

        return tenSach;
    }

    // top 10 doanh thu cao nhất
    public ArrayList<PhieuMuon> getTop10PhieuMuonByDoanhThu() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_TIENTHUE + " DESC LIMIT 10";
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

    // top 10 sách mượn nhiều nhất
    public ArrayList<Top10> getTop10SachMuonNhieuNhat() {
        ArrayList<Top10> list = new ArrayList<>();
        String selectQuery = "SELECT tenSach, COUNT(*) as soLanMuon " +
                "FROM PhieuMuon pm " +
                "INNER JOIN Sach s ON pm.maSach = s.maSach " +
                "GROUP BY pm.maSach " +
                "ORDER BY soLanMuon DESC " +
                "LIMIT 10";
//
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String tenSach = cursor.getString(cursor.getColumnIndexOrThrow("tenSach"));
                int soLanMuon = cursor.getInt(cursor.getColumnIndexOrThrow("soLanMuon"));
                list.add(new Top10(tenSach, soLanMuon));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public String getDoanhThu(String tuNgay, String denNgay) {
        String doanhThu = "0"; // Giá trị mặc định nếu không có dữ liệu
        db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT SUM(" + COLUMN_TIENTHUE + ") as doanhThu " +
                "FROM " + TABLE_NAME + " " +
                "WHERE " + COLUMN_NGAY + " BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{tuNgay, denNgay});
        if (cursor.moveToFirst()) {
            doanhThu = cursor.getString(cursor.getColumnIndexOrThrow("doanhThu"));
            if (doanhThu == null) {
                doanhThu = "0";
            }
        }
        cursor.close();
        return doanhThu;
    }


}
