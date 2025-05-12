package cn.gxust.project.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.gxust.project.Bean.ShopBean;
import cn.gxust.project.R;

public class ShopAdapter extends BaseAdapter {

    private List<ShopBean> shopBean;
    private Context context;

    // 构造方法
    public ShopAdapter(List<ShopBean> shopBean, Context context) {
        this.shopBean = shopBean;
        this.context = context;
    }

    // 返回条目总数量
    @Override
    public int getCount() {
        return shopBean.size();
    }

    // 返回条目对象
    @Override
    public Object getItem(int position) {
        return shopBean.get(position);
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
            holder.shopSales = convertView.findViewById(R.id.shopSales);
            holder.shopPrice = convertView.findViewById(R.id.shopPrice);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.shopImage.setImageResource(shopBean.get(position).getShopImg());
        holder.shopName.setText(shopBean.get(position).getShopName());
        holder.shopSales.setText(shopBean.get(position).getShopSales());
        holder.shopPrice.setText(shopBean.get(position).getShopPrice());

        return convertView;
    }

    class ViewHolder {
        ImageView shopImage;    // 商店图片
        TextView shopName;      // 商店名称
        TextView shopSales;      // 商店销量
        TextView shopPrice;     // 商店价格
    }

}
