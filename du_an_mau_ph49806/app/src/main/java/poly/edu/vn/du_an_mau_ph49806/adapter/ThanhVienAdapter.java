package poly.edu.vn.du_an_mau_ph49806.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import poly.edu.vn.du_an_mau_ph49806.DAO.ThanhVienDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.model.ThanhVien;
import poly.edu.vn.du_an_mau_ph49806.screen_main.QLThanhVienFragment;

public class ThanhVienAdapter extends BaseAdapter {
    Context context;
    QLThanhVienFragment fragment;
    ArrayList<ThanhVien> lists;
    ImageView imgDel;
    ThanhVienDAO dao ;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> lists) {
        this.context = context;
        this.lists = lists;
        dao = new ThanhVienDAO(context);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView != null){
            view = convertView;
        }
        view = LayoutInflater.from(context).inflate(R.layout.item_list_thanh_vien, null);

        final ThanhVien item = lists.get(position);

        TextView tvMaTV = view.findViewById(R.id.tvMaSach);
        TextView tvTenTV = view.findViewById(R.id.tvTenSach1);
        TextView tvNamSinh = view.findViewById(R.id.tvGiaSach);
        imgDel = view.findViewById(R.id.imgDeleteThanhVien);

        tvMaTV.setText("Mã Thành viên: "+item.getMaTV()+"");
        tvTenTV.setText("Tên Thành Viên: "+item.getHoTen());
        tvNamSinh.setText("Năm Sinh: "+item.getNamSinh());

//        imgDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragment.deleteThanhVien(item);
//            }
//        });

        imgDel.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        boolean checkDelete = dao.deleteThanhVien(item.getMaTV());
                        if(!checkDelete){
                            Toast.makeText(context, "xoá thất bại", Toast.LENGTH_SHORT).show();
                        }else {
                            lists.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "xoá thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Không", null)
                    .create().show();
        });

//        view.setOnLongClickListener(v -> {
//            View view1 = LayoutInflater.from(context).inflate(R.layout.iteam_thanh_vien_update, null);
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setView(view1);
//            AlertDialog dialog = builder.create();
//
//            EditText edtTenTV = view1.findViewById(R.id.edTenThanhVien);
//            EditText edtNamSinh = view1.findViewById(R.id.edNamSinhThanhVien);
//            Button btnUpdate = view1.findViewById(R.id.btnLuuThanhVien);
//            Button btnCancel = view1.findViewById(R.id.btnHuyThanhVien);
//
//            btnCancel.setOnClickListener(v2 -> {
//                dialog.dismiss();
//            });
//
//            btnUpdate.setOnClickListener(v2 -> {
//                String tenTV = edtTenTV.getText().toString();
//                String namSinh = edtNamSinh.getText().toString();
//
//                boolean check = false;
//
//                if (tenTV.isEmpty() || namSinh.isEmpty()) {
//                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    check = true;
//                }
//                if (tenTV.isEmpty()) {
//                    edtTenTV.setError("Vui lòng nhập tên thành viên");
//                    check = true;
//                }
//                if (namSinh.isEmpty()) {
//                    edtNamSinh.setError("Vui lòng nhập năm sinh");
//                    check = true;
//                }
//                if (check) {
//                    return;
//                }
//
//                ThanhVien thanhVien = new ThanhVien();
//                thanhVien.setHoTen(tenTV);
//                thanhVien.setNamSinh(namSinh);
//
//                long checkUpdate = dao.updateThanhVien(1, tenTV, namSinh);
//                if (checkUpdate <= 0) {
//                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
//                } else {
//                    lists.set(position, thanhVien);
//                    notifyDataSetChanged();
//                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//
//            });
//            dialog.show();
//            return true;
//        });
//
//
        return view;
    }


}
