package poly.edu.vn.du_an_mau_ph49806.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import poly.edu.vn.du_an_mau_ph49806.DAO.BookDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.model.Books;
import poly.edu.vn.du_an_mau_ph49806.screen_main.HomeFragment;

public class AdapterBook extends RecyclerView.Adapter<AdapterBook.BookViewHoder>{
    Context context;
    ArrayList<Books> listBook;
    OnDeleteClickListener deleteClickListener;
    BookDAO bookDAO;

//    public AdapterBook(Context context, ArrayList<Books> listBook) {
//        this.context = context;
//        this.listBook = listBook;
//    }
HomeFragment fragment;

    public AdapterBook(Context context, ArrayList<Books> listBook, OnDeleteClickListener deleteClickListener) {
        this.context = context;
        this.listBook = listBook;
        this.deleteClickListener = deleteClickListener;
        bookDAO = new BookDAO(context);
    }

    @NonNull
    @Override
    public BookViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, null);
        return new BookViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHoder holder, int position) {
        Books books = listBook.get(position);
        holder.tvMaPhieu.setText(books.getMaPhieu()+"");
        holder.tvThanhVien.setText(books.getThanhVien());
        holder.tvTenSach.setText(books.getTenSach());
        holder.tvTienThue.setText(books.getTienThue()+"");
        holder.tvTinhTrang.setText(books.getTinhTrang());
        holder.tvNgayThue.setText(books.getNgayThue());

        holder.imgDelete.setOnClickListener(v -> {
            deleteClickListener.onDeleteClick(position);
        });
//        holder.imgDelete.setOnClickListener(v -> {
//            new AlertDialog.Builder(context)
//                    .setTitle("Xác nhận xóa")
//                    .setMessage("Bạn có chắc chắn muốn xóa?")
//                    .setPositiveButton("Có", (dialog, which) -> {
//                        listBook.remove(position);
//
//                        notifyDataSetChanged();
//                    })
//                    .setNegativeButton("Không", null)
//                    .create().show();
//        });

        holder.itemView.setOnLongClickListener(v -> {
            View view = LayoutInflater.from(context).inflate(R.layout.iteam_update_home, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(view);

            Button btnLuu, btnHuy;
            EditText edtMaPhieu;
            CheckBox cbkTinhTrang;
            Spinner spThanhVien, spSach;
            cbkTinhTrang = view.findViewById(R.id.cbkTinhTrang_update);
            edtMaPhieu = view.findViewById(R.id.edtMaphieu_update);
            btnLuu = view.findViewById(R.id.btnLuu_update);
            btnHuy = view.findViewById(R.id.btnHuy_update);
            spThanhVien = view.findViewById(R.id.spThanhVien_update);
            spSach = view.findViewById(R.id.spTenSach_update);

            edtMaPhieu.setText(books.getMaPhieu()+"");
            // Điền thông tin hiện có của mục vào form
            edtMaPhieu.setText(String.valueOf(books.getMaPhieu()));
            cbkTinhTrang.setChecked(books.getTinhTrang().equals("đã trả sách"));

            // Điền thông tin Spinner
            String[] itemUser = new String[]{"phạm đức lợi", "phạm văn thành", "lê văn tuấn"};
            String[] itemBook = new String[]{"java1", "java3", "android 1"};

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, itemUser);
            spThanhVien.setAdapter(adapter1);
            spThanhVien.setSelection(adapter1.getPosition(books.getThanhVien()));

            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, itemBook);
            spSach.setAdapter(adapter2);
            spSach.setSelection(adapter2.getPosition(books.getTenSach()));

            AlertDialog dialog = builder.create();

            btnLuu.setOnClickListener(v1 -> {
                String maPhieu = edtMaPhieu.getText().toString();
                String thanhVien = spThanhVien.getSelectedItem().toString();
                String tenSach = spSach.getSelectedItem().toString();
                int tienThue = books.getTienThue();
                String tinhTrang = cbkTinhTrang.isChecked() ? "đã trả sách" : "chưa trả sách";

                books.setMaPhieu(Integer.parseInt(maPhieu));
                books.setThanhVien(thanhVien);
                books.setTenSach(tenSach);
                books.setTienThue(tienThue);
                books.setTinhTrang(tinhTrang);

                // Cập nhật lại dữ liệu trong danh sách và cơ sở dữ liệu
                listBook.set(position, books);
                notifyItemChanged(position);

                // Gọi phương thức update trong BookDAO (cần thêm phương thức này trong BookDAO)
                 boolean checkUpdate = bookDAO.updateBook(books);
                if(!checkUpdate){
                    Toast.makeText(context, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            });

            btnHuy.setOnClickListener(v12 -> dialog.dismiss());

            dialog.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public static class BookViewHoder extends RecyclerView.ViewHolder {
        TextView tvMaPhieu, tvThanhVien, tvTenSach, tvTienThue, tvTinhTrang, tvNgayThue;
        ImageView imgDelete;
        public BookViewHoder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieu = itemView.findViewById(R.id.tvMaPhieu);
            tvThanhVien = itemView.findViewById(R.id.tvThanhVien);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvTienThue = itemView.findViewById(R.id.tvTienThue_add);
            tvTinhTrang = itemView.findViewById(R.id.tvTinhTrang);
            tvNgayThue = itemView.findViewById(R.id.tvNgayThue);
//            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
}
