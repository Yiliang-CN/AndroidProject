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
    private OrderBean orderBean = new OrderBean(1, 0, null, null, null, null, 0.0, null, null, null, null, 1, null, null);


    // 订单数据
    private int orderShopID;
    private double orderPrice;
    private Long orderUserID, orderPhone;
    private String orderShopName, orderUserName, orderContent, orderTime, orderAddr, orderState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay);

        // 初始化UI控件
        initUI();
        // 订单数据预处理
        preSetOrderBean();
        // 更新UI信息
        updateUI();

        // 支付按钮点击事件
        payBtn.setOnClickListener(v -> setPayBtnOnClickListener());
        // 返回按钮点击事件
        payBtnBack.setOnClickListener(view -> finish());
    }

    // 订单数据预处理
    private void preSetOrderBean() {

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("cartPrice")) {
            orderPrice = intent.getDoubleExtra("cartPrice", 0.0);
            orderContent = intent.getStringExtra("cartContent");
        }

        // 店铺ID 店铺名
        SharedPreferences sharedPreferences = getSharedPreferences("shopInfo", MODE_PRIVATE);
        orderShopID = sharedPreferences.getInt("shopID", 0);
        orderShopName = sharedPreferences.getString("shopName", "null");

        // 用户ID 用户名
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        orderUserID = sharedPreferences.getLong("userID", 0);
        orderUserName = sharedPreferences.getString("userName", "null");

        // 时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        orderTime = simpleDateFormat.format(new Date());

        // 状态
        orderState = "待支付";
    }

    // 支付按钮点击的事件 内含地址和电话数据
    private void setPayBtnOnClickListener() {
        // 获取订单信息
        if (!getOrderInfo()) {
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

    // 获取订单信息
    private boolean getOrderInfo() {
        orderAddr = payOrderAddr.getText().toString();
        String orderPhoneStr = payOrderPhone.getText().toString();

        // 检查数据是否完整
        if (orderAddr.isEmpty() && orderPhoneStr.isEmpty()) {
            Toast.makeText(this, "请填写完订单信息", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            orderPhone = Long.valueOf(orderPhoneStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // 保存订单信息 数据用于提交服务器
    private void setOrderBean() {
        orderBean.setOrderShopID(orderShopID);
        orderBean.setOrderShopName(orderShopName);
        orderBean.setOrderUserID(orderUserID);
        orderBean.setOrderUserName(orderUserName);
        orderBean.setOrderContent(orderContent);
        orderBean.setOrderPrice(orderPrice);
        orderBean.setOrderTime(orderTime);
        orderBean.setOrderAddr(orderAddr);
        orderBean.setOrderPhone(Long.valueOf(orderPhone));
        orderBean.setOrderState("待支付");
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
        payOrderShopName.setText(orderShopName);
        payOrderUserName.setText(orderUserName);
        payOrderContent.setText(orderContent);
        payOrderPrice.setText(String.valueOf(orderPrice));
        payOrderTime.setText(orderTime);
        payOrderState.setText(orderState);
    }
}