package cn.gxust.project.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cn.gxust.project.Fragment.HomeFragment;
import cn.gxust.project.Fragment.OrderFragment;
import cn.gxust.project.Fragment.User.UserInfoFragment;
import cn.gxust.project.Fragment.UserFragment;
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

        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        userFragment = new UserFragment();
        userInfoFragment = new UserInfoFragment();


        // 设置默认页面
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
//            if (isUserLoggedIn()) {
//                fragmentTransaction.replace(R.id.mainBlankLayout, userInfoFragment);
//            } else {
//                fragmentTransaction.replace(R.id.mainBlankLayout, userFragment);
//            }

            fragmentTransaction.replace(R.id.mainBlankLayout, userFragment);
        }

        fragmentTransaction.commit();
    }

}