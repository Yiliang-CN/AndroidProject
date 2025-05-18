package cn.gxust.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import cn.gxust.project.Bean.OrderBean;
import cn.gxust.project.Bean.ShopBean;
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
            orderID.setText(String.valueOf(orderBean.getOrderID()));
            orderShopName.setText(orderBean.getOrderShopName());
            orderUserName.setText(orderBean.getOrderUserName());
            orderContent.setText(orderBean.getOrderContent());
            orderPrice.setText(String.valueOf(orderBean.getOrderPrice()));
            orderTime.setText(orderBean.getOrderTime());
            orderAddr.setText(orderBean.getOrderAddr());
            orderPhone.setText(String.valueOf(orderBean.getOrderPhone()));
            orderState.setText(orderBean.getOrderState());
        }
    }
}