package cn.gxust.project.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.gxust.project.Utils.OkHttpUtils;
import cn.gxust.project.activity.OrderActivity;
import cn.gxust.project.adapter.OrderAdapter;
import cn.gxust.project.bean.OrderBean;
import cn.gxust.project.R;

public class OrderFragment extends Fragment implements OrderAdapter.OnOrderAdapterListener {

    private static final String BASE_URL = "http://10.0.2.2:8080/users/";
    private static final String URL_SUFFIX = "/orders";
    private int userId;

    private ListView orderListView;
    private List<OrderBean> orderBeans;
    private OrderAdapter orderAdapter;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 100) {
                // 处理网络响应
                handleResponse((String) msg.obj);
            }
        }
    };

    public OrderFragment() {
    }

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取商家ID
        getUserId();

        orderBeans = new ArrayList<>();

        // 先登录再请求数据
        if (isUserLoggedIn()) {
            getHttpData();
        } else {
            Toast.makeText(getActivity(), "请先登录再查看历史订单", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载根View
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        // 初始化ListView
        orderListView = rootView.findViewById(R.id.orderListView);

        // 创建并设置Adapter
        orderAdapter = new OrderAdapter(orderBeans, getContext(), this);
        orderListView.setAdapter(orderAdapter);

        return rootView;
    }

    private void getHttpData() {
        Log.d("OrderFragment", "getHttpData: " + BASE_URL + userId + URL_SUFFIX);
        OkHttpUtils.getInstance().doGet(BASE_URL + userId + URL_SUFFIX, new OkHttpUtils.OkHttpCallback() {
            @Override
            public void onFailure(IOException e) {
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
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
    private void handleResponse(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int code = jsonObject.getInt("code");
            if (code != 200) {
                String errorMsg = jsonObject.getString("message");
                Toast.makeText(requireContext(), "获取数据失败! code: " + code + "message: " + errorMsg, Toast.LENGTH_SHORT).show();
                return;
            }

            // 获取数据数组
            JSONArray dataArray = jsonObject.getJSONArray("data");
            orderBeans.clear();

            // 遍历JSON数组 解析数据
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject orderObject = dataArray.getJSONObject(i);
                OrderBean orderBean = new OrderBean();
                orderBean.setId(orderObject.getLong("id"));
                orderBean.setShopName(orderObject.getString("shopName"));
                orderBean.setUserName(orderObject.getString("userName"));
                orderBean.setContent(orderObject.getString("content"));
                orderBean.setPrice(orderObject.getDouble("price"));
                orderBean.setTime(orderObject.getString("time"));
                orderBean.setAddr(orderObject.getString("addr"));
                orderBean.setPhone(orderObject.getString("phone"));
                orderBean.setState(orderObject.getString("state"));
                orderBeans.add(orderBean);
            }
            orderAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Toast.makeText(requireContext(), "数据解析失败", Toast.LENGTH_SHORT).show();
        }
    }


    // 订单项中点击店名即可打开订单详情页
    // 为什么要这样弄 因为订单项的布局中包含有 按钮 按钮会覆盖掉 项 本身的点击事件 所以只能单独选择店名的作为详细页面的入口
    @Override
    public void setOrderShopNameOnClickListener(OrderBean orderBean) {
        Intent intent = new Intent(getContext(), OrderActivity.class);
        intent.putExtra("orderBean", orderBean);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 设置启动标志，使得Activity在栈顶
        startActivity(intent);
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userLoginStatus", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isUserLoggedIn", false);   // 默认为未登录
    }

    // 获取商家ID
    private void getUserId() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);
    }
}