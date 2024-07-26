package poly.edu.vn.du_an_mau_ph49806.screen_main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import poly.edu.vn.du_an_mau_ph49806.DAO.ThanhVienDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.adapter.ThanhVienAdapter;
import poly.edu.vn.du_an_mau_ph49806.model.ThanhVien;


public class QLThanhVienFragment extends Fragment {
    ListView listView;
    ArrayList<ThanhVien> list = new ArrayList<>();
    ThanhVienDAO dao;
    ThanhVienAdapter adapter;
    ImageView imgAdd;

    public QLThanhVienFragment() {
        // Required empty public constructor
    }

    public static QLThanhVienFragment newInstance() {
        QLThanhVienFragment fragment = new QLThanhVienFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_l_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.lvThanhVien);
        imgAdd = view.findViewById(R.id.imgAddThanhVien);
        dao = new ThanhVienDAO(getContext());

        list = dao.getAllThanhVien();

        adapter = new ThanhVienAdapter(getContext(), list);
        listView.setAdapter(adapter);

        imgAdd.setOnClickListener(v -> {
            addThanhVien();
        });

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            ThanhVien thanhVien = list.get(position);
            updateThanhVien(thanhVien, position);
            return true;
        });

    }

    private void updateThanhVien(ThanhVien thanhVien, int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.iteam_thanh_vien_update, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtTenTV = view.findViewById(R.id.edTenThanhVien);
        EditText edtNamSinh = view.findViewById(R.id.edNamSinhThanhVien);
        Button btnUpdate = view.findViewById(R.id.btnLuuThanhVien);
        Button btnCancel = view.findViewById(R.id.btnHuyThanhVien);

        edtTenTV.setText(thanhVien.getHoTen());
        edtNamSinh.setText(thanhVien.getNamSinh());

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });


        btnUpdate.setOnClickListener(v -> {
            String tenTV = edtTenTV.getText().toString();
            String namSinh = edtNamSinh.getText().toString();

            boolean check = false;

            if (tenTV.isEmpty() || namSinh.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                check = true;
            }
            if (tenTV.isEmpty()) {
                edtTenTV.setError("Vui lòng nhập tên thành viên");
                check = true;
            }
            if (namSinh.isEmpty()) {
                edtNamSinh.setError("Vui lòng nhập năm sinh");
                check = true;
            }
            if (check) {
                return;
            }

            thanhVien.setHoTen(tenTV);
            thanhVien.setNamSinh(namSinh);
            long checkUpdate = dao.updateThanhVien(thanhVien);
            if (checkUpdate <= 0) {
                Toast.makeText(getContext(), "cập nhật thất bại", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "cập nhật thành công", Toast.LENGTH_SHORT).show();

                list.set(position, thanhVien);
                adapter = new ThanhVienAdapter(getContext(), list);
                listView.setAdapter(adapter);
                dialog.dismiss();
            }

        });
        dialog.show();
    }

    private void addThanhVien() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_thanh_vien_add, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtTenTV = view.findViewById(R.id.edNameThanhVien);
        EditText edtNamSinh = view.findViewById(R.id.edNamSinhThanhVien2);
        Button btnAdd = view.findViewById(R.id.btnThemThanhVien);
        Button btnCancel = view.findViewById(R.id.btnHuyThanhVien);

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnAdd.setOnClickListener(v -> {
            String tenTV = edtTenTV.getText().toString();
            String namSinh = edtNamSinh.getText().toString();
            boolean check = false;

            if (tenTV.isEmpty() || namSinh.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                check = true;
            }
            if (tenTV.isEmpty()) {
                edtTenTV.setError("Vui lòng nhập tên thành viên");
                check = true;
            }
            if (namSinh.isEmpty()) {
                edtNamSinh.setError("Vui lòng nhập năm sinh");
                check = true;
            }
            if (check) {
                return;
            }

            ThanhVien th = new ThanhVien(tenTV, namSinh);
            long checkAdd = dao.insertThanhVien(th);
            if (checkAdd <= 0) {
                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            } else {
                list = dao.getAllThanhVien();
                adapter.notifyDataSetChanged();
                adapter = new ThanhVienAdapter(getContext(), list);
                listView.setAdapter(adapter);

                dialog.dismiss();
            }
        });
        dialog.show();
    }


//    public void deleteThanhVien(ThanhVien item) {
//        long check = dao.deleteThanhVien(item.getMaTV());
//        if (check <= 0) {
//            Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
//        } else {
//            list.remove(item);
//            adapter.notifyDataSetChanged();
//            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
//        }
//
//    }
}