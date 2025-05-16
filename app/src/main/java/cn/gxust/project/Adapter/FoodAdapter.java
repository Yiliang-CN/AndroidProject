package cn.gxust.project.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.gxust.project.Bean.FoodBean;
import cn.gxust.project.R;

public class FoodAdapter extends BaseAdapter {
    private List<FoodBean> foodBean;
    private Context context;
    private OnFoodAdapterListener listener;

    public FoodAdapter(List<FoodBean> foodBean, Context context, OnFoodAdapterListener listener) {
        this.foodBean = foodBean;
        this.context = context;
        this.listener = listener;
    }

    // 监听器
    public interface OnFoodAdapterListener {
        void onFoodReduceClick(int position);       // 减少按钮点击事件 在Fragment中实现

        void onFoodAddClick(int position);          // 增加按钮点击事件 在Fragment中实现
    }

    // 此方法用于更新菜品列表数据
    public void updateFoodAdapterFoodBean(List<FoodBean> foodBeanList) {
        this.foodBean.clear();
        this.foodBean.addAll(foodBeanList);
//        this.foodBean = foodBeanList;
        notifyDataSetChanged(); // 刷新ListView
    }

    @Override
    public int getCount() {
        return foodBean.size();
    }

    @Override
    public Object getItem(int position) {
        return foodBean.get(position);
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
            convertView = View.inflate(context, R.layout.item_food, null);

            holder.foodImage = convertView.findViewById(R.id.foodImage);
            holder.foodName = convertView.findViewById(R.id.foodName);
            holder.foodSales = convertView.findViewById(R.id.foodSales);
            holder.foodPrice = convertView.findViewById(R.id.foodPrice);
            holder.foodAdd = convertView.findViewById(R.id.foodAdd);
            holder.foodNum = convertView.findViewById(R.id.foodNum);
            holder.foodReduce = convertView.findViewById(R.id.foodReduce);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.foodImage.setImageResource(foodBean.get(position).getFoodImage());
        holder.foodName.setText(foodBean.get(position).getFoodName());
        holder.foodSales.setText(String.valueOf(foodBean.get(position).getFoodSales()));
        holder.foodPrice.setText(String.valueOf(foodBean.get(position).getFoodPrice()));
        holder.foodNum.setText(String.valueOf(foodBean.get(position).getFoodNum()));

        // 减少按钮点击事件
        holder.foodReduce.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFoodReduceClick(position);
            }
        });

        // 增加按钮点击事件
        holder.foodAdd.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFoodAddClick(position);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView foodImage;
        TextView foodName;
        TextView foodSales;
        TextView foodPrice;
        ImageView foodReduce;
        TextView foodNum;
        ImageView foodAdd;
    }
}
