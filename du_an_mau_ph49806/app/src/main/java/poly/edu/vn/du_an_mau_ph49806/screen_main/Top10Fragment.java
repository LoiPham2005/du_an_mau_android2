package poly.edu.vn.du_an_mau_ph49806.screen_main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import poly.edu.vn.du_an_mau_ph49806.DAO.PhieuMuonDAO;
import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.adapter.TopAdapter;
import poly.edu.vn.du_an_mau_ph49806.model.PhieuMuon;
import poly.edu.vn.du_an_mau_ph49806.model.Top10;


public class Top10Fragment extends Fragment {
    ListView listView;
    ArrayList<Top10> list;
    TopAdapter topAdapter;


    public Top10Fragment() {
        // Required empty public constructor
    }

    public static Top10Fragment newInstance() {
        Top10Fragment fragment = new Top10Fragment();

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
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.lvTop10);
        list = new ArrayList<>();
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getTop10SachMuonNhieuNhat();
        topAdapter = new TopAdapter(getContext(), this, list);
        listView.setAdapter(topAdapter);
    }
}