package cn.gxust.project.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.gxust.project.Adapter.FoodAdapter;
import cn.gxust.project.Bean.FoodBean;
import cn.gxust.project.R;


public class ShopOrderFragment extends Fragment {
    private ListView foodListView;
    private List<FoodBean> foodBeanList;
    private FoodAdapter foodAdapter;

    // 模拟数据
    int[] foodID = {1, 2, 3, 4, 5, 6, 7, 8};
    String[] foodName = {"小炒肉", "小炒牛", "小炒鸡", "小炒鸭", "小炒羊", "小炒猪", "小炒牛", "小炒鸡"};
    String[] foodType = {"热销", "热销", "热销", "热销", "热销", "热销", "热销", "热销"};
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
        foodAdapter = new FoodAdapter(foodBeanList, getContext());

        for (int i = 0; i < foodID.length; i++) {
            FoodBean foodBean = new FoodBean(foodID[i], foodName[i], foodType[i], foodSales[i], foodPrice[i], null);
            foodBeanList.add(foodBean);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_order, container, false);

        foodListView = rootView.findViewById(R.id.foodListView);

        foodAdapter = new FoodAdapter(foodBeanList, getContext());
        foodListView.setAdapter(foodAdapter);

        return rootView;
    }
}