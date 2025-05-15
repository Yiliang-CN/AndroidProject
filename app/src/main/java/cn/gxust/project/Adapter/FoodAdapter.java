package cn.gxust.project.Adapter;

import android.content.Context;
import android.util.SparseIntArray;
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

    public FoodAdapter(List<FoodBean> foodBean, Context context) {
        this.foodBean = foodBean;
        this.context = context;
    }

    public void updateList(List<FoodBean> foodBean) {

        this.foodBean = foodBean;
        notifyDataSetChanged();
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
        holder.foodSales.setText(foodBean.get(position).getFoodSales());
        holder.foodPrice.setText(foodBean.get(position).getFoodPrice());

        holder.foodReduce.setOnClickListener(v -> {
            String numString = holder.foodNum.getText().toString();
            if (Integer.parseInt(numString) > 0) {
                numString = String.valueOf(Integer.parseInt(numString) - 1);
                holder.foodNum.setText(numString);
            }
        });

        holder.foodAdd.setOnClickListener(v -> {
            String numString = holder.foodNum.getText().toString();
            if (Integer.parseInt(numString) < 9) {
                numString = String.valueOf(Integer.parseInt(numString) + 1);
                holder.foodNum.setText(numString);
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
