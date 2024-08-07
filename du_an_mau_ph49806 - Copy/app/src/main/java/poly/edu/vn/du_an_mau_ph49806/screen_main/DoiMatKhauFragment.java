package poly.edu.vn.du_an_mau_ph49806.screen_main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import poly.edu.vn.du_an_mau_ph49806.DAO.ThuThuDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.model.ThuThu;


public class DoiMatKhauFragment extends Fragment {

    TextInputEditText edtOldPass, edtNewPass, edtConfirmPass;
    Button btnChangePass, btnCancel;
    ThuThuDAO thuThuDAO;

    public DoiMatKhauFragment() {
        // Required empty public constructor
    }


    public static DoiMatKhauFragment newInstance() {
        DoiMatKhauFragment fragment = new DoiMatKhauFragment();
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
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtOldPass = view.findViewById(R.id.edtOldPass);
        edtNewPass = view.findViewById(R.id.edtNewPass);
        edtConfirmPass = view.findViewById(R.id.edtConfirmPass);
        btnChangePass = view.findViewById(R.id.btnChangePass);
        btnCancel = view.findViewById(R.id.btnCancel);
        thuThuDAO = new ThuThuDAO(getContext());

        btnCancel.setOnClickListener(v -> {
            edtOldPass.setText("");
            edtNewPass.setText("");
            edtConfirmPass.setText("");
        });

        btnChangePass.setOnClickListener(v -> {
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String user = pref.getString("USERNAME", "");

            if (validate() > 0) {
                ThuThu thuThu = thuThuDAO.getID(user);
                thuThu.setMatKhau(edtNewPass.getText().toString());

                boolean checkUpdate = thuThuDAO.updateThuThu(thuThu);
                if(checkUpdate){
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    edtOldPass.setText("");
                    edtNewPass.setText("");
                    edtConfirmPass.setText("");
                }else{
                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public int validate() {
        int check = 1;
        if (edtOldPass.getText().toString().isEmpty() || edtNewPass.getText().toString().isEmpty() || edtConfirmPass.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String oldPass = pref.getString("PASSWORD", "");
            String newPass = edtNewPass.getText().toString();
            String confirmPass = edtConfirmPass.getText().toString();
            if (!oldPass.equals(edtOldPass.getText().toString())) {
                Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!newPass.equals(confirmPass)) {
                Toast.makeText(getContext(), "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }

        }

        return check;
    }
}