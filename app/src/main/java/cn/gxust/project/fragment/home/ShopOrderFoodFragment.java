package cn.gxust.project.fragment.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.gxust.project.adapter.FoodAdapter;
import cn.gxust.project.bean.FoodBean;
import cn.gxust.project.R;


public class ShopOrderFoodFragment extends Fragment implements FoodAdapter.OnFoodAdapterListener {
    private ListView foodListView;
    private List<FoodBean> foodBeanList;
    private List<FoodBean> foodBeanListOnCart;
    private FoodAdapter foodAdapter;

    // 定义更新购物车监听器
    private OnUpdateCartListener listener;

    // 更新购物车监听器
    public interface OnUpdateCartListener {
        // 下面的方法是 谁监听 谁实现
        void onUpdateCart(List<FoodBean> foodBeanList);
    }

    // 设置更新购物车监听器
    public void setOnUpdateCartListener(OnUpdateCartListener listener) {
        this.listener = listener;
    }

    public ShopOrderFoodFragment() {
    }

    public static ShopOrderFoodFragment newInstance(List<FoodBean> foodBeanList) {
        ShopOrderFoodFragment shopOrderFoodFragment = new ShopOrderFoodFragment();
        Bundle args = new Bundle();
        args.putSerializable("foodBeanList", (ArrayList<FoodBean>) foodBeanList);
        shopOrderFoodFragment.setArguments(args);
        return shopOrderFoodFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 接收数据
        recvFoodBeanList();

        foodBeanListOnCart = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_order_food, container, false);

        // 初始化ListView
        foodListView = rootView.findViewById(R.id.foodListView);

        // 创建并设置Adapter 将当前Fragment作为点击监听器传入
        foodAdapter = new FoodAdapter(foodBeanList, getActivity(), this);
        foodListView.setAdapter(foodAdapter);

        return rootView;
    }

    // 更新菜品列表
    public void updateFoodBeanList(List<FoodBean> foodBeanList) {
        foodAdapter.updateFoodAdapterFoodBean(foodBeanList);
    }

    // 菜品项中 减少按钮 的点击事件
    @Override
    public void onFoodReduceClickListener(int position) {
        FoodBean foodBean = foodBeanList.get(position);
        if (foodBean.getNum() > 0) {
            foodBean.setNum(foodBean.getNum() - 1);
            updateFoodBeanListToCart(foodBean);     // 更新购物车列表
            foodAdapter.notifyDataSetChanged();     // 通知适配器进行数据更新
        }
    }

    // 菜品项中 增加按钮 的点击事件
    @Override
    public void onFoodAddClickListener(int position) {
        FoodBean foodBean = foodBeanList.get(position);
        if (foodBean.getNum() < 9) {
            foodBean.setNum(foodBean.getNum() + 1);
            updateFoodBeanListToCart(foodBean);     // 更新购物车列表
            foodAdapter.notifyDataSetChanged();     // 通知适配器进行数据更新
        }
    }

    // 更新要传到购物车中的数据
    private void updateFoodBeanListToCart(FoodBean foodBean) {
        // 使用迭代器
        boolean isUpdate = false;
        Iterator<FoodBean> foodBeanOnCart = foodBeanListOnCart.iterator();
        while (foodBeanOnCart.hasNext()) {
            FoodBean foodBeanOnCartItem = foodBeanOnCart.next();

            // 如果购物车中存在该菜品
            if (foodBeanOnCartItem.getName().equals(foodBean.getName())) {
                isUpdate = true;
                if (foodBean.getNum() > 0) {
                    // 数量不为0 则替换原有内容
                    foodBeanOnCart.remove();
                    foodBeanListOnCart.add(foodBean);
                } else {
                    // 数量为0 则移除
                    foodBeanOnCart.remove();
                }
                break;
            }
        }

        // 如果不存在该菜品 且数量大于0 则添加
        if (!isUpdate && foodBean.getNum() > 0) {
            foodBeanListOnCart.add(foodBean);
        }

        // 更新购物车列表
        if (listener != null) {
            listener.onUpdateCart(foodBeanListOnCart);
        }
    }

    // 接收shopOrderFragment传来的数据
    private void recvFoodBeanList() {
        // 接收数据
        if (getArguments() != null) {
            foodBeanList = (List<FoodBean>) getArguments().getSerializable("foodBeanList");
        }
        // 检查数据是否完整
        if (foodBeanList == null) {
            Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
        }
    }
}