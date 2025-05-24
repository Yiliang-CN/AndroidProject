package cn.gxust.project.fragment.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.gxust.project.bean.ShopBean;
import cn.gxust.project.R;

public class ShopInfoFragment extends Fragment {
    private ShopBean shopBean;
    private TextView shopInfoName, shopInfoPhone, shopInfoAddr;

    public ShopInfoFragment() {
    }

    public static ShopInfoFragment newInstance(ShopBean shopBean) {
        ShopInfoFragment shopInfoFragment = new ShopInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable("shopBean", shopBean);
        shopInfoFragment.setArguments(args);
        return shopInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 接收数据
        recvShopBean();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_info, container, false);

        // 初始化UI控件
        initUI(rootView);

        // 更新UI信息
        updateUI();

        return rootView;
    }

    // 接收ShopActivity中传递过来的店铺数据
    private void recvShopBean() {
        // 接收数据
        if (getArguments() != null) {
            shopBean = (ShopBean) getArguments().getSerializable("shopBean");
        }
        // 检查数据是否完整
        if (shopBean == null) {
            Toast.makeText(getActivity(), "获取店铺数据失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 初始化UI控件
    private void initUI(View rootView) {
        shopInfoName = rootView.findViewById(R.id.shopInfoName);
        shopInfoPhone = rootView.findViewById(R.id.shopInfoPhone);
        shopInfoAddr = rootView.findViewById(R.id.shopInfoAddr);
    }

    // 更新UI信息
    private void updateUI() {
        shopInfoName.setText(shopBean.getName());
        shopInfoPhone.setText(String.valueOf(shopBean.getPhone()));
        shopInfoAddr.setText(shopBean.getAddr());
    }
}