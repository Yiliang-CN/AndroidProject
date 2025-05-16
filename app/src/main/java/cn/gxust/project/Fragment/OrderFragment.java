package cn.gxust.project.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.gxust.project.Activity.OrderActivity;
import cn.gxust.project.Adapter.OrderAdapter;
import cn.gxust.project.Bean.OrderBean;
import cn.gxust.project.Bean.ShopBean;
import cn.gxust.project.R;

public class OrderFragment extends Fragment implements OrderAdapter.OnOrderAdapterListener {

    private ListView orderListView;
    private List<OrderBean> orderBeanList;
    private OrderAdapter orderAdapter;

    //    <!--店名 订单编号 订单内容 价格 时间 状态-->
    private int[] orderID = {1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010};
    private String[] orderShopName = {"小店1", "小店2", "小店3", "小店4", "小店5", "小店6", "小店7", "小店8", "小店9", "小店10"};
    private String[] orderContent = {"小炒肉 1份", "小炒肉 2份", "小炒肉 3份", "小炒肉 4份", "小炒肉 5份", "小炒肉 6份", "小炒肉 7份", "小炒肉 8份", "小炒肉 9份", "小炒肉 10份"};
    private double[] orderPrice = {10.00, 20.00, 30.00, 40.00, 50.00, 60.00, 70.00, 80.00, 90.00, 100.00};
    private String[] orderTime = {"2019-01-01 00:00", "2019-01-01 00:00", "2019-01-01 00:00", "2019-01-01 00:00", "2019-01-01 00:00", "2019-01-01 00:00", "2019-01-01 00:00", "2019-01-01 00:00", "2019-01-01 00:00", "2019-01-01 00:00"};
    private String[] orderState = {"已完成", "已完成", "已完成", "已完成", "已完成", "已完成", "已完成", "已完成", "已完成", "已完成"};


    public OrderFragment() {
    }

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderBeanList = new ArrayList<>();
        for (int i = 0; i < orderID.length; i++) {
            OrderBean orderBean = new OrderBean(orderID[i], 1, orderShopName[i], 1L, "用户1", orderContent[i],  orderPrice[i], orderTime[i], "上海", 12345678910L, orderState[i], "无", "无");
            orderBeanList.add(orderBean);
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
        orderAdapter = new OrderAdapter(orderBeanList, getContext(), this);
        orderListView.setAdapter(orderAdapter);

        return rootView;
    }

    // 订单项中点击店名即可打开订单详情页
    // 为什么要这样弄 因为订单项的布局中包含有 按钮 按钮会覆盖掉 项 本身的点击事件 所以只能单独选择店名的作为详细页面的入口
    @Override
    public void onOrderShopNameClick(OrderBean orderBean) {
        Intent intent = new Intent(getContext(), OrderActivity.class);
        intent.putExtra("orderBean", orderBean);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 设置启动标志，使得Activity在栈顶
        startActivity(intent);
    }
}