package cn.gxust.project.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.gxust.project.bean.ShopBean;
import cn.gxust.project.R;

public class ShopAdapter extends BaseAdapter {

    private List<ShopBean> shopBeans;
    private Context context;

    // 构造方法
    public ShopAdapter(List<ShopBean> shopBeans, Context context) {
        this.shopBeans = shopBeans;
        this.context = context;
    }

    // 返回条目总数量
    @Override
    public int getCount() {
        return shopBeans.size();
    }

    // 返回条目对象
    @Override
    public Object getItem(int position) {
        return shopBeans.get(position);
    }

    // 返回条目id
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 返回条目视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_shop, null);

            holder.shopImage = convertView.findViewById(R.id.shopImage);
            holder.shopName = convertView.findViewById(R.id.shopName);
            holder.shopScore = convertView.findViewById(R.id.shopScore);
            holder.shopSales = convertView.findViewById(R.id.shopSales);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShopBean currentShop = shopBeans.get(position);
        holder.shopName.setText(currentShop.getName());
        holder.shopScore.setText("评分: " + String.valueOf(currentShop.getScore()));
        holder.shopSales.setText("销量: " + String.valueOf(currentShop.getSales()));
        if (!currentShop.getImage().equals("null")) {
            Glide.with(context).load(context.getString(R.string.base_url_images) + currentShop.getImage()).into(holder.shopImage);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView shopImage;    // 商店图片
        TextView shopName;      // 商店名称
        TextView shopScore;     // 商店评分
        TextView shopSales;     // 商店销量
    }

}
