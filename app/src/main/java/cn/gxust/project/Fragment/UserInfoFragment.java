package cn.gxust.project.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.gxust.project.Activity.MainActivity;
import cn.gxust.project.R;

public class UserInfoFragment extends Fragment {

    private TextView userInfoUserID, userInfoUserName, userInfoUserSex, userInfoUserBirthday, userInfoUserPhone;
    private Button userInfoLoginOut;

    // 用户数据
    private Long userID, userPhone;
    private String userName, userSex, userBirthday;


    public UserInfoFragment() {
    }

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);

        // 初始化UI控件
        initUI(rootView);

        // 获取存储的用户信息
        getUserInfo();

        // 设置用户信息更新UI
        updateUI();

        // 退出登录按钮点击事件
        userInfoLoginOut.setOnClickListener(v -> setUserInfoLoginOutOnClickListener());

        return rootView;
    }

    // 退出登录按钮点击事件
    private void setUserInfoLoginOutOnClickListener() {
        // 将登录状态修改成false
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLoginStatus", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isUserLoggedIn", false).apply();

        // 调用MainActivity的方法 切换显示的Fragment
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).switchToUserFragment();
        }
    }

    // 获取存储的用户信息
    private void getUserInfo() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        userID = sharedPreferences.getLong("userID", 1L);
        userName = sharedPreferences.getString("userName", "用户测试名");
        userSex = sharedPreferences.getString("userSex", "男");
        userBirthday = sharedPreferences.getString("userBirthday", "2001-01-01");
        userPhone = sharedPreferences.getLong("userPhone", 12345678901L);
    }

    // 初始化UI控件
    private void initUI(View rootView) {
        userInfoUserID = rootView.findViewById(R.id.userInfoUserID);
        userInfoUserName = rootView.findViewById(R.id.userInfoUserName);
        userInfoUserSex = rootView.findViewById(R.id.userInfoUserSex);
        userInfoUserBirthday = rootView.findViewById(R.id.userInfoUserBirthday);
        userInfoLoginOut = rootView.findViewById(R.id.userInfoLoginOut);
        userInfoUserPhone = rootView.findViewById(R.id.userInfoUserPhone);
    }

    private void updateUI() {
        userInfoUserID.setText(String.valueOf(userID));
        userInfoUserName.setText(userName);
        userInfoUserSex.setText(userSex);
        userInfoUserBirthday.setText(userBirthday);
        userInfoUserPhone.setText(String.valueOf(userPhone));
    }
}