package cn.gxust.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import cn.gxust.project.Bean.ShopBean;
import cn.gxust.project.Fragment.Home.ShopCmtFragment;
import cn.gxust.project.Fragment.Home.ShopInfoFragment;
import cn.gxust.project.Fragment.Home.ShopOrderFragment;
import cn.gxust.project.R;

public class ShopActivity extends AppCompatActivity {

    private ShopOrderFragment shopOrderFragment;
    private ShopCmtFragment shopCmtFragment;
    private ShopInfoFragment shopInfoFragment;

    ShopBean shopBean;
    ImageView btnBack;
    TextView shopName, shopOrder, shopCmt, shopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop);

        shopOrderFragment = new ShopOrderFragment();
        shopCmtFragment = new ShopCmtFragment();
        shopInfoFragment = new ShopInfoFragment();

        btnBack = findViewById(R.id.btnBack);
        shopName = findViewById(R.id.shopName);
        shopOrder = findViewById(R.id.shopOrder);
        shopCmt = findViewById(R.id.shopCmt);
        shopInfo = findViewById(R.id.shopInfo);

        btnBack.setOnClickListener(view -> finish());   // 返回按钮 返回到首页

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("shopBean")) {
            shopBean = (ShopBean) intent.getSerializableExtra("shopBean");
            if (shopBean != null) {
                shopName.setText(shopBean.getShopName());  // 设置店名
            }
        }

        // 设置默认页面
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.shopBlankLayout, shopOrderFragment)
                    .commit();
        }
    }

    public void switch_shoppage(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable("shopBean", shopBean);

        if (view.getId() == R.id.shopOrder) {
            fragmentTransaction.replace(R.id.shopBlankLayout, shopOrderFragment);
        } else if (view.getId() == R.id.shopCmt) {
            fragmentTransaction.replace(R.id.shopBlankLayout, shopCmtFragment);
        } else if (view.getId() == R.id.shopInfo) {
            shopInfoFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.shopBlankLayout, shopInfoFragment);
        }

        fragmentTransaction.commit();
    }
}