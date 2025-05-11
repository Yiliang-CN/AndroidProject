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

    private HomeFragment homefragment;
    private OrderFragment orderfragment;
    private UserFragment userfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        homefragment = new HomeFragment();
        orderfragment = new OrderFragment();
        userfragment = new UserFragment();

        // 设置默认页面
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.topLayout, homefragment)
                    .commit();
        }
    }

    public void switch_page(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (view.getId() == R.id.home) {
            fragmentTransaction.replace(R.id.topLayout, homefragment);
        } else if (view.getId() == R.id.order) {
            fragmentTransaction.replace(R.id.topLayout, orderfragment);
        } else if (view.getId() == R.id.user) {
            fragmentTransaction.replace(R.id.topLayout, userfragment);
        }

        fragmentTransaction.commit();
    }

}