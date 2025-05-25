package cn.gxust.project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.gxust.project.bean.CmtBean;
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

        CmtBean currentCmtBean = cmtBean.get(position);
        holder.cmtUserName.setText(currentCmtBean.getUserName());
        holder.cmtScore.setText(String.valueOf(currentCmtBean.getScore()));
        holder.cmtTime.setText(currentCmtBean.getTime());
        holder.cmtContent.setText(currentCmtBean.getContent());
        if (!currentCmtBean.getImage().equals("null")) {
            Glide.with(context).load(context.getString(R.string.base_url_images) + "/" + currentCmtBean.getImage()).into(holder.cmtUserImage);
        }


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
