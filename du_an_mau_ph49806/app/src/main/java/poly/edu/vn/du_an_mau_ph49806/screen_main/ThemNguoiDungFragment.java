package poly.edu.vn.du_an_mau_ph49806.screen_main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import poly.edu.vn.du_an_mau_ph49806.R;


public class ThemNguoiDungFragment extends Fragment {
    TextInputEditText edHoTen, edTenDangNhap, edMatKhau, edLaiMatKhau;

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

    }
}