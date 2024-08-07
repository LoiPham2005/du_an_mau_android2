package poly.edu.vn.du_an_mau_ph49806.screen_main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import poly.edu.vn.du_an_mau_ph49806.DAO.SachDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.adapter.SachAdapter;
import poly.edu.vn.du_an_mau_ph49806.model.Sach;


public class QLSach_Fragment extends Fragment {
    ListView listView;
    ArrayList<Sach> list = new ArrayList<>();
    SachDAO dao;
    SachAdapter adapter;
    ImageView imgAdd;
    static Sach sach;

    public QLSach_Fragment() {
        // Required empty public constructor
    }


    public static QLSach_Fragment newInstance() {
        QLSach_Fragment fragment = new QLSach_Fragment();
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
        return inflater.inflate(R.layout.fragment_q_l_sach_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.lvSach);
        imgAdd = view.findViewById(R.id.imgAddSach);
        dao = new SachDAO(getContext());

        list = (ArrayList<Sach>) dao.getAllSach();

        adapter = new SachAdapter(getContext(), list);
        listView.setAdapter(adapter);

        imgAdd.setOnClickListener(v -> {
            addSach();
        });

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            Sach sach1 = list.get(position);
            updateSach(sach1, position);
            return false;
        });
    }

    private void updateSach(Sach sach1, int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_sach_update, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtTenSach = view.findViewById(R.id.edTenSachUpdate);
        EditText edtGiaSach = view.findViewById(R.id.edGiaThueUpdate);
        Spinner spnLoaiSach = view.findViewById(R.id.spinnerLoaiSachUpdate);
        Button btnAdd = view.findViewById(R.id.btnThemSachUpdate);
        Button btnCancel = view.findViewById(R.id.btnHuySachUpdate);

        edtTenSach.setText(sach1.getTenSach());
        edtGiaSach.setText(String.valueOf(sach1.getGiaThue()));
        spnLoaiSach.setSelection(sach1.getMaLoai());

        // Lấy danh sách loại sách từ database và thiết lập Spinner
        List<String> listLoaiSach = dao.getAllLoaiSach();
        ArrayAdapter<String> adapterLoaiSach = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listLoaiSach);
        adapterLoaiSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiSach.setAdapter(adapterLoaiSach);

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnAdd.setOnClickListener(v -> {

            String tenSach = edtTenSach.getText().toString();
            String giaThue = edtGiaSach.getText().toString();
            String loaiSach = spnLoaiSach.getSelectedItem().toString();

            boolean check = false;

            if (tenSach.isEmpty()) {
                edtTenSach.setError("Tên sách không được để trống");
                check = true;
            }
            if (giaThue.isEmpty()) {
                edtGiaSach.setError("Giá thuê không được để trống");
                check = true;
            }

            if (check) {
                return;
            }

            sach1.setTenSach(tenSach);
            sach1.setGiaThue(Integer.parseInt(giaThue));
            // hiển thị vị trí của mã trong loại sách
//            sach1.setMaLoai(listLoaiSach.indexOf(loaiSach));
            sach1.setMaLoai(dao.getMaLoaiByName(loaiSach));
            // phần loại sách foreign key lấy từ mã loại sách cần lấy id của nó
//            Sach sach = new Sach(tenSach, Integer.parseInt(giaThue), listLoaiSach.indexOf(loaiSach));
            long checkAdd = dao.insertSach(sach1);
            if (checkAdd < 0) {
                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            } else {
                list.set(position, sach1);
                adapter = new SachAdapter(getContext(), list);
                listView.setAdapter(adapter);
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void addSach() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_sach_add, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtTenSach = view.findViewById(R.id.edTenSach);
        EditText edtGiaSach = view.findViewById(R.id.edGiaThue);
        Spinner spnLoaiSach = view.findViewById(R.id.spinnerLoaiSach);
        Button btnAdd = view.findViewById(R.id.btnThemSach1);
        Button btnCancel = view.findViewById(R.id.btnHuySach1);

        // Lấy danh sách loại sách từ database và thiết lập Spinner
        List<String> listLoaiSach = dao.getAllLoaiSach();
        ArrayAdapter<String> adapterLoaiSach = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listLoaiSach);
        adapterLoaiSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiSach.setAdapter(adapterLoaiSach);

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnAdd.setOnClickListener(v -> {

            String tenSach = edtTenSach.getText().toString();
            String giaThue = edtGiaSach.getText().toString();
            String loaiSach = spnLoaiSach.getSelectedItem().toString();

            boolean check = false;

            if (tenSach.isEmpty()) {
                edtTenSach.setError("Tên sách không được để trống");
                check = true;
            }
            if (giaThue.isEmpty()) {
                edtGiaSach.setError("Giá thuê không được để trống");
                check = true;
            }

            if (check) {
                return;
            }

            int maLoai = dao.getMaLoaiByName(loaiSach);
            // phần loại sách foreign key lấy từ mã loại sách cần lấy id của nó
            sach = new Sach(tenSach, Integer.parseInt(giaThue), maLoai);
            long checkAdd = dao.insertSach(sach);
            if (checkAdd < 0) {
                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            } else {
                list = (ArrayList<Sach>) dao.getAllSach();
                adapter = new SachAdapter(getContext(), list);
                listView.setAdapter(adapter);
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}