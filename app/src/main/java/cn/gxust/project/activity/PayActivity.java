package cn.gxust.project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cn.gxust.project.Utils.DialogUtils;
import cn.gxust.project.Utils.OkHttpUtils;
import cn.gxust.project.Utils.ValidationUtils;
import cn.gxust.project.R;

public class PayActivity extends AppCompatActivity {

    private TextView payOrderShopName, payOrderUserName, payOrderContent, payOrderPrice, payOrderTime, payOrderState;
    private EditText payOrderAddr, payOrderPhone;

    private ImageView payBtnBack;
    private Button payBtn;

    // 订单数据
    private int shopId, userId;
    private String content, addr, phone, shopName, userName, time;
    private double price;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 100) {
                handlerResponse((String) msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay);

        // 初始化UI控件
        initUI();
        // 设置订单信息
        setOrderInfo();
        // 更新UI信息
        updateUI();

        // 支付按钮点击事件
        payBtn.setOnClickListener(v -> setPayBtnOnClickListener());
        // 返回按钮点击事件
        payBtnBack.setOnClickListener(view -> finish());
    }

    // 支付按钮点击的事件 内含地址和电话数据
    private void setPayBtnOnClickListener() {
        // 获取订单信息
        if (!getOrderInfo()) return;

        // 发送网络请求
        postHttpData();
    }

    private void postHttpData() {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("shopId", shopId);
            jsonParam.put("userId", userId);
            jsonParam.put("content", content);
            jsonParam.put("price", price);
            jsonParam.put("addr", addr);
            jsonParam.put("phone", phone);
        } catch (JSONException e) {
            runOnUiThread(() -> Toast.makeText(PayActivity.this, "构建请求参数失败", Toast.LENGTH_SHORT).show());
            return;
        }

        OkHttpUtils.getInstance().doPost(getString(R.string.base_url_orders), jsonParam.toString(), new OkHttpUtils.OkHttpCallback() {
            @Override
            public void onFailure(IOException e) {
                runOnUiThread(() -> Toast.makeText(PayActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show());
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

    // 处理网络响应
    private void handlerResponse(String jsonData) {
        // 检验返回结果
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getInt("code") != 200) {
                DialogUtils.showConfirmDialog("支付失败", "支付失败，请稍后重试", "确认", this, this::finish);
            }

            // 支付成功弹窗提示
            DialogUtils.showConfirmDialog("支付成功", "您的订单已支付成功，点击确认继续", "确认", this, this::finish);

        } catch (JSONException e) {
            // 支付失败弹窗提示
            DialogUtils.showConfirmDialog("支付失败", "支付失败，请稍后重试", "确认", this, this::finish);
        }
    }

    // 设置订单信息
    private void setOrderInfo() {

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("cartPrice")) {
            price = intent.getDoubleExtra("cartPrice", 0.0);
            content = intent.getStringExtra("cartContent");
        }

        // 店铺ID 店铺名
        SharedPreferences sharedPreferences = getSharedPreferences("shopInfo", MODE_PRIVATE);
        shopId = sharedPreferences.getInt("id", 0);
        shopName = sharedPreferences.getString("name", "null");

        // 用户ID 用户名
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);
        userName = sharedPreferences.getString("name", "null");

        // 时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        time = simpleDateFormat.format(new Date());
    }

    // 获取地址和电话
    private boolean getOrderInfo() {
        addr = payOrderAddr.getText().toString();
        phone = payOrderPhone.getText().toString();

        if (!ValidationUtils.isValidPhone(phone)) {
            Toast.makeText(this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (addr.isEmpty()) {
            Toast.makeText(this, "请输入地址", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // 初始化UI控件
    private void initUI() {
        payBtnBack = findViewById(R.id.payBtnBack);
        payOrderShopName = findViewById(R.id.payOrderShopName);
        payOrderUserName = findViewById(R.id.payOrderUserName);
        payOrderContent = findViewById(R.id.payOrderContent);
        payOrderPrice = findViewById(R.id.payOrderPrice);
        payOrderTime = findViewById(R.id.payOrderTime);
        payOrderAddr = findViewById(R.id.payOrderAddr);
        payOrderPhone = findViewById(R.id.payOPhone);
        payOrderState = findViewById(R.id.payOrderState);
        payBtn = findViewById(R.id.payBtn);
    }

    // 更新UI信息
    private void updateUI() {
        payOrderShopName.setText(shopName);
        payOrderUserName.setText(userName);
        payOrderContent.setText(content);
        payOrderPrice.setText(String.valueOf(price));
        payOrderTime.setText(time);
        payOrderState.setText("待支付");
    }
}