package cn.gxust.project.Activity;

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

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.gxust.project.Bean.UserBean;
import cn.gxust.project.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    // 常量定义
    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static final String LOGIN_ENDPOINT = "/users/login";
    private static final String PHONE_REGEX = "^1[3-9][0-9]{9}$";

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
        getHttpData();
    }

    // 获取用户手机号和密码 用于登录验证
    private boolean getUserInfo() {
        userPhone = loginUserPhone.getText().toString();
        password = loginPassword.getText().toString();

        // 判断用户输入是否为空
        if (userPhone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入完整的账号信息", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 验证手机号格式
        if (!userPhone.matches("^1[3-9][0-9]{9}$")) {
            Toast.makeText(this, "手机号格式有误", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 验证密码格式
        if (password.length() < 6 || password.length() > 20) {
            Toast.makeText(this, "密码长度在6-20位之间", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void getHttpData() {
        // 创建JSON参数
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("phone", userPhone);
            jsonParam.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "构建请求参数失败", Toast.LENGTH_SHORT).show());
            return;
        }

        // 创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        // 创建请求体
        RequestBody body = RequestBody.create(jsonParam.toString(), MediaType.get("application/json; charset=utf-8"));

        // 创建Request对象
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_ENDPOINT)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);

        // 通过Call对象的enqueue()方法发送异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NotNull IOException e) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "网络请求失败，请检查网络连接", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();

                    // 不能直接在UI线程中处理数据
                    Message message = new Message();
                    message.what = 100;
                    message.obj = jsonData;
                    handler.sendMessage(message);
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "登录失败，服务器返回错误", Toast.LENGTH_SHORT).show());
                }
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
                Toast.makeText(LoginActivity.this, "登陆失败: " + errorMsg, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(LoginActivity.this, "JSON解析失败", Toast.LENGTH_SHORT).show();
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
                    .putString("phone", user.getPhone());
            editor.apply();
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