package cn.gxust.project.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import cn.gxust.project.R;

public class LoginActivity extends AppCompatActivity {

    private ImageView loginBtnBack;
    private EditText loginUserID, loginPassword;
    private Button loginBtn;

    private Long userID;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // 初始化UI控件
        initUI();

        // 登录按钮点击事件
        loginBtn.setOnClickListener(v -> setLoginBtnOnClickListener());

        // 返回按钮点击事件
        loginBtnBack.setOnClickListener(v -> finish());
    }

    // 登录按钮点击事件
    private void setLoginBtnOnClickListener() {
        // 获取用户名和密码
        if (!getUserInfo()) {
            return;
        }

        // 此处用于验证登录


        // 记录登录状态
        recordUserLoginStatus();

        // 记录用户信息
        recordUserInfo();

        // 切换到应用主页
        switchToMainActivity();
    }

    // 获取用户ID和密码 后续可用于登录验证
    private boolean getUserInfo() {
        String userIDStr = loginUserID.getText().toString();
        password = loginPassword.getText().toString();

        if (userIDStr.isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 检查用户ID格式
        try {
            userID = Long.parseLong(loginUserID.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "用户ID格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // 记录用户登录状态
    private void recordUserLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("userLoginStatus", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isUserLoggedIn", true).apply();
    }

    // 记录用户信息
    private void recordUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("userID", userID).apply();
        editor.putString("userName", "用户测试名").apply();
        editor.putString("userPassword", password).apply();
        editor.putString("userSex", "保密").apply();
        editor.putString("userBirthday", "2001-01-01").apply();
        editor.putLong("userPhone", 12345678901L).apply();
    }

    // 切换到应用首页
    private void switchToMainActivity() {
        // 删除旧的MainActivity 创建新的MainActivity 避免出现多个MainActivity 确保栈中只有一个MainActivity
        // 查询活动栈的命令: adb shell "dumpsys activity activities | grep '* ActivityRecord{'"
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    // 初始化UI控件
    private void initUI() {
        loginBtnBack = findViewById(R.id.loginBtnBack);
        loginUserID = findViewById(R.id.loginUserID);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
    }
}