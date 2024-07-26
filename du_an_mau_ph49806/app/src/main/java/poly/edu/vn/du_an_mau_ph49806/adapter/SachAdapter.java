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

import poly.edu.vn.du_an_mau_ph49806.DAO.SachDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.model.Sach;

public class SachAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sach> listSach;
    SachDAO dao;

    public SachAdapter(Context context, ArrayList<Sach> listSach) {
        this.context = context;
        this.listSach = listSach;
        dao = new SachDAO(context);
    }

    @Override
    public int getCount() {
        return listSach.size();
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
        View view = null;
        if (view != null) {
            view = convertView;
        }
        view = LayoutInflater.from(context).inflate(R.layout.item_list_sach, null);

        TextView tvMaSach = view.findViewById(R.id.tvMaSach);
        TextView tvTenSach = view.findViewById(R.id.tvTenSach1);
        TextView tvGia = view.findViewById(R.id.tvGiaSach);
        TextView tvLoaiSach = view.findViewById(R.id.tvLoaiSach);
        ImageView imgDel = view.findViewById(R.id.imgDeleteSach);

        Sach sach = listSach.get(position);
        tvMaSach.setText("Mã Sách: "+sach.getMaSach() + "");
        tvTenSach.setText("Tên Sách: "+sach.getTenSach());
        tvGia.setText("Giá Sách: "+sach.getGiaThue() + "");
        tvLoaiSach.setText("Loại Sách: " + getLoaiSachName(sach.getMaLoai()));

        imgDel.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        int checkDelete = dao.deleteSach(sach.getMaLoai());
                        if(checkDelete <0){
                            Toast.makeText(context, "xoá thất bại", Toast.LENGTH_SHORT).show();
                        }else {
                            listSach.remove(position);
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

    private String getLoaiSachName(int maLoai) {
        // Lấy tên loại sách từ dao dựa trên maLoai
        return dao.getLoaiSachName(maLoai);
    }
}
