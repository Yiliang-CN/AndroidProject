package cn.gxust.project;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import cn.gxust.project.Fragment.HomeFragment;
import cn.gxust.project.Fragment.OrderFragment;
import cn.gxust.project.Fragment.UserFragment;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        userFragment = new UserFragment();

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
            fragmentTransaction.replace(R.id.mainBlankLayout, userFragment);
        }

        fragmentTransaction.commit();
    }

}