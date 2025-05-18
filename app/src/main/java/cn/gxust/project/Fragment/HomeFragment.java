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

import cn.gxust.project.Activity.ShopActivity;
import cn.gxust.project.Adapter.ShopAdapter;
import cn.gxust.project.Bean.ShopBean;
import cn.gxust.project.R;

public class HomeFragment extends Fragment {
    private ListView shopListView;
    private List<ShopBean> shopBeanList;
    private ShopAdapter shopAdapter;

    // 模拟数据
    private int[] shopID = {1, 2, 3, 4, 5, 6, 7, 8};
    private String[] shopName = {"商店一号店铺", "商店二号店铺", "商店三号店铺", "商店四号店铺", "商店五号店铺", "商店六号店铺", "商店七号店铺", "商店八号店铺"};
    private int[] shopSales = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000};
    private double[] shopPrice = {100.0, 200.0, 300.0, 400.0, 500.0, 600.0, 700.0, 800.0};
    private Long[] shopPhone = {1111L, 2222L, 3333L, 4444L, 5555L, 6666L, 7777L, 8888L};
    private String[] shopAddr = {"地址1", "地址2", "地址3", "地址4", "地址5", "地址6", "地址7", "地址8"};


    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shopBeanList = new ArrayList<>();
        for (int i = 0; i < shopID.length; i++) {
            ShopBean shopBean = new ShopBean(shopID[i], shopName[i], shopSales[i], shopPrice[i], shopPhone[i], shopAddr[i], "http://img.zcool.cn/community/013f5e594b0f0fa801206b0e0f0c7a2.jpg@1280w_1l_2o_100sh.jpg");
            shopBeanList.add(shopBean);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载根View
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // 初始化ListView
        shopListView = rootView.findViewById(R.id.shopListView);

        // 创建并设置Adapter
        shopAdapter = new ShopAdapter(shopBeanList, getContext());
        shopListView.setAdapter(shopAdapter);

        // 设置每项的点击事件
        shopListView.setOnItemClickListener((parent, view, position, id) -> setShopListViewOnItemClickListener(position));

        return rootView;
    }

    // 实现ListView中每个项的点击事件
    private void setShopListViewOnItemClickListener(int position) {
        ShopBean shopBean = shopBeanList.get(position);
        Intent intent = new Intent(getActivity(), ShopActivity.class);
        intent.putExtra("shopBean", shopBean);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 设置启动标志 复用现有实例 使ShopActivity在栈顶
        startActivity(intent);
    }
}