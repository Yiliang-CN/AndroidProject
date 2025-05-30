package cn.gxust.project.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.gxust.project.Utils.DialogUtils;
import cn.gxust.project.Utils.OkHttpUtils;
import cn.gxust.project.activity.MainActivity;
import cn.gxust.project.R;

public class UserInfoFragment extends Fragment {

    private ImageView userInfoImage;
    private TextView userInfoUserId, userInfoUserName, userInfoUserGender, userInfoUserBirthday, userInfoUserPhone;
    private Button userInfoSave, userInfoLoginOut;

    // 用户数据
    private int userId;
    private String userName, userGender, userBirthday, userPhone, userImage;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 100) {
                // 处理网络响应
                handelResponse((String) msg.obj);
            }
        }
    };


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

        // 保存按钮点击事件
        userInfoSave.setOnClickListener(v -> setUserInfoSaveOnClickListener());

        // 退出登录按钮点击事件
        userInfoLoginOut.setOnClickListener(v -> setUserInfoLoginOutOnClickListener());

        return rootView;
    }

    private void setUserInfoSaveOnClickListener() {
        if (isUserInfoChange()) {
            postHttpData();
        } else {
            DialogUtils.showConfirmDialog("提示", "您未修改任何用户信息，请先修改信息后再点击保存!", "确认", requireContext(), null);
        }
    }

    private void postHttpData() {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("name", userInfoUserName.getText());
            jsonParam.put("gender", userInfoUserGender.getText());
            jsonParam.put("birthday", userInfoUserBirthday.getText());
            jsonParam.put("image", userImage);
        } catch (JSONException e) {
            Toast.makeText(requireContext(), "构建请求参数失败", Toast.LENGTH_SHORT);
            return;
        }

        OkHttpUtils.getInstance().doPost(getString(R.string.base_url_users) + "/" + userId, jsonParam.toString(), new OkHttpUtils.OkHttpCallback() {
            @Override
            public void onFailure(IOException e) {
                DialogUtils.showConfirmDialog("错误", "更新信息失败!请检查输入的内容是否有误!", "确认", requireContext(), null);
            }

            @Override
            public void onResponse(String body) {
                Message message = new Message();
                message.what = 100;
                message.obj = body;
                handler.sendMessage(message);
            }
        });
    }

    private void handelResponse(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int code = jsonObject.getInt("code");
            if (code != 200) {
                String errorMsg = jsonObject.getString("message");
                DialogUtils.showConfirmDialog("错误", "更新信息失败! code: " + code + "message: " + errorMsg, "确认", requireContext(), null);
                return;
            }
            updateUserInfo();
            DialogUtils.showConfirmDialog("提示", "数据更新成功!", "确认", requireContext(), null);
        } catch (JSONException e) {
            Toast.makeText(requireContext(), "解析数据失败", Toast.LENGTH_SHORT).show();
        }
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
        userId = sharedPreferences.getInt("id", 0);
        userName = sharedPreferences.getString("name", "null");
        userGender = sharedPreferences.getString("gender", "保密");
        userBirthday = sharedPreferences.getString("birthday", "null");
        userPhone = sharedPreferences.getString("phone", "null");
        userImage = sharedPreferences.getString("image", "null");
    }

    // 判断用户信息是否有修改
    private boolean isUserInfoChange() {
        return !userInfoUserName.getText().equals(userName) &&
                !userInfoUserGender.getText().equals(userGender) &&
                !userInfoUserBirthday.getText().equals(userBirthday);
    }

    // 初始化UI控件
    private void initUI(View rootView) {
        userInfoImage = rootView.findViewById(R.id.userInfoImage);
        userInfoUserId = rootView.findViewById(R.id.userInfoUserId);
        userInfoUserName = rootView.findViewById(R.id.userInfoUserName);
        userInfoUserGender = rootView.findViewById(R.id.userInfoUserGender);
        userInfoUserBirthday = rootView.findViewById(R.id.userInfoUserBirthday);
        userInfoUserPhone = rootView.findViewById(R.id.userInfoUserPhone);
        userInfoSave = rootView.findViewById(R.id.userInfoSave);
        userInfoLoginOut = rootView.findViewById(R.id.userInfoLoginOut);
    }

    private void updateUI() {
        userInfoUserId.setText(String.valueOf(userId));
        userInfoUserName.setText(userName);
        userInfoUserGender.setText(userGender);
        userInfoUserBirthday.setText(userBirthday);
        userInfoUserPhone.setText(String.valueOf(userPhone));
        if (!userImage.equals("null")) {
            Glide.with(requireContext()).load(getString(R.string.base_url_images) + userImage).into(userInfoImage);
        }
    }

    private void updateUserInfo() {
        userName = userInfoUserName.getText().toString();
        userGender = userInfoUserGender.getText().toString();
        userBirthday = userInfoUserBirthday.getText().toString();
    }
}