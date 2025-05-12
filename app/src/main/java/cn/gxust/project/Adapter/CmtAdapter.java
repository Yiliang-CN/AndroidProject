package cn.gxust.project.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cn.gxust.project.Bean.CmtBean;
import cn.gxust.project.R;

public class CmtAdapter extends BaseAdapter {
    private List<CmtBean> cmtBean;
    private Context context;

    public CmtAdapter(List<CmtBean> cmtBean, Context context) {
        this.cmtBean = cmtBean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cmtBean.size();
    }

    @Override
    public Object getItem(int position) {
        return cmtBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_cmt, null);

            holder.cmtUserImage = convertView.findViewById(R.id.cmtUserImage);
            holder.cmtUserName = convertView.findViewById(R.id.cmtUserName);
            holder.cmtScore = convertView.findViewById(R.id.cmtScore);
            holder.cmtTime = convertView.findViewById(R.id.cmtTime);
            holder.cmtContent = convertView.findViewById(R.id.cmtContent);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.cmtUserImage.setImageResource(cmtBean.get(position).getCmtUserImage());
        holder.cmtUserName.setText(cmtBean.get(position).getCmtUserName());
        holder.cmtScore.setText(cmtBean.get(position).getCmtScore());
        holder.cmtTime.setText(cmtBean.get(position).getCmtTime());
        holder.cmtContent.setText(cmtBean.get(position).getCmtContent());

        return convertView;
    }

    class ViewHolder {
        ImageView cmtUserImage;
        TextView cmtUserName;
        TextView cmtScore;
        TextView cmtTime;
        TextView cmtContent;
    }
}
