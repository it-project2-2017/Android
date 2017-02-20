package slu.com.pandora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.model.Product;

/**
 * Created by vince on 2/5/2017.
 */

public class OrderAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> productOrder;

    public OrderAdapter(Context context, int resource, List<Product> productOrder) {
        super(context, resource, productOrder);
        this.context = context;
        this.productOrder = productOrder;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_list_view_row, parent, false);

            holder = new ViewHolder();

            holder.productNameTV = (TextView) view.findViewById(R.id.orderName);
            holder.productPriceTV = (TextView) view.findViewById(R.id.orderPrice);
            holder.productQtyTV = (TextView) view.findViewById(R.id.orderQty);
            holder.deleteBtn = (Button)view.findViewById(R.id.deleteBtn);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Product order = productOrder.get(position);
        holder.productNameTV.setText(order.getName());
        holder.productPriceTV.setText(order.getPrice() * order.getQty() + "");
        holder.productQtyTV.setText(order.getQty().toString());

        holder.deleteBtn.setTag(position);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productOrder.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    static class ViewHolder{
        TextView productNameTV;
        TextView productPriceTV;
        TextView productQtyTV;
        Button deleteBtn;

    }

}
