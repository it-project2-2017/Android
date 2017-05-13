package slu.com.pandora.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.adapter.CurrentOrderAdapter;
import slu.com.pandora.adapter.QueueAndFinishedAdapter;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.Orders;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

/**
 * Created by Pro Game on 4/4/2017.
 */

public class ShowOrderMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final static String orderStatus ="unpaid";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_showorders);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.showOrdersRecycler);

        //ProgressBar
        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarForMenu);
        pb.setVisibility(ProgressBar.VISIBLE);

        //Get the orders from the webservice
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Orders> call = apiService.getOrders(orderStatus);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {

                List<ListOrder> listOrder = response.body().getOrderList().getListOrder();
                pb.setVisibility(ProgressBar.INVISIBLE);
                recyclerView.setAdapter(new QueueAndFinishedAdapter(listOrder));
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(llm);

            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                //Toast.makeText(Login.this, " Invalid username or password! ", Toast.LENGTH_LONG).show();
                Toast.makeText(ShowOrderMenuActivity.this,"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setHasFixedSize(true);
    }
}
