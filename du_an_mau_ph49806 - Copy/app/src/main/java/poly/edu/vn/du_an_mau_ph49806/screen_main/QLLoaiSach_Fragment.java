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

import poly.edu.vn.du_an_mau_ph49806.DAO.LoaiSachDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.adapter.LoaiSachAdapter;
import poly.edu.vn.du_an_mau_ph49806.model.LoaiSach;


public class QLLoaiSach_Fragment extends Fragment {
    ListView listView;
    ArrayList<LoaiSach> list = new ArrayList<>();
    LoaiSachDAO dao;
    LoaiSachAdapter adapter;
    ImageView imgAdd;
    public QLLoaiSach_Fragment() {
        // Required empty public constructor
    }

    public static QLLoaiSach_Fragment newInstance() {
        QLLoaiSach_Fragment fragment = new QLLoaiSach_Fragment();
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
        return inflater.inflate(R.layout.fragment_q_l_loai_sach_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.lvLoaiSach);
        imgAdd = view.findViewById(R.id.imgAddLoaiSach);
        dao = new LoaiSachDAO(getContext());

        list = (ArrayList<LoaiSach>) dao.getAllLoaiSach();

        adapter = new LoaiSachAdapter(getContext(), list);
        listView.setAdapter(adapter);

        imgAdd.setOnClickListener(v -> {
            addLoaiSach();
        });

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            LoaiSach loaiSach = list.get(position);
            updateLoaiSach(loaiSach, position);
            return true;
        });
    }

    private void updateLoaiSach(LoaiSach loaiSach, int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.iteam_loai_sach_update, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtTenTV = view.findViewById(R.id.edTenLoaiSachUpdate);
        Button btnUpdate = view.findViewById(R.id.btnLuuLoaiSachUpdate);
        Button btnCancel = view.findViewById(R.id.btnHuyLoaiSachUpdate);

        edtTenTV.setText(loaiSach.getTenLoai());

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });


        btnUpdate.setOnClickListener(v -> {
            String tenLS = edtTenTV.getText().toString();

            boolean check = false;

            if (tenLS.isEmpty()) {
                edtTenTV.setError("Vui lòng nhập tên thành viên");
                check = true;
            }

            if (check) {
                return;
            }
            loaiSach.setTenLoai(tenLS);
            long checkUpdate = dao.updateLoaiSach(loaiSach);
            if (checkUpdate <= 0) {
                Toast.makeText(getContext(), "cập nhật thất bại", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "cập nhật thành công", Toast.LENGTH_SHORT).show();

                list.set(position, loaiSach);

                adapter = new LoaiSachAdapter(getContext(), list);
                listView.setAdapter(adapter);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void addLoaiSach() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_loai_sach_add, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtTenTV = view.findViewById(R.id.edTenSach);
        Button btnAdd = view.findViewById(R.id.btnThemSach);
        Button btnCancel = view.findViewById(R.id.btnHuySach);

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnAdd.setOnClickListener(v -> {
            String tenLS = edtTenTV.getText().toString();
            boolean check = false;

            if (tenLS.isEmpty()) {
                edtTenTV.setError("Vui lòng nhập tên thành viên");
                check = true;
            }

            if (check) {
                return;
            }

            LoaiSach th = new LoaiSach(tenLS);
            long checkAdd = dao.insertLoaiSach(th);
            if (checkAdd < 0) {
                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            } else {
                list = (ArrayList<LoaiSach>) dao.getAllLoaiSach();

                adapter = new LoaiSachAdapter(getContext(), list);
                listView.setAdapter(adapter);

                dialog.dismiss();
            }
        });
        dialog.show();
    }
}