package slu.com.pandora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.model.ProductResponse;

/**
 * Created by vince on 2/15/2017.
 */

public class ProductAdapter extends ArrayAdapter<ProductResponse> {

    String url = "http://10.0.3.2:8080/imagetest/img/";
    private Context context;
    private List<ProductResponse> productRes;

    public ProductAdapter(Context context, int resource, List<ProductResponse> productRes) {
        super(context, resource, productRes);
        this.context = context;
        this.productRes = productRes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.product_grid_row, parent, false);

            holder = new ViewHolder();

            //ProductResponse productResponse = productRes.get(position);
            holder.productImageIV = (ImageView) view.findViewById(R.id.productIV);
            holder.productNameTV = (TextView) view.findViewById(R.id.productNameTV);
            //holder.productDescTV = (TextView) view.findViewById(R.id.productDescTV);
            holder.productPriceTV = (TextView) view.findViewById(R.id.productPriceTV);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        ProductResponse productResponse = productRes.get(position);
        Picasso.with(getContext()).load(url).resize(100,100).into(holder.productImageIV);
        holder.productNameTV.setText(productResponse.getProduct().getName());
        //holder.productDescTV.setText(productResponse.getProduct().getDescription());
        holder.productPriceTV.setText(productResponse.getProduct().getPrice().toString());


        return view;
    }

    static class ViewHolder{
        ImageView productImageIV;
        TextView productNameTV;
        //TextView productDescTV;
        TextView productPriceTV;

    }


}
