package poly.edu.vn.du_an_mau_ph49806.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import poly.edu.vn.du_an_mau_ph49806.R;
import poly.edu.vn.du_an_mau_ph49806.model.Top10;
import poly.edu.vn.du_an_mau_ph49806.screen_main.Top10Fragment;

public class TopAdapter extends ArrayAdapter<Top10> {
    private Context context;
    Top10Fragment top10Fragment;
    ArrayList<Top10> lists;
    TextView tvSach, tvSoLuong;
    ImageView imgDelete;

    public TopAdapter(@NonNull Context context, Top10Fragment top10Fragment, ArrayList<Top10> lists) {
        super(context,0, lists);
        this.context = context;
        this.top10Fragment = top10Fragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(context).inflate(R.layout.top_item, null);
        }
        Top10 top10 = lists.get(position);
        if (top10 != null){
            tvSach = v.findViewById(R.id.tvSach);
            tvSoLuong = v.findViewById(R.id.tvSL);

            tvSach.setText("Sách: "+top10.getTenSach());
            tvSoLuong.setText("Số lượng: "+top10.getSoLuong());

        }
        return v;
    }
}
