package cn.gxust.project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import cn.gxust.project.bean.ShopBean;
import cn.gxust.project.fragment.home.ShopCmtFragment;
import cn.gxust.project.fragment.home.ShopInfoFragment;
import cn.gxust.project.fragment.home.ShopOrderFragment;
import cn.gxust.project.R;

public class ShopActivity extends AppCompatActivity {

    private ShopOrderFragment shopOrderFragment;
    private ShopCmtFragment shopCmtFragment;
    private ShopInfoFragment shopInfoFragment;

    private ShopBean shopBean;
    private ImageView shopBtnBack;
    private TextView shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop);

        // 初始化UI控件
        initUI();

        // 设置默认页面
        if (savedInstanceState == null) {
            if (shopOrderFragment == null) {
                shopOrderFragment = ShopOrderFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.shopBlankLayout, shopOrderFragment)
                    .commit();
        }

        // 接收HomeFragment传递过来的店铺数据
        recvShopBean();

        // 更新UI信息
        updateUI();

        // 将店铺数据写入本地存储
        recordShopInfo();

        // 返回按钮的点击事件
        shopBtnBack.setOnClickListener(view -> finish());
    }

    // 接收HomeFragment传递过来的店铺数据
    private void recvShopBean() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("shopBean")) {
            shopBean = (ShopBean) intent.getSerializableExtra("shopBean");
        }
        // 检测数据是否存在 不存在则退出
        if (shopBean == null) {
            Toast.makeText(this, "获取店铺数据失败", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // 将店铺信息写入本地存储
    private void recordShopInfo() {
        if (shopBean != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("shopInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id", shopBean.getId())
                    .putString("name", shopBean.getName())
                    .putString("score",String.valueOf(shopBean.getScore()))
                    .putInt("sales", shopBean.getSales())
                    .putString("addr", shopBean.getPhone())
                    .putString("phone", shopBean.getAddr())
                    .apply();
        }
    }

    // 设置页面切换
    public void switchShopPage(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (view.getId() == R.id.shopOrder) {
            if (shopOrderFragment == null) {
                shopOrderFragment = ShopOrderFragment.newInstance();
            }
            fragmentTransaction.replace(R.id.shopBlankLayout, shopOrderFragment);

        } else if (view.getId() == R.id.shopCmt) {
            if (shopCmtFragment == null) {
                shopCmtFragment = ShopCmtFragment.newInstance();
            }
            fragmentTransaction.replace(R.id.shopBlankLayout, shopCmtFragment);

        } else if (view.getId() == R.id.shopInfo) {
            if (shopInfoFragment == null) {
                // 创建店铺信息页面 并将店铺信息shopBean传入
                shopInfoFragment = ShopInfoFragment.newInstance(shopBean);
            }
            fragmentTransaction.replace(R.id.shopBlankLayout, shopInfoFragment);
        }

        fragmentTransaction.commit();
    }

    // 初始化UI控件
    private void initUI() {
        shopBtnBack = findViewById(R.id.shopBtnBack);
        shopName = findViewById(R.id.shopName);
    }

    private void updateUI() {
        if (shopBean != null) {
            shopName.setText(shopBean.getName());
        }
    }
}