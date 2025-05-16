package cn.gxust.project.Fragment.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cn.gxust.project.Adapter.FoodNavAdapter;
import cn.gxust.project.R;

public class ShopOrderNavFragment extends Fragment {

    private ListView foodNavListView;
    private List<String> foodBeanTypeList;
    private FoodNavAdapter foodNavAdapter;

    public ShopOrderNavFragment() {
    }

    public static ShopOrderNavFragment newInstance() {
        return new ShopOrderNavFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            foodBeanTypeList = getArguments().getStringArrayList("foodBeanTypeList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_order_nav, container, false);

        foodNavListView = rootView.findViewById(R.id.foodNavListView);

        foodNavAdapter = new FoodNavAdapter(foodBeanTypeList, getActivity());
        foodNavListView.setAdapter(foodNavAdapter);

        // 设置分类栏的点击事件
        foodNavListView.setOnItemClickListener((parent, view, position, id) -> {
            // 获取点击的分类
            String foodType = foodBeanTypeList.get(position);
            // 更新父级fragment的FoodBeanList
            if (getParentFragment() instanceof ShopOrderFragment) {
                ((ShopOrderFragment) getParentFragment()).updateFoodBeanList(foodType);
            }
        });

        return rootView;
    }
}