package slu.com.pandora.activity;

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

/**
 * Created by Pro Game on 4/4/2017.
 */

public class ShowQueueOrderMenuActivity extends AppCompatActivity {
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
                Call<Orders> called = apiService.getOrders(queueStatus);
                called.enqueue(new Callback<Orders>() {
                    @Override
                    public void onResponse(Call<Orders> call, final Response<Orders> response) {
                        queueOrder = response.body().getOrderList().getListOrder();
                        ArrayList<ListOrder> returnQueueOrder = new ArrayList<ListOrder>();
                        //check if current orders has 4 items
                        for(int i = 0; i < queueOrder.size(); i++){
                            if(currentOrder.size() < 4){
                                currentOrder.add(queueOrder.get(i));
                            }
                        }
                        //change the status
                        for (int i = 0; i < currentOrder.size(); i++){
                            int id = currentOrder.get(i).getId();
                            Call<String> changeStatus = apiService.pendingStatus(id);
                            changeStatus.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    response.body().toString();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    response.body().toString();
                                }
                            });

                        }

                        //get the remaining queueorder
                        for (int i = 0; i < queueOrder.size(); i++){

                            if (!currentOrder.contains(queueOrder.get(i))){
                                returnQueueOrder.add(queueOrder.get(i));
                            }
                        }
                        pb.setVisibility(ProgressBar.INVISIBLE);
                        recyclerView.setAdapter(new QueueAndFinishedAdapter(returnQueueOrder));
                        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(llm);
                    }

                    @Override
                    public void onFailure(Call<Orders> call, Throwable t) {
                        //Toast.makeText(Login.this, " Invalid username or password! ", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                //Toast.makeText(Login.this, " Invalid username or password! ", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
            }
        });
    }
}
