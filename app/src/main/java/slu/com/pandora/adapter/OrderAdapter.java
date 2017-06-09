package slu.com.pandora.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.activity.MainActivity;
import slu.com.pandora.activity.OrderActivity;
import slu.com.pandora.model.Category;
import slu.com.pandora.model.Product;
import slu.com.pandora.model.ProductResponse;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

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
            holder.addBtn = (Button)view.findViewById(R.id.addBtn);
            holder.orderList = (ListView)view.findViewById(R.id.orderListLV);

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

                if (productOrder.get(position).getQty() == 1) {
                    decReservedProd(productOrder.get(position).getName());
                    productOrder.remove(position);
                    notifyDataSetChanged();
                } else if (productOrder.get(position).getQty() > 0){
                    order.setQty(order.getQty() - 1);
                    decReservedProd(productOrder.get(position).getName());
                    notifyDataSetChanged();
                } else if (productOrder.isEmpty()){
                    notifyDataSetChanged();
                }

            }
        });

        holder.addBtn.setTag(position);
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allProds();

                for (int i = 0; i < comProd.size(); i++){
                    if (productOrder.get(position).getId() == comProd.get(i).getId()){
                        productOrder.get(position).setAvailable(comProd.get(i).getAvailable());
                    }
                }

                if (productOrder.get(position).getAvailable()){
                    order.setQty(order.getQty() + 1);
                    reserveProduct(order.getId());
                    notifyDataSetChanged();
                    comProd.clear();
                } else {
                    Toast.makeText(view.getContext(), "Not Available" , Toast.LENGTH_LONG).show();
                    comProd.clear();
                }

            }
        });

        Animation animation = null;
        animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        animation.setDuration(400);
        view.startAnimation(animation);

        return view;
    }

    Category category = new Category();
    List<Product> comProd = new ArrayList<Product>();

    public void allProds(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductResponse> call = apiInterface.getAllProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                List<Product> products = response.body().getProductList().getList();

                for (Product prod : products) {
                    if (!comProd.contains(prod)){
                        comProd.add(prod);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Server Connection Lost", Toast.LENGTH_LONG).show();
            }
        });

    }

    private class ViewHolder{
        TextView productNameTV;
        TextView productPriceTV;
        TextView productQtyTV;
        Button deleteBtn;
        Button addBtn;
        ListView orderList;
    }

    public void decReservedProd(String name){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiInterface.decReservation(name);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    //Category category = new Category();
                    ((OrderActivity)context).getOrder("food");
                    //Toast.makeText(getContext(), "Decreased 1 order qty", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Failed to decrease qty", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(),"Server Connection Lost", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void reserveProduct(final int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiInterface.reserveProduct(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    ((OrderActivity)context).getOrder("food");
                    //Toast.makeText(getContext(), "Order Reserved" + id, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Failed to reserve order", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage() + "Server Connection Lost", Toast.LENGTH_LONG).show();
            }
        });
    }

}
