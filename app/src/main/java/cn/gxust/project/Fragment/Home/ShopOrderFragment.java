package cn.gxust.project.Fragment.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.gxust.project.Bean.FoodBean;
import cn.gxust.project.R;

public class ShopOrderFragment extends Fragment {
    private ShopOrderNavFragment shopOrderNavFragment;
    private ShopOrderFoodFragment shopOrderFoodFragment;

    private List<FoodBean> foodBeanList;
    private Set<String> foodBeanTypeHash;
    private List<String> foodBeanTypeList;

    // 模拟数据
    int[] foodID = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    String[] foodName = {"小炒肉", "小炒牛", "小炒鸡", "小炒鸭", "小炒羊", "小炒猪", "小炒鱼", "小炒兔",
            "红烧肉", "糖醋排骨", "水煮鱼", "宫保鸡丁",
            "麻辣香锅", "酸菜鱼", "回锅肉", "麻婆豆腐",
            "烤全羊", "北京烤鸭", "佛跳墙", "龙虾盛宴"};
    String[] foodType = {"热销", "热销", "推荐", "爆款", "爆款", "热销", "推荐", "推荐",
            "热销", "热销", "热销", "热销",
            "推荐", "推荐", "推荐", "推荐",
            "爆款", "爆款", "爆款", "爆款"};
    String[] foodSales = {"1000", "1000", "1000", "1000", "1000", "1000", "1000", "1000",
            "950", "900", "850", "800",
            "750", "700", "650", "600",
            "2000", "1800", "1500", "1200"};
    String[] foodPrice = {"10", "10", "10", "10", "10", "10", "10", "10",
            "15", "18", "20", "16",
            "22", "25", "18", "12",
            "88", "68", "128", "158"};

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
        foodBeanTypeHash = new HashSet<>();
        for (FoodBean foodBean : foodBeanList) {
            foodBeanTypeHash.add(foodBean.getFoodType());
        }
        foodBeanTypeList = new ArrayList<>(foodBeanTypeHash);

        // 将存储菜品信息的List根据菜品类别进行排序
        Collections.sort(foodBeanList, (food1, food2) -> {
            int index1 = foodBeanTypeList.indexOf(food1.getFoodType());
            int index2 = foodBeanTypeList.indexOf(food2.getFoodType());
            return Integer.compare(index1, index2);
        });

        // 添加一个"全部"类别到foodBeanTypeList的最前面
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

        // 提交事务
        fragmentTransaction.commit();
        return rootView;
    }

    // 找到对应类别的位置
    public void findFoodTypePosition(String foodType) {

        if ("全部".equals(foodType)) {
            shopOrderFoodFragment.scrollToPosition(0);
            return;
        }

        for (FoodBean foodBean : foodBeanList) {
            if (foodType.equals(foodBean.getFoodType())) {
                int position = foodBeanList.indexOf(foodBean);
                shopOrderFoodFragment.scrollToPosition(position);
                return;
            }
        }
    }
}