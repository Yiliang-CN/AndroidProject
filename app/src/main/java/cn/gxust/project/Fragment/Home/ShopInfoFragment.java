package cn.gxust.project.Fragment.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.gxust.project.Bean.ShopBean;
import cn.gxust.project.R;

public class ShopInfoFragment extends Fragment {

    ShopBean shopBean;
    TextView shopInfoName, shopInfoPhone, shopInfoAddr;

    public ShopInfoFragment() {
    }

    public static ShopInfoFragment newInstance() {
        return new ShopInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            shopBean = (ShopBean) getArguments().getSerializable("shopBean");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_info, container, false);

        shopInfoName = rootView.findViewById(R.id.shopInfoName);
        shopInfoPhone = rootView.findViewById(R.id.shopInfoPhone);
        shopInfoAddr = rootView.findViewById(R.id.shopInfoAddr);

        shopInfoName.setText(shopBean.getShopName());
        shopInfoPhone.setText(shopBean.getShopPhone());
        shopInfoAddr.setText(shopBean.getShopAddr());

        return rootView;
    }
}