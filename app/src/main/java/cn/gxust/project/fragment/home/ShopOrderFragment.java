package cn.gxust.project.fragment.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.gxust.project.Utils.OkHttpUtils;
import cn.gxust.project.activity.PayActivity;
import cn.gxust.project.bean.FoodBean;
import cn.gxust.project.R;

public class ShopOrderFragment extends Fragment implements ShopOrderFoodFragment.OnUpdateCartListener {

    //定义常量
    private static final String BASE_URL = "http://10.0.2.2:8080/shops/";
    private static final String URL_SUFFIX = "/foods";
    private int shopId;

    private ShopOrderNavFragment shopOrderNavFragment;
    private ShopOrderFoodFragment shopOrderFoodFragment;
    private TextView shopOrderFoodSales;
    private Button shopOrderPayBtn;

    private List<FoodBean> foodBeans;
    private double cartPrice;   // 购物车总价
    private String cartContent; // 购物车内容
    private Set<String> foodBeanTypeHash;
    private List<String> foodBeanTypeList;


    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 100) {
                // 处理网络响应
                handleResponse((String) msg.obj);
            }
        }
    };


    public ShopOrderFragment() {
    }

    public static ShopOrderFragment newInstance() {
        return new ShopOrderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取店铺ID
        getShopId();

        foodBeans = new ArrayList<>();
        foodBeanTypeHash = new HashSet<>();
        foodBeanTypeList = new ArrayList<>(foodBeanTypeHash);

        // 请求数据
        getHttpData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_order, container, false);

        // 初始化UI控件
        initUI(rootView);

        // 支付按钮点击事件
        shopOrderPayBtn.setOnClickListener(v -> setShopOrderPayBtnOnClickListener());

        return rootView;
    }

    // 请求数据
    private void getHttpData() {
        String URL = BASE_URL + shopId + URL_SUFFIX;

        OkHttpUtils.getInstance().doGet(URL, new OkHttpUtils.OkHttpCallback() {
            @Override
            public void onFailure(IOException e) {
                Toast.makeText(requireContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(requireContext(), "获取数据失败! code: " + code + "message: " + errorMsg, Toast.LENGTH_SHORT).show();
                return;
            }

            JSONArray dataArray = jsonObject.getJSONArray("data");
            foodBeans.clear();
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject foodObject = dataArray.getJSONObject(i);
                FoodBean foodBean = new FoodBean();
                foodBean.setName(foodObject.getString("name"));
                foodBean.setType(foodObject.getString("type"));
                foodBean.setSales(foodObject.getInt("sales"));
                foodBean.setPrice(foodObject.getDouble("price"));
                foodBean.setNum(foodObject.getInt("num"));
                if (!foodObject.isNull("image")) {
                    foodBean.setImage(foodObject.getString("image"));
                }
                foodBeans.add(foodBean);
            }

            // 获取所有菜品类别组成一个新的集合 菜品类别列表
            for (FoodBean foodBean : foodBeans) {
                foodBeanTypeHash.add(foodBean.getType());
            }
            foodBeanTypeList.clear();
            foodBeanTypeList.addAll(foodBeanTypeHash);
            foodBeanTypeList.add(0, "全部");

            // 请求成功数据成功后设置子Fragment
            setChildFragment();

        } catch (JSONException e) {
            Toast.makeText(requireContext(), "数据解析失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 设置子Fragment
    private void setChildFragment() {
        // 添加两个 Fragment
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        // 添加 ShopOrderNavFragment 到 shopOrderNavBlankLayout
        // 传递数据 菜品类别列表
        if (shopOrderNavFragment == null) {
            shopOrderNavFragment = ShopOrderNavFragment.newInstance(new ArrayList<>(foodBeanTypeList));
        }
        fragmentTransaction.replace(R.id.shopOrderNavBlankLayout, shopOrderNavFragment);

        // 添加 ShopOrderFoodFragment 到 shopOrderBlankLayout
        // 传递数据 菜品列表
        if (shopOrderFoodFragment == null) {
            shopOrderFoodFragment = ShopOrderFoodFragment.newInstance(new ArrayList<>(foodBeans));
        }
        fragmentTransaction.replace(R.id.shopOrderBlankLayout, shopOrderFoodFragment);

        // 设置更新购物车的监听器
        shopOrderFoodFragment.setOnUpdateCartListener(this);

        // 提交事务
        fragmentTransaction.commit();
    }

    // 根据选择的类别更新菜品列表
    public void updateFoodBeanList(String foodType) {
        List<FoodBean> foodBeanList = new ArrayList<>();

        if (foodType.equals("全部")) {
            foodBeanList = this.foodBeans;   // 选择全部则返回全部菜品
        } else {
            for (FoodBean foodBean : this.foodBeans) {
                if (foodBean.getType().equals(foodType)) {
                    foodBeanList.add(foodBean); // 根据选择的类别返回对应类别的菜品
                }
            }
        }

        if (shopOrderFoodFragment != null) {
            shopOrderFoodFragment.updateFoodBeanList(foodBeanList);
        }
    }

    // 更新购物车
    @Override
    public void onUpdateCart(List<FoodBean> foodBeanListOnCart) {

        // 调试用 测试数据是否正确
        Log.d("ShopOrderFoodFragment", "当前购物车内容");
        for (FoodBean food : foodBeanListOnCart) {
            Log.d("ShopOrderFoodFragment", "菜品名：" + food.getName() + ", 数量：" + food.getNum());
        }

        this.cartPrice = 0;
        StringBuilder cartContentBuilder = new StringBuilder();
        for (FoodBean foodBean : foodBeanListOnCart) {
            this.cartPrice = this.cartPrice + foodBean.getPrice() * foodBean.getNum();
            cartContentBuilder.append(foodBean.getName())
                    .append(" * ")
                    .append(foodBean.getNum())
                    .append("份")
                    .append("\n");
        }
        this.cartContent = cartContentBuilder.toString();

        shopOrderFoodSales.setText("￥" + String.valueOf(this.cartPrice));
    }

    // 支付按钮的点击事件
    private void setShopOrderPayBtnOnClickListener() {

        // 获取用户登录状态
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLoginStatus", MODE_PRIVATE);
        boolean isUserLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false);

        // 判断用户是否登录
        if (!isUserLoggedIn) {
            // 提示用户请先登录
            Toast.makeText(getActivity(), "请先登录!", Toast.LENGTH_SHORT).show();
        } else {
            // 跳转到支付页面
            Intent intent = new Intent(getActivity(), PayActivity.class);
            intent.putExtra("cartPrice", cartPrice);            // 传递购物车价格
            intent.putExtra("cartContent", cartContent);        // 传递购物车内容
            startActivity(intent);
        }
    }

    // 获取商家ID
    private void getShopId() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shopInfo", MODE_PRIVATE);
        shopId = sharedPreferences.getInt("id", 0);
    }


    // 初始化UI控件
    private void initUI(View rootView) {
        shopOrderFoodSales = rootView.findViewById(R.id.shopOrderFoodSales);
        shopOrderPayBtn = rootView.findViewById(R.id.shopOrderPayBtn);
    }
}