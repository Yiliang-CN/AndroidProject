package cn.gxust.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        orderBtnBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("orderBean")) {
            orderBean = (OrderBean) intent.getSerializableExtra("orderBean");
            if (orderBean != null) {
                orderID.setText(String.valueOf(orderBean.getOrderID()));
                orderShopName.setText(orderBean.getOrderShopName());
                orderUserName.setText(orderBean.getOrderUserName());
                orderContent.setText(orderBean.getOrderContent());
                orderPrice.setText(orderBean.getOrderPrice());
                orderTime.setText(orderBean.getOrderTime());
                orderAddr.setText(orderBean.getOrderAddr());
                orderPhone.setText(orderBean.getOrderPhone());
                orderState.setText(orderBean.getOrderState());
            }
        }

    }
}