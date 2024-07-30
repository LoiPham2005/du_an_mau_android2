package poly.edu.vn.du_an_mau_ph49806.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import poly.edu.vn.du_an_mau_ph49806.DAO.ThuThuDAO;
import poly.edu.vn.du_an_mau_ph49806.MainActivity;
import poly.edu.vn.du_an_mau_ph49806.R;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnCancel;
    CheckBox chkRemember;
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        thuThuDAO = new ThuThuDAO(this);
        edtUsername = findViewById(R.id.user);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnDangNhap);
        btnCancel = findViewById(R.id.btnHuy_reset);
        chkRemember = findViewById(R.id.chkLuu);

        // đọc dữ liệu từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edtUsername.setText(preferences.getString("USERNAME", ""));
        edtPassword.setText(preferences.getString("PASSWORD", ""));
        chkRemember.setChecked(preferences.getBoolean("REMEMBER", false));

        btnCancel.setOnClickListener(v -> {
            edtUsername.setText("");
            edtPassword.setText("");
            chkRemember.setChecked(false);
        });

        btnLogin.setOnClickListener(v -> {
            checkLogin();
        });

    }

    private void checkLogin() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        boolean remember = chkRemember.isChecked();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        }else {
            if(thuThuDAO.checkLogin(username, password)>0){
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(username, password, remember);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("user", username);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void rememberUser(String username, String password, boolean status) {
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status) {
            // Nếu status là false, xóa dữ liệu lưu trữ
            editor.clear();
        } else {
            editor.putString("USERNAME", username);
            editor.putString("PASSWORD", password);
            editor.putBoolean("REMEMBER", status);
        }
        // lưu lại toàn bộ dữ liệu
        editor.commit();
    }

}