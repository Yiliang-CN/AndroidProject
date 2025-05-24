package cn.gxust.project.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import cn.gxust.project.fragment.HomeFragment;
import cn.gxust.project.fragment.OrderFragment;
import cn.gxust.project.fragment.UserInfoFragment;
import cn.gxust.project.fragment.UserFragment;
import cn.gxust.project.R;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private UserFragment userFragment;
    private UserInfoFragment userInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 设置默认显示页面
        if (savedInstanceState == null) {
            if (homeFragment == null) {
                homeFragment = HomeFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainBlankLayout, homeFragment)
                    .commit();
        }
    }

    // 设置页面切换
    public void switchMainPage(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // 点击图标后 先检查对应的Fragment是否存在 不存在则创建 存在则进行复用替换
        if (view.getId() == R.id.home) {
            if (homeFragment == null) {
                homeFragment = HomeFragment.newInstance();
            }
            fragmentTransaction.replace(R.id.mainBlankLayout, homeFragment);

        } else if (view.getId() == R.id.order) {
            if (orderFragment == null) {
                orderFragment = OrderFragment.newInstance();
            }
            fragmentTransaction.replace(R.id.mainBlankLayout, orderFragment);

        } else if (view.getId() == R.id.user) {
            // 判断用户是否已经登录
            if (isUserLoggedIn()) {
                if (userInfoFragment == null) {
                    userInfoFragment = UserInfoFragment.newInstance();
                }
                fragmentTransaction.replace(R.id.mainBlankLayout, userInfoFragment);

            } else {
                if (userFragment == null) {
                    userFragment = UserFragment.newInstance();
                }
                fragmentTransaction.replace(R.id.mainBlankLayout, userFragment);

            }
        }

        fragmentTransaction.commit();
    }

    // 判断用户是否已经登录
    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("userLoginStatus", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isUserLoggedIn", false);   // 默认为未登录
    }

    // 对外提供的公共方法 用于切换到初始的userFragment
    public void switchToUserFragment() {
        if (userFragment == null) {
            userFragment = UserFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainBlankLayout, userFragment)
                .commit();
    }
}