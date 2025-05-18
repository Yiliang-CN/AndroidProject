package cn.gxust.project.Fragment.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.gxust.project.Adapter.FoodNavAdapter;
import cn.gxust.project.R;

public class ShopOrderNavFragment extends Fragment {

    private ListView foodNavListView;
    private List<String> foodBeanTypeList;
    private FoodNavAdapter foodNavAdapter;

    public ShopOrderNavFragment() {
    }

    public static ShopOrderNavFragment newInstance(List<String> foodBeanTypeList) {
        ShopOrderNavFragment shopOrderNavFragment = new ShopOrderNavFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("foodBeanTypeList", (ArrayList<String>) foodBeanTypeList);
        shopOrderNavFragment.setArguments(args);
        return shopOrderNavFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 接收数据
        recvFoodBeanTypeList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_order_nav, container, false);

        // 初始化ListView
        foodNavListView = rootView.findViewById(R.id.foodNavListView);

        // 创建并设置Adapter
        foodNavAdapter = new FoodNavAdapter(foodBeanTypeList, getActivity());
        foodNavListView.setAdapter(foodNavAdapter);

        // 设置分类栏的点击事件
        foodNavListView.setOnItemClickListener((parent, view, position, id) -> setFoodNavListViewOnItemClickListener(position));

        return rootView;
    }

    private void setFoodNavListViewOnItemClickListener(int position) {
        String foodType = foodBeanTypeList.get(position);
        // 更新父级fragment的FoodBeanList
        if (getParentFragment() instanceof ShopOrderFragment) {
            ((ShopOrderFragment) getParentFragment()).updateFoodBeanList(foodType);
        }
    }

    // 接收shopOrderFragment传来的数据
    private void recvFoodBeanTypeList() {
        // 接收数据
        if (getArguments() != null) {
            foodBeanTypeList = getArguments().getStringArrayList("foodBeanTypeList");
        }
        // 检查数据是否完整
        if (foodBeanTypeList == null) {
            Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
        }
    }
}