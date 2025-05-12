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
    int[] shopId = {1, 2, 3, 4, 5, 6, 7, 8};
    String[] shopName = {"商店一号店铺", "商店二号店铺", "商店三号店铺", "商店四号店铺", "商店五号店铺", "商店六号店铺", "商店七号店铺", "商店八号店铺"};
    String[] shopSales = {"1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000"};
    String[] shopPrice = {"100", "200", "300", "400", "500", "600", "700", "800"};
    String[] shopPhone = {"1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888"};
    String[] shopAddr = {"地址1", "地址2", "地址3", "地址4", "地址5", "地址6", "地址7", "地址8"};


    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shopBeanList = new ArrayList<>();
        shopAdapter = new ShopAdapter(shopBeanList, getContext());

        for (int i = 0; i < shopId.length; i++) {
            ShopBean shopBean = new ShopBean(shopId[i], shopName[i], shopSales[i], shopPrice[i], shopPhone[i], shopAddr[i], "http://img.zcool.cn/community/013f5e594b0f0fa801206b0e0f0c7a2.jpg@1280w_1l_2o_100sh.jpg");
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
        shopListView.setOnItemClickListener((parent, view, position, id) -> {
            ShopBean shopBean = shopBeanList.get(position);
            Intent intent = new Intent(getActivity(), ShopActivity.class);
            intent.putExtra("shopBean", shopBean);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 设置启动标志，使得Activity在栈顶
            startActivity(intent);
        });

        return rootView;
    }
}