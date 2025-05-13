package cn.gxust.project.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.gxust.project.R;

public class FoodNavAdapter extends BaseAdapter {
    private List<String> foodBeanTypeList;
    private Context context;

    public FoodNavAdapter(List<String> foodBeanTypeList, Context context) {
        this.foodBeanTypeList = foodBeanTypeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return foodBeanTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodBeanTypeList.get(position);
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
            convertView = View.inflate(context, R.layout.item_foodnav, null);

            holder.foodNavText = convertView.findViewById(R.id.foodNavText);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.foodNavText.setText(foodBeanTypeList.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView foodNavText;
    }
}
