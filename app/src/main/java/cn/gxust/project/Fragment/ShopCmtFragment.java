package cn.gxust.project.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.gxust.project.Adapter.CmtAdapter;
import cn.gxust.project.Bean.CmtBean;
import cn.gxust.project.R;


public class ShopCmtFragment extends Fragment {

    private ListView cmtListView;
    private List<CmtBean> cmtBeanList;
    private CmtAdapter cmtAdapter;

    // 模拟数据
    int[] cmtID = {1, 2, 3, 4, 5, 6, 7, 8};
    String[] cmtUserName = {"张三", "李四", "王五", "赵六", "孙七", "周八", "吴九", "郑十"};
    String[] cmtScore = {"5", "4", "3", "2", "1", "5", "4", "3"};
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
        cmtAdapter = new CmtAdapter(cmtBeanList, getContext());

        for (int i = 0; i < cmtID.length; i++) {
            CmtBean cmtBean = new CmtBean(cmtID[i], null, cmtUserName[i], null, cmtScore[i], cmtTime[i], cmtContent[i], null);
            cmtBeanList.add(cmtBean);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_cmt, container, false);

        cmtListView = rootView.findViewById(R.id.cmtListView);

        cmtAdapter = new CmtAdapter(cmtBeanList, getContext());
        cmtListView.setAdapter(cmtAdapter);

        return rootView;
    }
}