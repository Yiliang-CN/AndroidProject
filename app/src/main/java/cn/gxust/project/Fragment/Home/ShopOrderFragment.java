package cn.gxust.project.Fragment.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.gxust.project.Bean.FoodBean;
import cn.gxust.project.R;

public class ShopOrderFragment extends Fragment {
    private ShopOrderNavFragment shopOrderNavFragment;
    private ShopOrderFoodFragment shopOrderFoodFragment;

    private List<FoodBean> foodBeanList;
    private Set<String> foodBeanType;
    private List<String> foodBeanTypeList;

    // 模拟数据
    int[] foodID = {1, 2, 3, 4, 5, 6, 7, 8};
    String[] foodName = {"小炒肉", "小炒牛", "小炒鸡", "小炒鸭", "小炒羊", "小炒猪", "小炒牛", "小炒鸡"};
    String[] foodType = {"热销", "热销", "热销", "爆款", "爆款", "爆款", "推荐", "推荐"};
    String[] foodSales = {"1000", "1000", "1000", "1000", "1000", "1000", "1000", "1000"};
    String[] foodPrice = {"10", "10", "10", "10", "10", "10", "10", "10"};

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
            FoodBean foodBean = new FoodBean(foodID[i], foodName[i], foodType[i], foodSales[i], foodPrice[i], null);
            foodBeanList.add(foodBean);
        }

        // 获取所有菜品类别
        foodBeanType = new HashSet<>();
        for (FoodBean foodBean : foodBeanList) {
            foodBeanType.add(foodBean.getFoodType());
        }
        foodBeanTypeList = new ArrayList<>(foodBeanType);

        foodBeanTypeList.add(0, "全部");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_order, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 添加 ShopOrderNavFragment 到 shopOrderNavBlankLayout
        if (shopOrderNavFragment == null) {
            shopOrderNavFragment = new ShopOrderNavFragment();
            // 传递数据
            Bundle bundleFoodNav = new Bundle();
            bundleFoodNav.putStringArrayList("foodBeanTypeList", new ArrayList<>(foodBeanTypeList));
            shopOrderNavFragment.setArguments(bundleFoodNav);
        }
        fragmentTransaction.replace(R.id.shopOrderNavBlankLayout, shopOrderNavFragment);

        // 添加 ShopOrderFoodFragment 到 shopOrderBlankLayout
        if (shopOrderFoodFragment == null) {
            shopOrderFoodFragment = new ShopOrderFoodFragment();
            // 传递数据
            Bundle bundleFood = new Bundle();
            bundleFood.putSerializable("foodBeanList", new ArrayList<>(foodBeanList));
            shopOrderFoodFragment.setArguments(bundleFood);
        }
        fragmentTransaction.replace(R.id.shopOrderBlankLayout, shopOrderFoodFragment);

        fragmentTransaction.commit();

        return rootView;
    }

    // 更新菜品列表
    public void updateFoodBeanList(String foodType) {
        List<FoodBean> foodBeanList = new ArrayList<>();

        if (foodType.equals("全部")) {
            foodBeanList = this.foodBeanList;
        } else {
            for (FoodBean foodBean : this.foodBeanList) {
                if (foodBean.getFoodType().equals(foodType)) {
                    foodBeanList.add(foodBean);
                }
            }
        }

        if (shopOrderFoodFragment != null) {
            shopOrderFoodFragment.updateFoodList(foodBeanList);
        }
    }
}