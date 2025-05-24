package cn.gxust.project.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import cn.gxust.project.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUserPhone, registerPassword, registerPasswordAgain;
    private ImageView registerBtnBack;
    private Button registerBtn;

    private Long userPhone;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        initUI();

        // 注册按钮点击事件
        registerBtn.setOnClickListener(v -> setRegisterBtnOnClickListener());

        // 返回按钮点击事件
        registerBtnBack.setOnClickListener(v -> finish());
    }

    // 注册按钮点击事件
    private void setRegisterBtnOnClickListener() {
        // 获取用户手机号和密码
        if (!getUserInfo()) {
            return;
        }

        // 此处用于验证注册


        // 注册成功返回登录界面
        Toast.makeText(this, "注册成功！请登录！", Toast.LENGTH_SHORT).show();
        finish();
    }

    // 获取用户信息
    private boolean getUserInfo() {
        String userPhoneStr = registerUserPhone.getText().toString();
        password = registerPassword.getText().toString();
        String passwordAgain = registerPasswordAgain.getText().toString();

        if (userPhoneStr.isEmpty() && password.isEmpty() && passwordAgain.isEmpty()) {
            Toast.makeText(this, "请填写完注册信息", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(passwordAgain)) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            userPhone = Long.parseLong(userPhoneStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "手机号格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void initUI() {
        registerUserPhone = findViewById(R.id.registerUserPhone);
        registerPassword = findViewById(R.id.registerPassword);
        registerPasswordAgain = findViewById(R.id.registerPasswordAgain);
        registerBtnBack = findViewById(R.id.registerBtnBack);
        registerBtn = findViewById(R.id.registerBtn);
    }
}