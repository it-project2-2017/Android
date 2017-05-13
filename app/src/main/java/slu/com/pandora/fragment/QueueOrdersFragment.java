package slu.com.pandora.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.adapter.QueueAndFinishedAdapter;
import slu.com.pandora.model.ItemObject;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.Orders;
import slu.com.pandora.model.ProdList;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

/**
 * Created by Pro Game on 3/8/2017.
 */

public class QueueOrdersFragment extends Fragment {
    private final static String queueStatus = "paid";
    private final static String currentStatus = "pending";

    List<ListOrder> queueOrder = new ArrayList<ListOrder>();
    List<ListOrder> currentOrder = new ArrayList<ListOrder>();

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rv;
    private View rootView;
    private ProgressBar pb;
    private ApiInterface apiService;

    public QueueOrdersFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        // Progress Bar
        pb = (ProgressBar) rootView.findViewById(R.id.progressBar);
        pb.setVisibility(ProgressBar.VISIBLE);

        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        //Refresh
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callWebService();
                refreshLayout.setRefreshing(false);
            }
        });

        callWebService();
        rv.setHasFixedSize(true);


        return rootView;
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
                        rv.setAdapter(new QueueAndFinishedAdapter(returnQueueOrder));
                        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                        rv.setLayoutManager(llm);
                    }

                    @Override
                    public void onFailure(Call<Orders> call, Throwable t) {
                        //Toast.makeText(Login.this, " Invalid username or password! ", Toast.LENGTH_LONG).show();
                        Toast.makeText(rootView.getContext(),"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                //Toast.makeText(Login.this, " Invalid username or password! ", Toast.LENGTH_LONG).show();
                Toast.makeText(rootView.getContext(),"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
            }
        });
    }


}
