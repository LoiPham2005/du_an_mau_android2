package poly.edu.vn.du_an_mau_ph49806.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "DBbook", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng ThuThu
        String createTableThuThu =
                "create table ThuThu (" +
                        "maTT TEXT PRIMARY KEY, " +
                        "hoTen TEXT NOT NULL, " +
                        "matKhau TEXT NOT NULL)";
        db.execSQL(createTableThuThu);

        String insertThuThu = "INSERT INTO ThuThu (maTT, hoTen, matKhau) values " +
                "('admin', 'admin', 'admin'); ";
        db.execSQL(insertThuThu);

// Tạo bảng ThanhVien
        String createTableThanhVien =
                "create table ThanhVien (" +
                        "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "hoTen TEXT NOT NULL, " +
                        "namSinh TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);

        String insertThanhVien = "INSERT INTO ThanhVien (hoTen, namSinh) values " +
                "('lê văn hải', '2000')," +
                "('lê văn hải2', '2001')";
        db.execSQL(insertThanhVien);

// Tạo bảng LoaiSach
        String createTableLoaiSach =
                "create table LoaiSach (" +
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);

        String insertLoaiSach = "INSERT INTO LoaiSach (tenLoai) values " +
                "('cntt')," +
                "('marketing')";
        db.execSQL(insertLoaiSach);

// Tạo bảng Sach
        String createTableSach =
                "create table Sach (" +
                        "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenSach TEXT NOT NULL, " +
                        "giaThue INTEGER NOT NULL, " +
                        "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(createTableSach);

// Tạo bảng PhieuMuon
        String createTablePhieuMuon =
                "create table PhieuMuon (" +
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "maTT TEXT REFERENCES ThuThu(maTT), " +
                        "maTV INTEGER REFERENCES ThanhVien(maTV), " +
                        "maSach INTEGER REFERENCES Sach(maSach), " +
                        "tienThue INTEGER NOT NULL, " +
                        "ngay DATE NOT NULL, " +
                        "traSach INTEGER NOT NULL)";
        db.execSQL(createTablePhieuMuon);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng ThuThu
        db.execSQL("DROP TABLE IF EXISTS ThuThu");
        // Xóa bảng ThanhVien
        db.execSQL("DROP TABLE IF EXISTS ThanhVien");
        // Xóa bảng LoaiSach
        db.execSQL("DROP TABLE IF EXISTS LoaiSach");
        // Xóa bảng Sach
        db.execSQL("DROP TABLE IF EXISTS Sach");
        // Xóa bảng PhieuMuon
        db.execSQL("DROP TABLE IF EXISTS PhieuMuon");


        onCreate(db);
    }
}
