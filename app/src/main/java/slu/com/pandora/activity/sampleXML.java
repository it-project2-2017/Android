/**
 * Created by matt on 2/14/2017.
 */

package slu.com.pandora.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.adapter.SaycoOrderAdapter;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.Orders;
import slu.com.pandora.model.ProdList;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

public class sampleXML extends AppCompatActivity {
    private final static String orderStatus = "unpaid";
    TextView prodName;
    TextView quantity;
    RecyclerView recyclerView;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for testing connection
        /*setContentView(R.layout.order_transaction_list_adapter);
        prodName = (TextView) findViewById(R.id.sampleProdName);
        quantity = (TextView) findViewById(R.id.sampleQuantity);
        createConnection();*/

        //for testing view
        setContentView(R.layout.order_transaction_adapter);
        recyclerView = (RecyclerView) findViewById(R.id.order_transaction_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //context = this;
        //
        createSampleConnection();
    }

    private void createConnection(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Orders> call = apiService.getOrders(orderStatus);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                int statusCode = response.code();
               /* heirarchy
                Orders = .getOrderList()
                OrderList = .getList()
                ListOrders = .get(position) eto ung isang order transaction.
                ProdList = getProdlist();*/

                List<ProdList> prodList = response.body().getOrderList().getListOrder().get(0).getProdlist();
                //prodList.get(position).getKey() eto ung pagkuha nang isang order.
                prodName.setText(prodList.get(1).getKey().toString());
                quantity.setText(prodList.get(1).getValue().toString());

            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });
    }

    private void createSampleConnection(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Orders> call = apiService.getOrders(orderStatus);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                List<ListOrder> listOrders = response.body().getOrderList().getListOrder();
                recyclerView.setAdapter( new SaycoOrderAdapter(listOrders,context));
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });
    }


}
