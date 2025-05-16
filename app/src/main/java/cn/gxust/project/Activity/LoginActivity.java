package cn.gxust.project.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import cn.gxust.project.R;

public class LoginActivity extends AppCompatActivity {

    ImageView loginBtnBack;
    EditText loginUserID, loginPassword;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        loginBtnBack = findViewById(R.id.loginBtnBack);
        loginUserID = findViewById(R.id.loginUserID);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);

        // 返回按钮点击事件
        loginBtnBack.setOnClickListener(v -> finish());

        // 登录按钮点击事件
        loginBtn.setOnClickListener(v -> {
            setLoginBtnOnClickListener();
        });
    }

    // 登录按钮点击事件
    private void setLoginBtnOnClickListener() {
        // 获取用户名和密码 后续可用于验证登录
        Long userID = Long.parseLong(loginUserID.getText().toString());
        String password = loginPassword.getText().toString();

        // 记录登录状态
        SharedPreferences sharedPreferences = getSharedPreferences("userLoginStatus", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isUserLoggedIn", true).apply();

        // 记录用户信息
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        sharedPreferences.edit().putLong("userID", userID).apply();
        sharedPreferences.edit().putString("userName", "用户测试名").apply();
        sharedPreferences.edit().putString("userPassword", password).apply();
        sharedPreferences.edit().putString("userSex", "保密").apply();
        sharedPreferences.edit().putString("userBirthday", "2001-01-01").apply();
        sharedPreferences.edit().putLong("userPhone", 12345678901L).apply();

        // 删除旧的MainActivity 创建新的MainActivity 避免出现多个MainActivity 确保栈中只有一个MainActivity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        // 查询任务栈的命令: adb shell "dumpsys activity activities | grep '* ActivityRecord{'"

        finish();
    }
}