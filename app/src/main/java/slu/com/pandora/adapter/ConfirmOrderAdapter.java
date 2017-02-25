package slu.com.pandora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.model.Product;

/**
 * Created by vince on 2/5/2017.
 */

public class ConfirmOrderAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> productOrder;

    public ConfirmOrderAdapter(Context context, int resource, List<Product> productOrder) {
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
            view = inflater.inflate(R.layout.confirm_order_list_view_row, parent, false);

            holder = new ViewHolder();

            holder.productNameTV = (TextView) view.findViewById(R.id.orderName);
            holder.productPriceTV = (TextView) view.findViewById(R.id.orderPrice);
            holder.productQtyTV = (TextView) view.findViewById(R.id.orderQty);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Product order = productOrder.get(position);
        holder.productNameTV.setText(order.getName());
        holder.productPriceTV.setText(order.getPrice() * order.getQty() + "");
        holder.productQtyTV.setText(order.getQty().toString());

        return view;
    }

    static class ViewHolder{
        TextView productNameTV;
        TextView productPriceTV;
        TextView productQtyTV;
    }

}
