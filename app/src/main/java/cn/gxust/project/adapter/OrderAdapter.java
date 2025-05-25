package cn.gxust.project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.gxust.project.bean.OrderBean;
import cn.gxust.project.R;

public class OrderAdapter extends BaseAdapter {
    private List<OrderBean> orderBeans;
    private Context context;
    private OnOrderAdapterListener listener;

    public OrderAdapter(List<OrderBean> orderBeans, Context context, OnOrderAdapterListener listener) {
        this.orderBeans = orderBeans;
        this.context = context;
        this.listener = listener;
    }

    public interface OnOrderAdapterListener {
        void setOrderShopNameOnClickListener(OrderBean orderBean);

        void setOrderCancelBtnOnClickListener(OrderBean orderBean);

        void setOrderDeleteBtnOnClickListener(OrderBean orderBean);
    }

    @Override
    public int getCount() {
        return orderBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return orderBeans.get(position);
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
            holder.orderID = convertView.findViewById(R.id.orderId);
            holder.orderContent = convertView.findViewById(R.id.orderContent);
            holder.orderPrice = convertView.findViewById(R.id.orderPrice);
            holder.orderTime = convertView.findViewById(R.id.orderTime);
            holder.orderState = convertView.findViewById(R.id.orderState);
            holder.orderCancelBtn = convertView.findViewById(R.id.orderCancelBtn);
            holder.orderDeleteBtn = convertView.findViewById(R.id.orderDeleteBtn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OrderBean currentOrderBean = orderBeans.get(position);
        holder.orderShopName.setText(currentOrderBean.getShopName());
        holder.orderID.setText(String.valueOf(currentOrderBean.getId()));
        holder.orderContent.setText(currentOrderBean.getContent());
        holder.orderPrice.setText(String.valueOf(currentOrderBean.getPrice()));
        holder.orderTime.setText(currentOrderBean.getTime());
        holder.orderState.setText(currentOrderBean.getState());

        // 设置点击事件
        holder.orderShopName.setOnClickListener(v -> {
            if (listener != null) {
                listener.setOrderShopNameOnClickListener(currentOrderBean);
            }
        });


        // 删除订单
        holder.orderDeleteBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.setOrderDeleteBtnOnClickListener(currentOrderBean);
            }
        });

        if ("已取消".equals(currentOrderBean.getState())) {
            holder.orderCancelBtn.setVisibility(View.GONE);
        } else {
            holder.orderCancelBtn.setOnClickListener(v -> {
                if (listener != null) {
                    listener.setOrderCancelBtnOnClickListener(currentOrderBean);
                }
            });
        }

        return convertView;
    }

    class ViewHolder {
        TextView orderShopName;         // 商店名称
        TextView orderID;               // 订单ID
        TextView orderContent;          // 订单内容
        TextView orderPrice;            // 订单价格
        TextView orderTime;             // 订单时间
        TextView orderState;            // 订单状态
        Button orderCancelBtn;          // 取消订单
        Button orderDeleteBtn;          // 删除订单
    }
}
