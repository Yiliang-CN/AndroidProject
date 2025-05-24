package cn.gxust.project.fragment.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.gxust.project.adapter.CmtAdapter;
import cn.gxust.project.bean.CmtBean;
import cn.gxust.project.R;


public class ShopCmtFragment extends Fragment {

    private ListView cmtListView;
    private List<CmtBean> cmtBeanList;
    private CmtAdapter cmtAdapter;

    // 模拟数据
    Long[] cmtID = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L};
    String[] cmtUserName = {"张三", "李四", "王五", "赵六", "孙七", "周八", "吴九", "郑十"};
    int[] cmtScore = {5, 4, 3, 2, 1, 5, 4, 3};
    String[] cmtTime = {"2018-01-01", "2018-01-02", "2018-01-03", "2018-01-04", "2018-01-05", "2018-01-06", "2018-01-07", "2018-01-08"};
    String[] cmtContent = {"这个店真不错", "这个店真不错", "这个店真不错", "这个店真不错", "这个店真不错", "这个店真不错", "这个店真不错", "这个店真不错"};

    public ShopCmtFragment() {
    }

    public static ShopCmtFragment newInstance() {
        return new ShopCmtFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cmtBeanList = new ArrayList<>();
        for (int i = 0; i < cmtID.length; i++) {
            CmtBean cmtBean = new CmtBean(cmtID[i], 0L, cmtUserName[i], 0, cmtScore[i], cmtTime[i], cmtContent[i], null);
            cmtBeanList.add(cmtBean);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载根View
        View rootView = inflater.inflate(R.layout.fragment_shop_cmt, container, false);

        // 初始化ListView
        cmtListView = rootView.findViewById(R.id.cmtListView);

        // 创建并设置Adapter
        cmtAdapter = new CmtAdapter(cmtBeanList, getContext());
        cmtListView.setAdapter(cmtAdapter);

        return rootView;
    }
}