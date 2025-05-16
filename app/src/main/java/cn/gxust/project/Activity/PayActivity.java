package cn.gxust.project.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cn.gxust.project.Bean.OrderBean;
import cn.gxust.project.R;

public class PayActivity extends AppCompatActivity {
    private TextView payOrderShopName, payOrderUserName, payOrderContent, payOrderPrice, payOrderTime, payOrderState;
    private EditText payOrderAddr, payOrderPhone;

    private ImageView payBtnBack;
    private Button payBtn;
    private OrderBean orderBean;

    // 订单数据
    private int orderShopID;
    private double orderPrice;
    private Long orderUserID;
    private String orderShopName, orderUserName, orderContent, orderTime, orderAddr, orderPhone, orderState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay);

        // 初始化控件
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

        // 返回按钮
        payBtnBack.setOnClickListener(view -> finish());

        orderBean = new OrderBean(1, 0, null, null, null, null, 0.0, null, null, null, null, null, null);
        // 订单数据处理
        preSetOrderBean(); // 订单数据预处理

        // 更新UI信息
        payOrderShopName.setText(this.orderShopName);
        payOrderUserName.setText(this.orderUserName);
        payOrderContent.setText(this.orderContent);
        payOrderPrice.setText(String.valueOf(this.orderPrice));
        payOrderTime.setText(this.orderTime);
        payOrderState.setText(this.orderState);

        // 支付按钮
        payBtn.setOnClickListener(v -> {
            setPayBtnOnClickListener();
        });
    }

    // 设置订单数据 这个数据是提交给服务器的数据 地址和电话数据在提交按钮处设置
    private void preSetOrderBean() {

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("cartPrice")) {
            this.orderPrice = intent.getDoubleExtra("cartPrice", 0.0);
            this.orderContent = intent.getStringExtra("cartContent");
        }

        // 店铺ID 店铺名
        SharedPreferences sharedPreferences = getSharedPreferences("shopInfo", MODE_PRIVATE);
        this.orderShopID = sharedPreferences.getInt("shopID", 0);
        this.orderShopName = sharedPreferences.getString("shopName", "null");

        // 用户ID 用户名
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        this.orderUserID = sharedPreferences.getLong("userID", 0);
        this.orderUserName = sharedPreferences.getString("userName", "null");

        // 时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        this.orderTime = simpleDateFormat.format(new Date());

        // 状态
        this.orderState = "待支付";
    }

    // 支付按钮点击的事件
    private void setPayBtnOnClickListener() {
        this.orderAddr = payOrderAddr.getText().toString();
        this.orderPhone = payOrderPhone.getText().toString();

        // 检查数据是否完整
        if (this.orderAddr.isEmpty() && this.orderPhone.isEmpty()) {
            Toast.makeText(this, "请填写完所有信息", Toast.LENGTH_SHORT).show();
            return;
        }

        // 保存订单信息
        setOrderBean();

        // 支付成功后弹窗提示
        new AlertDialog.Builder(this)
                .setTitle("支付成功")
                .setMessage("您的订单已支付成功，点击确认继续")
                .setPositiveButton("确认", (dialog, which) -> {
                    finish();
                })
                .setCancelable(false) // 不可取消 只有确定
                .show();
    }

    // 保存订单信息
    private void setOrderBean() {
        this.orderBean.setOrderShopID(this.orderShopID);
        this.orderBean.setOrderShopName(this.orderShopName);
        this.orderBean.setOrderUserID(this.orderUserID);
        this.orderBean.setOrderUserName(this.orderUserName);
        this.orderBean.setOrderContent(this.orderContent);
        this.orderBean.setOrderPrice(this.orderPrice);
        this.orderBean.setOrderTime(this.orderTime);
        this.orderBean.setOrderAddr(this.orderAddr);
        this.orderBean.setOrderPhone(Long.valueOf(this.orderPhone));
        this.orderBean.setOrderState("待支付");
    }
}