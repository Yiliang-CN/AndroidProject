package cn.gxust.project.Fragment.User;

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
    private String userName, userPassword, userSex, userBirthday;


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

        userInfoUserID = rootView.findViewById(R.id.userInfoUserID);
        userInfoUserName = rootView.findViewById(R.id.userInfoUserName);
        userInfoUserSex = rootView.findViewById(R.id.userInfoUserSex);
        userInfoUserBirthday = rootView.findViewById(R.id.userInfoUserBirthday);
        userInfoLoginOut = rootView.findViewById(R.id.userInfoLoginOut);
        userInfoUserPhone = rootView.findViewById(R.id.userInfoUserPhone);

        // 获取存储的用户信息
        getUserInfo();
        // 设置用户信息更新UI
        setUserInfo();

        // 退出登录按钮点击事件
        userInfoLoginOut.setOnClickListener(v -> {
            setUserInfoLoginOutOnClickListener();
        });

        return rootView;
    }

    // 退出登录按钮点击事件
    private void setUserInfoLoginOutOnClickListener() {
        // 记录登录状态
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLoginStatus", getActivity().MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isUserLoggedIn", false).apply();

        // 调用MainActivity的方法 切换显示的Fragment
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).userLoginOutChangeFragment();
        }
    }

    // 获取存储的用户信息
    private void getUserInfo() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        this.userID = sharedPreferences.getLong("userID", 1L);
        this.userName = sharedPreferences.getString("userName", "用户测试名");
        this.userPassword = sharedPreferences.getString("userPassword", "000000");
        this.userSex = sharedPreferences.getString("userSex", "男");
        this.userBirthday = sharedPreferences.getString("userBirthday", "2001-01-01");
        this.userPhone = sharedPreferences.getLong("userPhone", 12345678901L);
    }

    private void setUserInfo() {
        userInfoUserID.setText(String.valueOf(this.userID));
        userInfoUserName.setText(this.userName);
        userInfoUserSex.setText(this.userSex);
        userInfoUserBirthday.setText(this.userBirthday);
        userInfoUserPhone.setText(String.valueOf(this.userPhone));
    }
}