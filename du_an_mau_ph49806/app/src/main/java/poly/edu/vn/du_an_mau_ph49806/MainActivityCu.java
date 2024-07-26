package poly.edu.vn.du_an_mau_ph49806;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import poly.edu.vn.du_an_mau_ph49806.screen_main.HomeFragment;
import poly.edu.vn.du_an_mau_ph49806.screen_main.QLLoaiSach_Fragment;
import poly.edu.vn.du_an_mau_ph49806.screen_main.QLSach_Fragment;
import poly.edu.vn.du_an_mau_ph49806.screen_main.QLThanhVienFragment;

public class MainActivityCu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_QL_LOAISACH = 1;
    public static final int FRAGMENT_QL_SACH = 2;
    public static final int FRAGMENT_QL_THANH_VIEN = 3;
    public static final int FRAGMENT_TOP10 = 4;
    public static final int FRAGMENT_DOANH_THU = 5;

    int currentFragment = FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
        .replace(R.id.frameLayout, HomeFragment.newInstance())
        .commit();



    }

    private void replaceFragment(Fragment fragment) {
        // cách 1
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
        // cách 2
//        getSupportFragmentManager().beginTransaction
//                .replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home) {
            if (currentFragment != FRAGMENT_HOME) {
                replaceFragment(new HomeFragment());
                setTitle("Quản Lí Phiếu Mượn");
                currentFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_ql_loai_sach) {
            if (currentFragment != FRAGMENT_QL_LOAISACH) {
                replaceFragment(new QLLoaiSach_Fragment());
                setTitle("Quản Lí Loại Sách");
                currentFragment = FRAGMENT_QL_LOAISACH;
            }
        } else if (id == R.id.nav_ql_sach) {
            if (currentFragment != FRAGMENT_QL_SACH) {
                replaceFragment(new QLSach_Fragment());
                setTitle("Quản Lí Sách");
                currentFragment = FRAGMENT_QL_SACH;
            }
        } else if (id == R.id.nav_ql_thanh_vien) {
            if (currentFragment != FRAGMENT_QL_THANH_VIEN) {
                replaceFragment(new QLThanhVienFragment());
                setTitle("Quản Lí Thành Viên");
                currentFragment = FRAGMENT_QL_THANH_VIEN;
            }

        }
//        else if (id == R.id.nav_ql_thanh_vien) {
//            if (currentFragment != FRAGMENT_TOP10) {
//                replaceFragment(new QLLoaiSach_Fragment());
//                setTitle("Quản Lí Thành Viên");
//                currentFragment = FRAGMENT_QL_LOAISACH;
//            }
//        }
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