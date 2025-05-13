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

            finish();
        });
    }
}