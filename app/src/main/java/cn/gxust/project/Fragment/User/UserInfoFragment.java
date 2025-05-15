package cn.gxust.project.Fragment.User;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.gxust.project.Activity.MainActivity;
import cn.gxust.project.R;

public class UserInfoFragment extends Fragment {

    private Button userInfoLoginOut;

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

        userInfoLoginOut = rootView.findViewById(R.id.userInfoLoginOut);
        userInfoLoginOut.setOnClickListener(v -> {
            // 记录登录状态
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLoginStatus", getActivity().MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("isUserLoggedIn", false).apply();

            // 调用MainActivity的方法 切换显示的Fragment
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).userLoginOutChangeFragment();
            }
        });

        return rootView;
    }
}