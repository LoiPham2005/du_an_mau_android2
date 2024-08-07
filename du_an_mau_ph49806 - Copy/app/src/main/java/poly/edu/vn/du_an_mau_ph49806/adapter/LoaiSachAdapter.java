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

import poly.edu.vn.du_an_mau_ph49806.DAO.LoaiSachDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.model.LoaiSach;
import poly.edu.vn.du_an_mau_ph49806.model.ThanhVien;

public class LoaiSachAdapter extends BaseAdapter {
    Context context;
    ArrayList<LoaiSach> lists;
    LoaiSachDAO dao;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> lists) {
        this.context = context;
        this.lists = lists;
        dao = new LoaiSachDAO(context);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        }
        view = LayoutInflater.from(context).inflate(R.layout.item_list_loai_sach, null);

        LoaiSach loaiSach = lists.get(position);
        TextView tvMaTV = view.findViewById(R.id.tvMaLoaiSach);
        TextView tvTenTV = view.findViewById(R.id.tvTenLoaiSach);

        ImageView imgDel = view.findViewById(R.id.imgDeleteLoaiSach);

        tvMaTV.setText("Mã Thành Viên: "+loaiSach.getMaLoai() + "");
        tvTenTV.setText("Tên Thành viên: "+loaiSach.getTenLoai());

        imgDel.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        int checkDelete = dao.deleteLoaiSach(loaiSach.getMaLoai());
                        if(checkDelete <=0){
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
        return view;
    }
}
