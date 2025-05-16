package cn.gxust.project.Fragment.Home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.gxust.project.Activity.PayActivity;
import cn.gxust.project.Bean.FoodBean;
import cn.gxust.project.R;

public class ShopOrderFragment extends Fragment implements ShopOrderFoodFragment.OnUpdateCartListener {
    private ShopOrderNavFragment shopOrderNavFragment;
    private ShopOrderFoodFragment shopOrderFoodFragment;
    private TextView shopOrderFoodSales;
    private Button shopOrderPayBtn;

    private List<FoodBean> foodBeanList;
    private double cartPrice;   // 购物车总价
    private String cartContent; // 购物车内容
    private Set<String> foodBeanTypeHash;
    private List<String> foodBeanTypeList;

    // 模拟数据
    int[] foodID = {1, 2, 3, 4, 5, 6, 7, 8};
    String[] foodName = {"小炒肉", "小炒牛", "小炒鸡", "小炒鸭", "小炒羊", "小炒猪", "小炒鱼", "小炒兔"};
    String[] foodType = {"热销", "热销", "推荐", "爆款", "爆款", "热销", "推荐", "推荐"};
    int[] foodSales = {1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000};
    double[] foodPrice = {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0};
    int[] foodNum = {0, 0, 0, 0, 0, 0, 0, 0};

    public ShopOrderFragment() {
    }

    public static ShopOrderFragment newInstance() {
        return new ShopOrderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        foodBeanList = new ArrayList<>();

        for (int i = 0; i < foodID.length; i++) {
            FoodBean foodBean = new FoodBean(foodID[i], 0, foodName[i], foodType[i], foodSales[i], foodPrice[i], foodNum[i], null);
            foodBeanList.add(foodBean);
        }

        // 获取所有菜品类别组成一个新的集合 菜品类别列表
        foodBeanTypeHash = new HashSet<>();
        for (FoodBean foodBean : foodBeanList) {
            foodBeanTypeHash.add(foodBean.getFoodType());
        }
        foodBeanTypeList = new ArrayList<>(foodBeanTypeHash);

        // 根据 菜品类别列表 的顺序对 菜品列表 的数据进行重新排序
        Collections.sort(foodBeanList, (food1, food2) -> {
            int index1 = foodBeanTypeList.indexOf(food1.getFoodType());
            int index2 = foodBeanTypeList.indexOf(food2.getFoodType());
            return Integer.compare(index1, index2);
        });

        // 添加一个"全部"类别到 菜品类别列表 的最前面
        foodBeanTypeList.add(0, "全部");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_order, container, false);

        shopOrderFoodSales = rootView.findViewById(R.id.shopOrderFoodSales);
        shopOrderPayBtn = rootView.findViewById(R.id.shopOrderPayBtn);

        // 支付按钮的点击事件
        shopOrderPayBtn.setOnClickListener(v -> {
            setShopOrderPayBtnOnClickListener();
        });

        // 添加两个 Fragment
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 添加 ShopOrderNavFragment 到 shopOrderNavBlankLayout
        if (shopOrderNavFragment == null) {
            shopOrderNavFragment = new ShopOrderNavFragment();
            // 传递数据 菜品类别列表
            Bundle bundleFoodNav = new Bundle();
            bundleFoodNav.putStringArrayList("foodBeanTypeList", new ArrayList<>(foodBeanTypeList));
            shopOrderNavFragment.setArguments(bundleFoodNav);
        }
        fragmentTransaction.replace(R.id.shopOrderNavBlankLayout, shopOrderNavFragment);

        // 添加 ShopOrderFoodFragment 到 shopOrderBlankLayout
        if (shopOrderFoodFragment == null) {
            shopOrderFoodFragment = new ShopOrderFoodFragment();
            // 传递数据 菜品列表
            Bundle bundleFood = new Bundle();
            bundleFood.putSerializable("foodBeanList", new ArrayList<>(foodBeanList));
            shopOrderFoodFragment.setArguments(bundleFood);

            // 设置更新购物车的监听器
            shopOrderFoodFragment.setOnUpdateCartListener(this);
        }
        fragmentTransaction.replace(R.id.shopOrderBlankLayout, shopOrderFoodFragment);

        // 提交事务
        fragmentTransaction.commit();
        return rootView;
    }

    // 根据选择的类别更新菜品列表
    public void updateFoodBeanList(String foodType) {
        List<FoodBean> foodBeanList = new ArrayList<>();

        if (foodType.equals("全部")) {
            foodBeanList = this.foodBeanList;   // 选择全部则返回全部菜品
        } else {
            for (FoodBean foodBean : this.foodBeanList) {
                if (foodBean.getFoodType().equals(foodType)) {
                    foodBeanList.add(foodBean); // 根据选择的类别返回对应类别的菜品
                }
            }
        }

        if (shopOrderFoodFragment != null) {
            shopOrderFoodFragment.updateFoodBeanList(foodBeanList);
        }
    }

    @Override
    public void onUpdateCart(List<FoodBean> foodBeanListOnCart) {

        // 调试用 测试数据是否正确
        Log.d("ShopOrderFoodFragment", "当前购物车内容");
        for (FoodBean food : foodBeanListOnCart) {
            Log.d("ShopOrderFoodFragment", "菜品ID：" + food.getFoodID() + ", 名称：" + food.getFoodName() + ", 数量：" + food.getFoodNum());
        }

        this.cartPrice = 0;
        StringBuilder cartContentBuilder = new StringBuilder();
        for (FoodBean foodBean : foodBeanListOnCart) {
            this.cartPrice = this.cartPrice + foodBean.getFoodPrice() * foodBean.getFoodNum();
            cartContentBuilder.append(foodBean.getFoodName())
                    .append(" * ")
                    .append(foodBean.getFoodNum())
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
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            // 跳转到支付页面
            Intent intent = new Intent(getActivity(), PayActivity.class);
            intent.putExtra("cartPrice", cartPrice);            // 传递购物车价格
            intent.putExtra("cartContent", cartContent);        // 传递购物车内容
            startActivity(intent);
        }
    }
}