package poly.edu.vn.du_an_mau_ph49806.screen_main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import poly.edu.vn.du_an_mau_ph49806.R;


public class Top10Fragment extends Fragment {



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
}