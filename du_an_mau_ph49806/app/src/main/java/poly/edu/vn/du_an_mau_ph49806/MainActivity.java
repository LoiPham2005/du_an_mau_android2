package poly.edu.vn.du_an_mau_ph49806;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import poly.edu.vn.du_an_mau_ph49806.DAO.ThuThuDAO;
import poly.edu.vn.du_an_mau_ph49806.Login.LoginActivity;
import poly.edu.vn.du_an_mau_ph49806.model.ThuThu;
import poly.edu.vn.du_an_mau_ph49806.screen_main.DoanhThuFragment;
import poly.edu.vn.du_an_mau_ph49806.screen_main.DoiMatKhauFragment;
import poly.edu.vn.du_an_mau_ph49806.screen_main.PhieuMuonFragment;
import poly.edu.vn.du_an_mau_ph49806.screen_main.QLLoaiSach_Fragment;
import poly.edu.vn.du_an_mau_ph49806.screen_main.QLSach_Fragment;
import poly.edu.vn.du_an_mau_ph49806.screen_main.QLThanhVienFragment;
import poly.edu.vn.du_an_mau_ph49806.screen_main.ThemNguoiDungFragment;
import poly.edu.vn.du_an_mau_ph49806.screen_main.Top10Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    TextView tvUser;
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        // set toolbar thay the actionbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.fpt);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nv = findViewById(R.id.navigationView);
        nv.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, PhieuMuonFragment.newInstance())
                .commit();

        View mHeaderView = nv.getHeaderView(0);
        tvUser = mHeaderView.findViewById(R.id.tvWelcome);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        thuThuDAO = new ThuThuDAO(this);
        ThuThu thuThu = thuThuDAO.getID(user);
        String userName = thuThu.getHoTen();

        tvUser.setText("Welcome " + userName + "!");

        // admin có quyền add user
        if (user.equalsIgnoreCase("admin")) {
            nv.getMenu().findItem(R.id.nav_them_nguoi_dung).setVisible(true);
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            toolbar.setTitle("Quản Lí Phiếu Mượn");
            fragment = PhieuMuonFragment.newInstance();

        } else if (id == R.id.nav_ql_loai_sach) {

            toolbar.setTitle("Quản Lí Loại Sách");
            fragment = QLLoaiSach_Fragment.newInstance();

        } else if (id == R.id.nav_ql_sach) {
            toolbar.setTitle("Quản Lí Sách");
            fragment = QLSach_Fragment.newInstance();

        } else if (id == R.id.nav_ql_thanh_vien) {
            toolbar.setTitle("Quản Lí Thành Viên");
            fragment = QLThanhVienFragment.newInstance();
        }
        else if (id == R.id.nav_top10) {
            toolbar.setTitle("Top 10 sách mượn nhều nhất");
            fragment = Top10Fragment.newInstance();
        }
        else if (id == R.id.nav_doanh_thu) {
            toolbar.setTitle("Doanh thu");
            fragment = DoanhThuFragment.newInstance();
        }
        else if (id == R.id.nav_them_nguoi_dung) {
            toolbar.setTitle("thêm người dùng");
            fragment = ThemNguoiDungFragment.newInstance();
        }else if (id == R.id.nav_doi_mat_khau) {
            toolbar.setTitle("Đổi mật khẩu");
            fragment = DoiMatKhauFragment.newInstance();
        }else if (id == R.id.nav_dang_xuat) {
            new AlertDialog.Builder(this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Có", (dialog, which) -> {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Đóng MainActivity
                    })
                    .setNegativeButton("Không", null)
                    .show();
            return true;
        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment).commit();

        drawerLayout.close();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Thoát ứng dụng")
                    .setMessage("bạn có muốn thoát ứng dụng không?")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("Có", (dialog, which) -> {
                        super.onBackPressed();
                    })
                    .setNegativeButton("không", (dialog, which) -> {
                        dialog.dismiss();
                    }).create().show();

        }
    }

}