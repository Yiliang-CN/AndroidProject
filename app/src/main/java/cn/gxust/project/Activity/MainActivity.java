package cn.gxust.project.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import cn.gxust.project.Fragment.HomeFragment;
import cn.gxust.project.Fragment.OrderFragment;
import cn.gxust.project.Fragment.User.UserInfoFragment;
import cn.gxust.project.Fragment.UserFragment;
import cn.gxust.project.R;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment = new HomeFragment();
    private OrderFragment orderFragment = new OrderFragment();
    private UserFragment userFragment = new UserFragment();
    private UserInfoFragment userInfoFragment = new UserInfoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 设置默认显示页面为首页
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainBlankLayout, homeFragment)
                    .commit();
        }
    }

    public void switch_mainpage(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (view.getId() == R.id.home) {
            fragmentTransaction.replace(R.id.mainBlankLayout, homeFragment);
        } else if (view.getId() == R.id.order) {
            fragmentTransaction.replace(R.id.mainBlankLayout, orderFragment);
        } else if (view.getId() == R.id.user) {
            // 判断用户是否登录
            if (isUserLoggedIn()) {
                fragmentTransaction.replace(R.id.mainBlankLayout, userInfoFragment);
            } else {
                fragmentTransaction.replace(R.id.mainBlankLayout, userFragment);
            }
        }

        fragmentTransaction.commit();
    }

    // 判断用户是否登录
    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("userLoginStatus", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isUserLoggedIn", false);   // 默认为未登录
    }

    // 用户退出登录后，切换到用户登录注册页面
    public void userLoginOutChangeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainBlankLayout, userFragment)
                .commit();
    }

}