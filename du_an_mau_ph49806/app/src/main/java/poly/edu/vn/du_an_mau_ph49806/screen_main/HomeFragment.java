package poly.edu.vn.du_an_mau_ph49806.screen_main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import poly.edu.vn.du_an_mau_ph49806.DAO.BookDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.adapter.AdapterBook;
import poly.edu.vn.du_an_mau_ph49806.model.Books;


public class HomeFragment extends Fragment implements AdapterBook.OnDeleteClickListener{
    AdapterBook adapterBook;
    ArrayList<Books> listBook;
    RecyclerView recyclerView;
    ImageView imgAdd;
    BookDAO bookDAO;
    String[] itemUser = new String[]{"phạm đức lợi","phạm văn thành", "lê văn tuấn"};
    String[] itemBook = new String[]{"java1", "java3", "android 1"};
    int gia;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        imgAdd = view.findViewById(R.id.imgAdd);

        bookDAO = new BookDAO(getContext());
        listBook = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        listBook = bookDAO.getAllBooks();

        // thêm gạch chân RecycleView
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        adapterBook = new AdapterBook(getContext(), listBook, this);
        recyclerView.setAdapter(adapterBook);

        // thêm dữ liệu
        imgAdd.setOnClickListener(v -> {
            addBook();

        });
    }

    private void addBook() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.iteam_add_home, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        Button btnLuu, btnHuy;
        EditText edtMaPhieu;
        CheckBox cbkTinhTrang;
        cbkTinhTrang = view.findViewById(R.id.cbkTinhTrang);
        edtMaPhieu = view.findViewById(R.id.edtMaphieu);
        btnLuu = view.findViewById(R.id.btnLuu);
        btnHuy = view.findViewById(R.id.btnHuy);
        Spinner spThanhVien, spSach;
        spThanhVien = view.findViewById(R.id.spThanhVien);
        spSach = view.findViewById(R.id.spTenSach);

        spThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"Bạn chọn: " + itemUser[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter adapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, itemUser);
        spThanhVien.setAdapter(adapter1);


        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"Bạn chọn: " + itemBook[position], Toast.LENGTH_SHORT).show();
                if(position == 0){
                    gia = 10000;
                } else if (position == 1) {
                    gia = 20000;
                }else {
                    gia = 30000;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, itemBook);
        spSach.setAdapter(adapter2);


        AlertDialog dialog = builder.create();

        btnLuu.setOnClickListener(v -> {
            String thanhVien = spThanhVien.getSelectedItem().toString();
            String tenSach = spSach.getSelectedItem().toString();
            int tienThue = gia;
            String tinhTrang;
            if(cbkTinhTrang.isChecked()){
                tinhTrang = "đã trả sách";
            }else {
                tinhTrang = "chưa trả sách";
            }
            //cách 1
//            Date date = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày tháng năm
//            String currentDate = sdf.format(date);

            // cách 2
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = dateFormat.format(calendar.getTime());


            Books books = new Books( thanhVien, tenSach, tienThue,tinhTrang,date);
            boolean checkInsert = bookDAO.insertBook(books);
            if(!checkInsert){
                Toast.makeText(getContext(), "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }else {
                listBook = bookDAO.getAllBooks();
                adapterBook = new AdapterBook(getContext(), listBook, this);
                recyclerView.setAdapter(adapterBook);

                Toast.makeText(getContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnHuy.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }


    @Override
    public void onDeleteClick(int position) {
        Books bookDelete = listBook.get(position);
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa?")
                .setPositiveButton("Có", (dialog, which) -> {
                    listBook.remove(position);
                    boolean checkDelete = bookDAO.deleteBook(bookDelete.getMaPhieu());
                    if(!checkDelete){
                        Toast.makeText(getContext(), "xoá thất bại", Toast.LENGTH_SHORT).show();
                    }else {
                        listBook = bookDAO.getAllBooks();
                        adapterBook = new AdapterBook(getContext(), listBook, this);
                        recyclerView.setAdapter(adapterBook);

                        Toast.makeText(getContext(), "xoá thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Không", null)
                .create().show();
    }
}