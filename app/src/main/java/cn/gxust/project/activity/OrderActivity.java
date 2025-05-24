package cn.gxust.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import cn.gxust.project.bean.OrderBean;
import cn.gxust.project.R;

public class OrderActivity extends AppCompatActivity {

    private ImageView orderBtnBack;
    private TextView orderID, orderShopName, orderUserName, orderContent, orderPrice, orderTime, orderAddr, orderPhone, orderState;

    private OrderBean orderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);

        // 初始化UI控件
        initUI();

        // 接收订单信息
        recvOrderBean();

        // 更新UI控件
        updateUI();

        // 返回按钮点击事件
        orderBtnBack.setOnClickListener(v -> finish());
    }

    private void recvOrderBean() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("orderBean")) {
            orderBean = (OrderBean) intent.getSerializableExtra("orderBean");
        }
        // 检测数据是否存在 不存在则退出
        if (orderBean == null) {
            Toast.makeText(this, "获取订单数据失败", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // 初始化UI控件
    private void initUI() {
        orderBtnBack = findViewById(R.id.orderBtnBack);
        orderID = findViewById(R.id.orderID);
        orderShopName = findViewById(R.id.orderShopName);
        orderUserName = findViewById(R.id.orderUserName);
        orderContent = findViewById(R.id.orderContent);
        orderPrice = findViewById(R.id.orderPrice);
        orderTime = findViewById(R.id.orderTime);
        orderAddr = findViewById(R.id.orderAddr);
        orderPhone = findViewById(R.id.orderPhone);
        orderState = findViewById(R.id.orderState);
    }

    private void updateUI() {
        if (orderBean != null) {
            orderID.setText(String.valueOf(orderBean.getId()));
            orderShopName.setText(orderBean.getShopName());
            orderUserName.setText(orderBean.getUserName());
            orderContent.setText(orderBean.getContent());
            orderPrice.setText(String.valueOf(orderBean.getPrice()));
            orderTime.setText(orderBean.getTime());
            orderAddr.setText(orderBean.getAddr());
            orderPhone.setText(orderBean.getPhone());
            orderState.setText(orderBean.getState());
        }
    }
}