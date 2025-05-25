package cn.gxust.project.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.gxust.project.R;
import cn.gxust.project.Utils.DialogUtils;
import cn.gxust.project.Utils.OkHttpUtils;
import cn.gxust.project.Utils.ValidationUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUserPhone, registerPassword, registerPasswordAgain;
    private ImageView registerBtnBack;
    private Button registerBtn;

    private String phone, password;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 100) {
                //处理登录响应
                handlerRegisterResponse((String) msg.obj);
            }
        }
    };

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
        postHttpData();
    }

    private void postHttpData() {
        // 创建JSON参数
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("phone", phone);
            jsonParam.put("password", password);
        } catch (JSONException e) {
            Toast.makeText(RegisterActivity.this, "构建请求参数失败", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpUtils.getInstance().doPost(getString(R.string.base_url_users_register), jsonParam.toString(), new OkHttpUtils.OkHttpCallback() {
            @Override
            public void onFailure(IOException e) {
                Toast.makeText(RegisterActivity.this, "注册失败！请稍后重试！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String body) {
                Message message = new Message();
                message.what = 100;
                message.obj = body;
                handler.sendMessage(message);
            }
        });
    }

    private void handlerRegisterResponse(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int code = jsonObject.getInt("code");
            if (code != 200) {
                String errorMsg = jsonObject.getString("message");
                Toast.makeText(RegisterActivity.this, "注册失败! code: " + code + "message: " + errorMsg, Toast.LENGTH_SHORT).show();
                return;
            }

            // 注册成功返回登录界面
            DialogUtils.showConfirmDialog("提示", "注册成功！即将前往登录！", "确定", this, this::finish);

        } catch (JSONException e) {
            Toast.makeText(RegisterActivity.this, "数据解析失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 获取用户信息
    private boolean getUserInfo() {
        phone = registerUserPhone.getText().toString();
        password = registerPassword.getText().toString();
        String passwordAgain = registerPasswordAgain.getText().toString();

        if (!ValidationUtils.isValidPhone(phone)) {
            Toast.makeText(this, "手机号格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!ValidationUtils.isValidPassword(password)) {
            Toast.makeText(this, "密码格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(passwordAgain)) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
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