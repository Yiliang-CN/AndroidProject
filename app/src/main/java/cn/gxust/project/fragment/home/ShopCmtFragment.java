package cn.gxust.project.fragment.home;

import static android.content.Context.MODE_PRIVATE;

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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.gxust.project.Utils.OkHttpUtils;
import cn.gxust.project.adapter.CmtAdapter;
import cn.gxust.project.bean.CmtBean;
import cn.gxust.project.R;


public class ShopCmtFragment extends Fragment {

    private int shopId;

    private ListView cmtListView;
    private List<CmtBean> cmtBeans;
    private CmtAdapter cmtAdapter;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 100) {
                // 处理网络响应
                handlerResponse(msg.obj.toString());
            }
        }
    };


    public ShopCmtFragment() {
    }

    public static ShopCmtFragment newInstance() {
        return new ShopCmtFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getShopId();

        cmtBeans = new ArrayList<>();

        getHttpData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载根View
        View rootView = inflater.inflate(R.layout.fragment_shop_cmt, container, false);

        // 初始化ListView
        cmtListView = rootView.findViewById(R.id.cmtListView);

        // 创建并设置Adapter
        cmtAdapter = new CmtAdapter(cmtBeans, getContext());
        cmtListView.setAdapter(cmtAdapter);

        return rootView;
    }

    // 请求数据
    private void getHttpData() {

        String URL = getString(R.string.base_url_shops)+ "/" + shopId + getString(R.string.url_suffix_comments);
        OkHttpUtils.getInstance().doGet(URL, new OkHttpUtils.OkHttpCallback() {
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

    private void handlerResponse(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int code = jsonObject.getInt("code");
            if (code != 200) {
                String errorMsg = jsonObject.getString("message");
                Toast.makeText(requireContext(), "获取数据失败! code: " + code + "message: " + errorMsg, Toast.LENGTH_SHORT).show();
                return;
            }

            JSONArray dataArray = jsonObject.getJSONArray("data");
            cmtBeans.clear();
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject cmtObject = dataArray.getJSONObject(i);
                CmtBean cmtBean = new CmtBean();
                cmtBean.setUserName(cmtObject.getString("userName"));
                cmtBean.setScore(cmtObject.getDouble("score"));
                cmtBean.setTime(cmtObject.getString("time"));
                cmtBean.setContent(cmtObject.getString("content"));
                if (!cmtObject.isNull("image")) {
                    cmtBean.setImage(cmtObject.getString("image"));
                }
                cmtBeans.add(cmtBean);
            }
            cmtAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Toast.makeText(requireContext(), "数据解析失败", Toast.LENGTH_SHORT).show();
        }

    }

    // 获取商家ID
    private void getShopId() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shopInfo", MODE_PRIVATE);
        shopId = sharedPreferences.getInt("id", 0);
    }
}