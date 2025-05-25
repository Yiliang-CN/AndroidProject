package cn.gxust.project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.gxust.project.Utils.OkHttpUtils;
import cn.gxust.project.activity.ShopActivity;
import cn.gxust.project.adapter.ShopAdapter;
import cn.gxust.project.bean.ShopBean;
import cn.gxust.project.R;

public class HomeFragment extends Fragment {

    private ListView shopListView;
    private List<ShopBean> shopBeans;
    private ShopAdapter shopAdapter;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 100) {
                // 处理网络响应
                handleResponse((String) msg.obj);
            }
        }
    };

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shopBeans = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载根View
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // 初始化ListView
        shopListView = rootView.findViewById(R.id.shopListView);

        // 创建并设置Adapter
        shopAdapter = new ShopAdapter(shopBeans, getContext());
        shopListView.setAdapter(shopAdapter);

        // 设置每项的点击事件
        shopListView.setOnItemClickListener((parent, view, position, id) -> setShopListViewOnItemClickListener(position));

        // 请求数据
        getHttpData();

        return rootView;
    }

    // 请求数据
    private void getHttpData() {
        OkHttpUtils.getInstance().doGet(getString(R.string.base_url_shops), new OkHttpUtils.OkHttpCallback() {
            @Override
            public void onFailure(IOException e) {
                Toast.makeText(requireContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
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

    // 处理网络响应
    private void handleResponse(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int code = jsonObject.getInt("code");
            if (code != 200) {
                String errorMsg = jsonObject.getString("message");
                Toast.makeText(requireContext(), "获取数据失败! code: " + code + "message: " + errorMsg, Toast.LENGTH_SHORT).show();
                return;
            }

            // 获取数据数组
            JSONArray dataArray = jsonObject.getJSONArray("data");
            shopBeans.clear();

            // 遍历JSON数组 解析数据
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject shopObject = dataArray.getJSONObject(i);
                ShopBean shopBean = new ShopBean();
                shopBean.setId(shopObject.getInt("id"));
                shopBean.setName(shopObject.getString("name"));
                shopBean.setScore(shopObject.getDouble("score"));
                shopBean.setSales(shopObject.getInt("sales"));
                shopBean.setAddr(shopObject.getString("addr"));
                shopBean.setPhone(shopObject.getString("phone"));
                if (!shopObject.isNull("image")) {
                    shopBean.setImage(shopObject.getString("image"));
                }
                shopBeans.add(shopBean);
            }
            shopAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Toast.makeText(requireContext(), "数据解析失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 实现ListView中每个项的点击事件
    private void setShopListViewOnItemClickListener(int position) {
        ShopBean shopBean = shopBeans.get(position);
        Intent intent = new Intent(getActivity(), ShopActivity.class);
        intent.putExtra("shopBean", shopBean);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 设置启动标志 复用现有实例 使ShopActivity在栈顶
        startActivity(intent);
    }
}