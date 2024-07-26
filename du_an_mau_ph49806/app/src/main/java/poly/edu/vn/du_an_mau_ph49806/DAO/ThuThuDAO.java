package poly.edu.vn.du_an_mau_ph49806.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import poly.edu.vn.du_an_mau_ph49806.database.DBHelper;
import poly.edu.vn.du_an_mau_ph49806.model.ThuThu;

public class ThuThuDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    public static final String TABLE_NAME = "ThuThu";
    public static final String COLUMN_ID = "maTT";
    public static final String COLUMN_HOTEN = "hoTen";
    public static final String COLUMN_MATKHAU = "matKhau";

    public ThuThuDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Insert
    public long insertThuThu(String maTT, String hoTen, String matKhau) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, maTT);
        values.put(COLUMN_HOTEN, hoTen);
        values.put(COLUMN_MATKHAU, matKhau);
        return db.insert(TABLE_NAME, null, values);
    }

    // Update
    public int updateThuThu(String maTT, String hoTen, String matKhau) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEN, hoTen);
        values.put(COLUMN_MATKHAU, matKhau);
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{maTT});
    }

    // Delete
    public int deleteThuThu(String maTT) {
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{maTT});
    }

    // Get All
    public List<ThuThu> getAllThuThu() {
        List<ThuThu> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                thuThu.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOTEN)));
                thuThu.setMatKhau(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATKHAU)));
                list.add(thuThu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Get by ID
    public ThuThu getID(String maTT) {
        ThuThu thuThu = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{maTT});
        if (cursor.moveToFirst()) {
            thuThu = new ThuThu();
            thuThu.setMaTT(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            thuThu.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOTEN)));
            thuThu.setMatKhau(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATKHAU)));
        }
        cursor.close();
        return thuThu;
    }

    public int checkLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_ID + " = ? AND " + COLUMN_MATKHAU + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}