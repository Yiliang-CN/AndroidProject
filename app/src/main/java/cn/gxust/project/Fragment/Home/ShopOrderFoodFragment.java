package cn.gxust.project.Fragment.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cn.gxust.project.Adapter.FoodAdapter;
import cn.gxust.project.Bean.FoodBean;
import cn.gxust.project.R;


public class ShopOrderFoodFragment extends Fragment {
    private ListView foodListView;
    private List<FoodBean> foodBeanList;
    private FoodAdapter foodAdapter;

    public ShopOrderFoodFragment() {
    }

    public static ShopOrderFoodFragment newInstance() {
        return new ShopOrderFoodFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            foodBeanList = (List<FoodBean>) getArguments().getSerializable("foodBeanList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_order_food, container, false);

        // 初始化ListView
        foodListView = rootView.findViewById(R.id.foodListView);

        // 创建并设置Adapter
        foodAdapter = new FoodAdapter(foodBeanList, getActivity());
        foodListView.setAdapter(foodAdapter);

        return rootView;
    }

    // 根据分类的选择  滚动到指定位置
    public void scrollToPosition(int position) {
        foodListView.smoothScrollToPosition(position);
    }
}