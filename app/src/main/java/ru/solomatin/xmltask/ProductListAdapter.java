package ru.solomatin.xmltask;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.solomatin.xmltask.Model.Product;

/**
 * Created by altair on 26.07.2016.
 */
public class ProductListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Product> productItems;

    public ProductListAdapter(Activity activity, List<Product> productItems) {
        this.activity = activity;
        this.productItems = productItems;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.activdate)
        TextView activDate;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getCount() {
        return productItems.size();
    }

    @Override
    public Object getItem(int location) {
        return productItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.list_product_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Product p = productItems.get(position);
        holder.id.setText(p.getId());
        holder.name.setText(p.getName());
        holder.activDate.setText(p.getActivdatum());
        return convertView;
    }
}
