package slu.com.pandora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.model.Product;
import slu.com.pandora.model.ProductResponse;

/**
 * Created by vince on 4/16/2017.
 */

public class FinishOrderAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> productOrder;

    public FinishOrderAdapter(Context context, int resource, ArrayList<Product> productOrder) {
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
            view = inflater.inflate(R.layout.popup_finish_order_row, parent, false);

            holder = new ViewHolder();

            holder.pendingProdName = (TextView) view.findViewById(R.id.pendingProdName);
            holder.pendingProdQty = (TextView) view.findViewById(R.id.pendingProdQty);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        Product order = productOrder.get(position);
        holder.pendingProdName.setText(order.getName());
        holder.pendingProdQty.setText(order.getQty() + "");

        return view;
    }

    private class ViewHolder{
        TextView pendingProdName;
        TextView pendingProdQty;
    }

}
