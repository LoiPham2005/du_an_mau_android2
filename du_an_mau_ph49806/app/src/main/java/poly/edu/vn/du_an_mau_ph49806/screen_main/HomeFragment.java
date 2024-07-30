package poly.edu.vn.du_an_mau_ph49806.screen_main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import poly.edu.vn.du_an_mau_ph49806.DAO.PhieuMuonDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.adapter.PhieuMuonAdapter;
import poly.edu.vn.du_an_mau_ph49806.model.PhieuMuon;


public class HomeFragment extends Fragment {
    ArrayList<PhieuMuon> listPhieuMuon;
    ListView listView;
    ImageView imgAdd;
    PhieuMuonDAO phieuMuonDAO;
    PhieuMuonAdapter adapter;
    int tienThue;
    TextView tvTienThue;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.listView);
        imgAdd = view.findViewById(R.id.imgAdd);
        phieuMuonDAO = new PhieuMuonDAO(getContext());

        listPhieuMuon = new ArrayList<>();
        listPhieuMuon.addAll(phieuMuonDAO.getAllPhieuMuon());
        adapter = new PhieuMuonAdapter(getContext(), listPhieuMuon);
        listView.setAdapter(adapter);

        imgAdd.setOnClickListener(v -> addBook());

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            PhieuMuon phieuMuon = listPhieuMuon.get(position);
            UpdatePhieuMuon(position);
            return true;
        });

        return view;
    }

    private void addBook() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.iteam_add_home, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        Button btnLuu = view.findViewById(R.id.btnLuu);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        Spinner spThanhVien = view.findViewById(R.id.spThanhVien);
        Spinner spSach = view.findViewById(R.id.spTenSach);
        tvTienThue = view.findViewById(R.id.tvTienThue_add);
        TextView tvNgayThue = view.findViewById(R.id.tvNgayThue_add);
        CheckBox cbkTinhTrang = view.findViewById(R.id.cbkTinhTrang);


        // Đặt adapter cho Spinner
        List<String> listThanhVien = phieuMuonDAO.getAllThanhVien();
        ArrayAdapter<String> adapterThanhVien = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listThanhVien);
        adapterThanhVien.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThanhVien.setAdapter(adapterThanhVien);

        List<String> listSach = phieuMuonDAO.getAllSach();
        ArrayAdapter<String> adapterSach = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listSach);
        adapterSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSach.setAdapter(adapterSach);

        AlertDialog dialog = builder.create();


        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tenSach = spSach.getSelectedItem().toString();
                int tienThue = phieuMuonDAO.getSachPriceByName(tenSach);
                tvTienThue.setText(String.valueOf(tienThue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Lấy ngày hiện tại
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ngay = dateFormat.format(date);
        tvNgayThue.setText(ngay);

        btnLuu.setOnClickListener(v -> {
            String thanhVien = spThanhVien.getSelectedItem().toString();
            int maThanhVien = phieuMuonDAO.getThanhVienByName(thanhVien);

            String tenSach = spSach.getSelectedItem().toString();
            int tienThue = phieuMuonDAO.getSachPriceByName(tenSach);
            int maSach = phieuMuonDAO.getSachByName(tenSach);
            int tinhTrang = cbkTinhTrang.isChecked() ? 1 : 0;

            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.setMaTT(thanhVien);
            phieuMuon.setMaTV(maThanhVien);
            phieuMuon.setMaSach(maSach);
            phieuMuon.setTienThue(tienThue);
            phieuMuon.setNgay(ngay);  // có thể viết tắt dateFormat.format(new Date())
            phieuMuon.setTraSach(tinhTrang);

            long result = phieuMuonDAO.insertPhieuMuon(phieuMuon);

            if (result > 0) {
                Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                listPhieuMuon.clear();
                listPhieuMuon.addAll(phieuMuonDAO.getAllPhieuMuon());
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        btnHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void UpdatePhieuMuon(int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.iteam_update_home, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        EditText edtMaPM = view.findViewById(R.id.edtMaphieu_update);
        Button btnLuu = view.findViewById(R.id.btnLuu_update);
        Button btnHuy = view.findViewById(R.id.btnHuy_update);
        Spinner spThanhVien = view.findViewById(R.id.spThanhVien_update);
        Spinner spSach = view.findViewById(R.id.spTenSach_update);
        TextView tvNgayThue = view.findViewById(R.id.tvNgayThue_update);
        TextView tvTienThue = view.findViewById(R.id.tvTienThue_update);
        CheckBox cbkTinhTrang = view.findViewById(R.id.cbkTinhTrang_update);

        // Đặt adapter cho Spinner
        List<String> listThanhVien = phieuMuonDAO.getAllThanhVien();
        ArrayAdapter<String> adapterThanhVien = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listThanhVien);
        adapterThanhVien.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThanhVien.setAdapter(adapterThanhVien);

        List<String> listSach = phieuMuonDAO.getAllSach();
        ArrayAdapter<String> adapterSach = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listSach);
        adapterSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSach.setAdapter(adapterSach);

        PhieuMuon phieuMuon = listPhieuMuon.get(position);

        edtMaPM.setText(phieuMuon.getMaPM() + "");
        spThanhVien.setSelection(listThanhVien.indexOf(phieuMuonDAO.getThanhvienByName(phieuMuon.getMaTV())));
        spSach.setSelection(listSach.indexOf(phieuMuonDAO.getTenSachByName(phieuMuon.getMaSach())));
        tvNgayThue.setText(phieuMuon.getNgay());
        tvTienThue.setText(String.valueOf(phieuMuon.getTienThue()));
        cbkTinhTrang.setChecked(phieuMuon.getTraSach() == 1);

        AlertDialog dialog = builder.create();

        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tenSach = spSach.getSelectedItem().toString();
                tienThue = phieuMuonDAO.getSachPriceByName(tenSach);
                tvTienThue.setText(String.valueOf(tienThue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btnLuu.setOnClickListener(v -> {
            String thanhVien = spThanhVien.getSelectedItem().toString();
            int maThanhVien = phieuMuonDAO.getThanhVienByName(thanhVien);

            String tenSach = spSach.getSelectedItem().toString();
            int tienThue = phieuMuonDAO.getSachPriceByName(tenSach);
            int maSach = phieuMuonDAO.getSachByName(tenSach);
            int tinhTrang = cbkTinhTrang.isChecked() ? 1 : 0;

//            Date date = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            String ngayThue = dateFormat.format(date);

            PhieuMuon updatedPhieuMuon = new PhieuMuon();
            updatedPhieuMuon.setMaPM(phieuMuon.getMaPM());
            updatedPhieuMuon.setMaTT(thanhVien);
            updatedPhieuMuon.setMaTV(maThanhVien);
            updatedPhieuMuon.setMaSach(maSach);
            updatedPhieuMuon.setTienThue(tienThue);
            updatedPhieuMuon.setNgay(phieuMuon.getNgay());
            updatedPhieuMuon.setTraSach(tinhTrang);

            long result = phieuMuonDAO.updatePhieuMuon(updatedPhieuMuon);

            if (result > 0) {
                Toast.makeText(getContext(), "Cập nhật phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                listPhieuMuon.set(position, updatedPhieuMuon);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Cập nhật phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        btnHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

}
