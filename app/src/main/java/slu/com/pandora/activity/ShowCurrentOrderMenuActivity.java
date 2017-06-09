package slu.com.pandora.activity;

/**
 * Created by matt on 6/4/2017.
 */

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
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


public class ShowCurrentOrderMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final static String queueStatus = "paid";
    private final static String currentStatus = "pending";

    List<ListOrder> queueOrder = new ArrayList<ListOrder>();
    List<ListOrder> currentOrder = new ArrayList<ListOrder>();

    private SwipeRefreshLayout refreshLayout;
    private ProgressBar pb;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_showorders);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.showOrdersRecycler);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        //ProgressBar
        pb = (ProgressBar) findViewById(R.id.progressBarForMenu);
        pb.setVisibility(ProgressBar.VISIBLE);

        //Refresh
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.showOrder_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callWebService();
                refreshLayout.setRefreshing(false);
            }
        });

        //Get the orders from the webservice
        callWebService();
        recyclerView.setHasFixedSize(true);
    }

    private void callWebService(){
        Call<Orders> call = apiService.getOrders(currentStatus);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                currentOrder = response.body().getOrderList().getListOrder();
                pb.setVisibility(ProgressBar.INVISIBLE);
                recyclerView.setAdapter(new QueueAndFinishedAdapter(currentOrder));
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(llm);
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
            }
        });
    }

}
