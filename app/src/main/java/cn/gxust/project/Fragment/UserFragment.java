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

        userLoginBtn = rootView.findViewById(R.id.userLoginBtn);
        userLoginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        userRegisterBtn = rootView.findViewById(R.id.userRegisterBtn);
        userRegisterBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });

        return rootView;
    }
}