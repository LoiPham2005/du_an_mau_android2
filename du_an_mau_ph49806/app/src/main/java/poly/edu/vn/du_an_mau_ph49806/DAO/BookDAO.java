package poly.edu.vn.du_an_mau_ph49806.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import poly.edu.vn.du_an_mau_ph49806.database.DBHelper;
import poly.edu.vn.du_an_mau_ph49806.model.Books;

public class BookDAO {
    DBHelper dbHelper;
    SQLiteDatabase database;

    public BookDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<Books> getAllBooks() {
        ArrayList<Books> books = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM BOOK", null);
        if (cursor.moveToFirst()) {
            do {
                Books book = new Books(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );
                books.add(book);
                Log.d("BookDAO", "Book: " + book.getTenSach()); // Thêm log để kiểm tra
            } while (cursor.moveToNext());
        }
        cursor.close();
        return books;
    }

    public boolean insertBook(Books book) {
        ContentValues values = new ContentValues();
        values.put("thanhvien", book.getThanhVien());
        values.put("tensach", book.getTenSach());
        values.put("tienthue", book.getTienThue());
        values.put("tinhtrang", book.getTinhTrang());
        values.put("ngaythue", book.getNgayThue());

        long check = database.insert("BOOK", null, values);
        return check != -1;
    }

    public boolean updateBook(Books book) {
        ContentValues values = new ContentValues();
        values.put("thanhvien", book.getThanhVien());
        values.put("tensach", book.getTenSach());
        values.put("tienthue", book.getTienThue());
        values.put("tinhtrang", book.getTinhTrang());
        values.put("ngaythue", book.getNgayThue());

        long check = database.update("BOOK", values, "maPhieu = ?", new String[]{String.valueOf(book.getMaPhieu())});
        return check != -1;
    }

    public boolean deleteBook(int maPhieu) {
        long check = database.delete("BOOK", "maPhieu = ?", new String[]{String.valueOf(maPhieu)});
        return check != -1;
    }

}
