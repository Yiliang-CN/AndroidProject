package cn.gxust.project.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import cn.gxust.project.R;

public class LoginActivity extends AppCompatActivity {

    ImageView loginBtnBack;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        loginBtnBack = findViewById(R.id.loginBtnBack);
        loginBtnBack.setOnClickListener(v -> finish());

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> {

            // 记录登录状态
            SharedPreferences sharedPreferences = getSharedPreferences("userLoginStatus", MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("isUserLoggedIn", true).apply();

            // 删除旧的MainActivity 创建新的MainActivity 避免出现多个MainActivity 确保栈中只有一个MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            // 查询任务栈的命令: adb shell "dumpsys activity activities | grep '* ActivityRecord{'"

            finish();
        });
    }
}