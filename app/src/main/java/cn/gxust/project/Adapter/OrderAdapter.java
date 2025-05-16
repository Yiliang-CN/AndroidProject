package cn.gxust.project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.gxust.project.Activity.OrderActivity;
import cn.gxust.project.Bean.OrderBean;
import cn.gxust.project.R;

public class OrderAdapter extends BaseAdapter {
    private List<OrderBean> orderBean;
    private Context context;
    private OnOrderAdapterListener listener;


    public OrderAdapter(List<OrderBean> orderBean, Context context, OnOrderAdapterListener listener) {
        this.orderBean = orderBean;
        this.context = context;
        this.listener = listener;
    }

    public interface OnOrderAdapterListener {
        void onOrderShopNameClick(OrderBean orderBean);
    }

    @Override
    public int getCount() {
        return orderBean.size();
    }

    @Override
    public Object getItem(int position) {
        return orderBean.get(position);
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
            convertView = View.inflate(context, R.layout.item_order, null);

            holder.orderShopName = convertView.findViewById(R.id.orderShopName);
            holder.orderID = convertView.findViewById(R.id.orderID);
            holder.orderContent = convertView.findViewById(R.id.orderContent);
            holder.orderPrice = convertView.findViewById(R.id.orderPrice);
            holder.orderTime = convertView.findViewById(R.id.orderTime);
            holder.orderState = convertView.findViewById(R.id.orderState);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.orderShopName.setText(orderBean.get(position).getOrderShopName());
        holder.orderID.setText(String.valueOf(orderBean.get(position).getOrderID()));
        holder.orderContent.setText(orderBean.get(position).getOrderContent());
        holder.orderPrice.setText(String.valueOf(orderBean.get(position).getOrderPrice()));
        holder.orderTime.setText(orderBean.get(position).getOrderTime());
        holder.orderState.setText(orderBean.get(position).getOrderState());

        holder.orderShopName.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOrderShopNameClick(orderBean.get(position));
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView orderShopName;         // 商店名称
        TextView orderID;               // 订单ID
        TextView orderContent;          // 订单内容
        TextView orderPrice;            // 订单价格
        TextView orderTime;             // 订单时间
        TextView orderState;           // 订单状态
    }
}
