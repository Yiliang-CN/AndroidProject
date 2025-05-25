package cn.gxust.project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
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

import cn.gxust.project.Utils.DialogUtils;
import cn.gxust.project.Utils.OkHttpUtils;
import cn.gxust.project.Utils.ValidationUtils;
import cn.gxust.project.bean.OrderBean;
import cn.gxust.project.R;

public class OrderActivity extends AppCompatActivity {

    private ImageView orderBtnBack;
    private TextView orderId, orderShopName, orderUserName, orderContent, orderPrice, orderTime, orderAddr, orderPhone, orderState;

    private TextView orderScoreText, orderCmtText;
    private EditText orderScore, orderCmt;
    private Button orderCmtBtn;

    private OrderBean orderBean;
    private double score;
    private String content;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 100) {
                handleResponse((String) msg.obj);
            }
        }
    };

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

        // 设置控件是否可见可用
        setViewsVisibility();

        orderCmtBtn.setOnClickListener(v -> setOrderCmtBtnOnClickListener());

        // 返回按钮点击事件
        orderBtnBack.setOnClickListener(v -> finish());
    }

    private void setOrderCmtBtnOnClickListener() {
        if (!getCmtInfo()) {
            return;
        }
        postHttpData();
    }

    private void postHttpData() {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("shopId", orderBean.getShopId());
            jsonParam.put("userId", orderBean.getUserId());
            jsonParam.put("orderId", orderBean.getId());
            jsonParam.put("score", score);
            jsonParam.put("content", content);
        } catch (JSONException e) {
            Toast.makeText(OrderActivity.this, "构建请求参数失败", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpUtils.getInstance().doPost(getString(R.string.base_url_comments), jsonParam.toString(), new OkHttpUtils.OkHttpCallback() {
            @Override
            public void onFailure(IOException e) {
                DialogUtils.showConfirmDialog("错误", "评论失败", "确定", OrderActivity.this, null);
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

    private void handleResponse(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int code = jsonObject.getInt("code");
            if (code != 200) {
                String errorMsg = jsonObject.getString("message");
                Toast.makeText(OrderActivity.this, "获取数据失败! code: " + code + "message: " + errorMsg, Toast.LENGTH_SHORT).show();
                return;
            }
            DialogUtils.showConfirmDialog("提示", "评论成功!", "确认", OrderActivity.this, null);
        } catch (JSONException e) {
            Toast.makeText(OrderActivity.this, "数据解析失败", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean getCmtInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        orderBean.setUserId(sharedPreferences.getInt("id", 1));

        sharedPreferences = getSharedPreferences("shopInfo", MODE_PRIVATE);
        orderBean.setShopId(sharedPreferences.getInt("id", 1));

        if (!ValidationUtils.isValidScore(orderScore.getText().toString())) {
            Toast.makeText(this, "评分为1-5分", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!ValidationUtils.isValidContent(orderCmt.getText().toString())) {
            Toast.makeText(this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        score = Double.parseDouble(orderScore.getText().toString());
        content = orderCmt.getText().toString();

        return true;
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
        orderId = findViewById(R.id.orderId);
        orderShopName = findViewById(R.id.orderShopName);
        orderUserName = findViewById(R.id.orderUserName);
        orderContent = findViewById(R.id.orderContent);
        orderPrice = findViewById(R.id.orderPrice);
        orderTime = findViewById(R.id.orderTime);
        orderAddr = findViewById(R.id.orderAddr);
        orderPhone = findViewById(R.id.orderPhone);
        orderState = findViewById(R.id.orderState);
        orderScoreText = findViewById(R.id.orderScoreText);
        orderScore = findViewById(R.id.orderScore);
        orderCmtText = findViewById(R.id.orderCmtText);
        orderCmt = findViewById(R.id.orderCmt);
        orderCmtBtn = findViewById(R.id.orderCmtBtn);
    }

    private void updateUI() {
        if (orderBean != null) {
            orderId.setText(String.valueOf(orderBean.getId()));
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

    // 设置控件是否可见可用
    private void setViewsVisibility() {
        if(orderBean.getState().equals("已完成")){
            orderScoreText.setVisibility(View.VISIBLE);
            orderScore.setVisibility(View.VISIBLE);
            orderCmtText.setVisibility(View.VISIBLE);
            orderCmt.setVisibility(View.VISIBLE);
            orderCmtBtn.setVisibility(View.VISIBLE);
        }else{

            orderScoreText.setVisibility(View.GONE);
            orderScore.setVisibility(View.GONE);
            orderCmtText.setVisibility(View.GONE);
            orderCmt.setVisibility(View.GONE);
            orderCmtBtn.setVisibility(View.GONE);
        }
    }
}