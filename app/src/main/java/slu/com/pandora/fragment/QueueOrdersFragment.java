package slu.com.pandora.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.adapter.SaycoOrderAdapter;
import slu.com.pandora.adapter.UseThisRecyclerViewAdapter;
import slu.com.pandora.model.ItemObject;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.OrderResponse;
import slu.com.pandora.model.Orders;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

/**
 * Created by Pro Game on 3/8/2017.
 */

public class QueueOrdersFragment extends Fragment {
    private final static String orderStatus = "unpaid";
    private boolean status;
    private List<ListOrder> listOrders;
    private List<ItemObject> rowListItem;

    public QueueOrdersFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        final RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Orders> call = apiService.getOrders(orderStatus);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                List<ListOrder> sanaMakuhaNa = response.body().getOrderList().getListOrder();
                rv.setAdapter(new UseThisRecyclerViewAdapter(sanaMakuhaNa));
                rv.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                rv.setLayoutManager(llm);


            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });

        //getUnpaidOrders(rv);
        rv.setHasFixedSize(true);
        //use for sample
        //List<ItemObject> rowListItem = getAllItemList();
        //UseThisRecyclerViewAdapter adapter = new UseThisRecyclerViewAdapter(rowListItem);
        //getUnpaidOrders();

        /*UseThisRecyclerViewAdapter adapter = new UseThisRecyclerViewAdapter(listOrders);
        rv.setAdapter(adapter);*/

       /* if(getStatus()==true){
            UseThisRecyclerViewAdapter adapter = new UseThisRecyclerViewAdapter(listOrders);
            rv.setAdapter(adapter);
        }else if(getStatus()==false){
            List<ItemObject> rowListItem = getAllItemList();
            UseThisRecyclerViewAdapter adapter = new UseThisRecyclerViewAdapter(rowListItem);
            rv.setAdapter(adapter);
        }
        */



        /*LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;*/

        return rootView;
    }
    //Change according to the webservice
    private List<ItemObject> getAllItemList(){
        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("Table No.1","Product Name","Quantity"));
        allItems.add(new ItemObject("Table No.2","Product Name","Quantity"));
        allItems.add(new ItemObject("Table No.3","Product Name","Quantity"));
        allItems.add(new ItemObject("Table No.4","Product Name","Quantity"));

        return allItems;
    }

    public void getUnpaidOrders(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Orders> call = apiService.getOrders(orderStatus);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                //getUnpaid = response.body().getOrderList().getListOrder();
                List<ListOrder> listOrders = response.body().getOrderList().getListOrder();
                setListOrders(listOrders);
                setStatus(true);
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                setStatus(false);
            }
        });

    }

    //get the response from the service
    public void setListOrders(List<ListOrder> listOrders){
        this.listOrders = listOrders;
    }

    private void setStatus(boolean status){
        this.status = status;
    }

    private boolean getStatus(){
        return this.status;
    }
}
