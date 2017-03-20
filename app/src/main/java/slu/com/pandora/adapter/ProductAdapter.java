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
import slu.com.pandora.model.Product;
import slu.com.pandora.model.ProductResponse;

/**
 * Created by vince on 2/15/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    //change url to your url.
    String url = "http://192.168.1.5:8010/PanBox/img/";
    //String url = "http://172.20.10.5:28080/PanBox/img/";
    private Context context;
    private List<Product> productRes;

    public ProductAdapter(Context context, int resource, List<Product> productRes) {
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
            view = inflater.inflate(R.layout.product_list_grid_row, parent, false);

            holder = new ViewHolder();

            holder.productImageIV = (ImageView) view.findViewById(R.id.productIV);
            holder.productNameTV = (TextView) view.findViewById(R.id.productNameTV);
            holder.productPriceTV = (TextView) view.findViewById(R.id.productPriceTV);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        Product product = productRes.get(position);
        Picasso.with(getContext()).load(url).resize(150,150).into(holder.productImageIV);
        holder.productNameTV.setText(product.getName().toString());
        holder.productPriceTV.setText(product.getPrice().toString());

        return view;
    }

    static class ViewHolder{
        ImageView productImageIV;
        TextView productNameTV;
        TextView productPriceTV;
    }

}
