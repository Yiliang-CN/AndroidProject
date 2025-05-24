package cn.gxust.project.activity;

import static cn.gxust.project.Utils.ValidationUtils.isValidPassword;
import static cn.gxust.project.Utils.ValidationUtils.isValidPhone;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.gxust.project.bean.UserBean;
import cn.gxust.project.R;
import cn.gxust.project.Utils.OkHttpUtils;

public class LoginActivity extends AppCompatActivity {

    // 常量定义
    private static final String URL = "http://10.0.2.2:8080/users/login";

    // UI控件
    private ImageView loginBtnBack;
    private EditText loginUserPhone, loginPassword;
    private Button loginBtn;

    // 用户数据
    private String userPhone;
    private String password;
    private UserBean user;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 100) {
                // 处理登录响应
                handleLoginResponse((String) msg.obj);
            }
        }
    };

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

        // 验证登录
        postHttpData();
    }

    // 获取用户手机号和密码 用于登录验证
    private boolean getUserInfo() {
        userPhone = loginUserPhone.getText().toString();
        password = loginPassword.getText().toString();

        if (!isValidPhone(userPhone)) {
            Toast.makeText(this, "手机号格式有误", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "密码格式有误", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void postHttpData() {
        // 创建JSON参数
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("phone", userPhone);
            jsonParam.put("password", password);
        } catch (JSONException e) {
            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "构建请求参数失败", Toast.LENGTH_SHORT).show());
            return;
        }

        // 使用OkHttpUtils发送网络请求
        OkHttpUtils.getInstance().doPost(URL, jsonParam.toString(), new OkHttpUtils.OkHttpCallback() {
            @Override
            public void onFailure(IOException e) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show());
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

    // 处理登录响应
    private void handleLoginResponse(String jsonData) {
        // 将json数据转为UserBean对象
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int code = jsonObject.getInt("code");
            if (code != 200) {
                String errorMsg = jsonObject.getString("message");
                Toast.makeText(LoginActivity.this, "登陆失败! code: " + code + "message: " + errorMsg, Toast.LENGTH_SHORT).show();
                return;
            }

            // 提取用户信息
            JSONObject dataObject = jsonObject.getJSONObject("data");
            try {
                user = new Gson().fromJson(dataObject.toString(), UserBean.class);
            } catch (JsonSyntaxException e) {
                Toast.makeText(LoginActivity.this, "数据解析错误", Toast.LENGTH_SHORT).show();
                return;
            }

            // 检查解析是否成功
            if (user != null && user.getId() != 0) {
                // 记录登录状态
                recordUserLoginStatus();
                // 记录用户信息
                recordUserInfo();
                // 切换到应用主页
                switchToMainActivity();
            } else {
                Toast.makeText(LoginActivity.this, "登录失败！用户数据解析错误", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            Toast.makeText(LoginActivity.this, "数据解析失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void recordUserLoginStatus() {
        if (user != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("userLoginStatus", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isUserLoggedIn", true).apply();
        }
    }

    // 记录用户信息
    private void recordUserInfo() {
        if (user != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id", user.getId())
                    .putString("name", user.getName())
                    .putString("gender", user.getGender())
                    .putString("birthday", user.getBirthday())
                    .putString("phone", user.getPhone())
                    .apply();
        }
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
        loginUserPhone = findViewById(R.id.loginUserPhone);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
    }
}