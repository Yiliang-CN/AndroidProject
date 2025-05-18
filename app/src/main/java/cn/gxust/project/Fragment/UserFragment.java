package cn.gxust.project.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.gxust.project.Activity.LoginActivity;
import cn.gxust.project.Activity.RegisterActivity;
import cn.gxust.project.R;

public class UserFragment extends Fragment {

    Button userLoginBtn, userRegisterBtn;

    public UserFragment() {
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        // 初始化UI控件
        initUI(rootView);

        // 设置登录按钮点击事件
        userLoginBtn.setOnClickListener(v -> setUserLoginBtnOnClickListener());

        // 设置注册按钮点击事件
        userRegisterBtn.setOnClickListener(v -> setUserRegisterBtnOnClickListener());

        return rootView;
    }

    // 登录按钮点击事件
    private void setUserLoginBtnOnClickListener() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    // 注册按钮点击事件
    private void setUserRegisterBtnOnClickListener() {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(intent);
    }

    // 初始化UI控件
    private void initUI(View rootView) {
        userLoginBtn = rootView.findViewById(R.id.userLoginBtn);
        userRegisterBtn = rootView.findViewById(R.id.userRegisterBtn);
    }
}