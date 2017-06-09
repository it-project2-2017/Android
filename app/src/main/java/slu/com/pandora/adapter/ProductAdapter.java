package slu.com.pandora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.model.Product;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

/**
 * Created by vince on 2/15/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    //change url to your url.
    //String url = "http://192.168.1.19:28080/PanBox/img/";
    String url = "http://192.168.36.2:8010/PanBox/img/";
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
        final Product product = productRes.get(position);
        Picasso.with(getContext()).load(url + product.getId()).resize(150,150).into(holder.productImageIV);
        holder.productNameTV.setText(product.getName().toString());
        holder.productPriceTV.setText(product.getPrice().toString());

        if(productRes.get(position).getAvailable()){
            return view;
        } else {
            view.setAlpha((float) 0.4);
            return view;
        }
    }

    @Override
    public boolean areAllItemsEnabled(){
        return false;
    }

    @Override
    public boolean isEnabled(final int position){

        if (productRes.get(position).getAvailable()){
            return true;
        }else {
            return false;
        }
    }
    private static class ViewHolder{
        ImageView productImageIV;
        TextView productNameTV;
        TextView productPriceTV;
    }

}