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

import poly.edu.vn.du_an_mau_ph49806.DAO.PhieuMuonDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.model.PhieuMuon;
import poly.edu.vn.du_an_mau_ph49806.screen_main.HomeFragment;

public class PhieuMuonAdapter extends BaseAdapter {
    Context context;
    ArrayList<PhieuMuon> list;
    PhieuMuonDAO dao;
//    HomeFragment fragment;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        dao = new PhieuMuonDAO(context);
//        fragment = HomeFragment.newInstance();
    }

    @Override
    public int getCount() {
        return list.size();
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
        if(view != null){
            view = convertView;
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_home,null);
        }

        PhieuMuon phieuMuon = list.get(position);

        TextView tvMaPhieu = view.findViewById(R.id.tvMaPhieu);
//        TextView tvMaTT = view.findViewById(R.id.tvMaTT);
        TextView tvThanhVien = view.findViewById(R.id.tvThanhVien);
        TextView tvTenSach = view.findViewById(R.id.tvTenSach);
        TextView tvTienThue = view.findViewById(R.id.tvTienThue_add);
        TextView tvTinhTrang = view.findViewById(R.id.tvTinhTrang);
        TextView tvNgayThue = view.findViewById(R.id.tvNgayThue);
        ImageView imgDelete = view.findViewById(R.id.imgDeletePhieuMuon);

//       PhieuMuon phieuMuon = dao.getPhieuMuonByID(phieuMuon2.getMaPM());
//        tvMaPhieu.setText(phieuMuon.getMaPM());
//        tvMaTT.setText(phieuMuon.getMaTT());
//        tvThanhVien.setText(phieuMuon.getMaTV());
//        tvTenSach.setText(phieuMuon.getMaSach());
//        tvTienThue.setText(phieuMuon.getTienThue());
//        tvTinhTrang.setText(phieuMuon.getTraSach());
//        tvNgayThue.setText(phieuMuon.getNgay());

        String thanhVien  = dao.getThanhvienByName(phieuMuon.getMaTV());
        String tinhTrang = phieuMuon.getTraSach() == 1 ? "Đã trả sách" : "Chưa trả sách";
        String tenSach = dao.getTenSachByName(phieuMuon.getMaSach());

        tvMaPhieu.setText("Mã phiếu: "+String.valueOf(phieuMuon.getMaPM()));
//        tvMaTT.setText(phieuMuon.getMaTT());
        tvThanhVien.setText("Thành viên: "+thanhVien);
        tvTenSach.setText("Tên sách: "+tenSach);
        tvTienThue.setText("Tiền thuê: "+String.valueOf(phieuMuon.getTienThue()));
        tvTinhTrang.setText(tinhTrang);
        tvNgayThue.setText("Ngày thuê: "+phieuMuon.getNgay());


        imgDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        int checkDelete = dao.deletePhieuMuon(phieuMuon.getMaPM());
                        if(checkDelete <0){
                            Toast.makeText(context, "xoá thất bại", Toast.LENGTH_SHORT).show();
                        }else {
                            list.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "xoá thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Không", null)
                    .create().show();
        });


//        view.setOnLongClickListener(v -> {
//            fragment.UpdatePhieuMuon( position);
//            return true;
//        });


        return view;
    }
}
