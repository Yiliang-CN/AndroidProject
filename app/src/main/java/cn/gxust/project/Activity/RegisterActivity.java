package cn.gxust.project.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import cn.gxust.project.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView registerBtnBack;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        registerBtnBack = findViewById(R.id.registerBtnBack);
        registerBtnBack.setOnClickListener(v -> finish());

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(v -> {

            finish();
        });
    }
}