package slu.com.pandora.fragment;

/**
 * Created by Pro Game on 3/8/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.adapter.UseThisRecyclerViewAdapter;
import slu.com.pandora.model.ItemObject;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.Orders;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

public class FinishedOrdersFragment extends Fragment {
    private final static String orderStatus = "finished";
    List<ListOrder> listOrders;
    public FinishedOrdersFragment() {

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

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        //List<ItemObject> rowListItem = getAllItemList();
        getUnpaidOrders();
        //UseThisRecyclerViewAdapter adapter = new UseThisRecyclerViewAdapter(listOrders);

        //rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

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

    private void getUnpaidOrders(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Orders> call = apiService.getOrders(orderStatus);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                listOrders = response.body().getOrderList().getListOrder();
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });
    }
}
