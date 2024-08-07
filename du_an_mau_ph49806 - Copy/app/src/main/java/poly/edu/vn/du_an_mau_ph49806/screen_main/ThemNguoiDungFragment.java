package poly.edu.vn.du_an_mau_ph49806.screen_main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import poly.edu.vn.du_an_mau_ph49806.DAO.ThuThuDAO;
import poly.edu.vn.du_an_mau_ph49806.R;


public class ThemNguoiDungFragment extends Fragment {
    TextInputEditText edHoTen, edTenDangNhap, edMatKhau, edLaiMatKhau;
    Button btnHuyThemNguoi, btnThemNguoiDung;
    ThuThuDAO thuThuDAO;

    public ThemNguoiDungFragment() {
        // Required empty public constructor
    }


    public static ThemNguoiDungFragment newInstance() {
        ThemNguoiDungFragment fragment = new ThemNguoiDungFragment();

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
        return inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edHoTen = view.findViewById(R.id.edHoTen);
        edTenDangNhap = view.findViewById(R.id.edTenDangNhap);
        edMatKhau = view.findViewById(R.id.edMatKhau);
        edLaiMatKhau = view.findViewById(R.id.edLaiMatKhau);
        btnThemNguoiDung = view.findViewById(R.id.btnThemNguoiDung);
        btnHuyThemNguoi = view.findViewById(R.id.btnHuyThemNguoi);

        thuThuDAO = new ThuThuDAO(getContext());

        btnHuyThemNguoi.setOnClickListener(v -> {
            huyThemNguoi();
        });

        btnThemNguoiDung.setOnClickListener(v -> {
            themNguoiDung();
        });

    }

    private void themNguoiDung() {
        String tenDangNhap = edTenDangNhap.getText().toString();
        String hoTen = edHoTen.getText().toString();
        String matKhau = edMatKhau.getText().toString();
        String laiMatKhau = edLaiMatKhau.getText().toString();
        boolean check = false;

        if (hoTen.isEmpty() || tenDangNhap.isEmpty() || matKhau.isEmpty() || laiMatKhau.isEmpty()) {
            check = true;
        }

        if (!matKhau.equals(laiMatKhau)) {
            check = true;
        }

        if (check) {
            return;
        }

        // Thêm người dùng vào cơ sở dữ liệu
        long checkAdd = thuThuDAO.insertThuThu(tenDangNhap,hoTen, matKhau);
        if (checkAdd <=0){
            Toast.makeText(getContext(), "thêm thất bại", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
            huyThemNguoi();
        }

    }

    private void huyThemNguoi() {
        edHoTen.setText("");
        edTenDangNhap.setText("");
        edMatKhau.setText("");
        edLaiMatKhau.setText("");
    }
}