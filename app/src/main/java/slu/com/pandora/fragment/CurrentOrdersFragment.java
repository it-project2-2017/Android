package slu.com.pandora.fragment;

/**
 * Created by matt on 3/8/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.adapter.CurrentOrderAdapter;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.Orders;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

public class CurrentOrdersFragment extends Fragment {
    private final static String orderStatus = "pending";
    public CurrentOrdersFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = this.getArguments();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        // Progress Bar
        final ProgressBar pb = (ProgressBar) rootView.findViewById(R.id.progressBar);
        pb.setVisibility(ProgressBar.VISIBLE);

        final RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Orders> call = apiService.getOrders(orderStatus);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {



                List<ListOrder> listOrder = response.body().getOrderList().getListOrder();
                pb.setVisibility(ProgressBar.INVISIBLE);
                rv.setAdapter(new CurrentOrderAdapter(listOrder));
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                rv.setLayoutManager(llm);

            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                //Toast.makeText(Login.this, " Invalid username or password! ", Toast.LENGTH_LONG).show();
                Toast.makeText(rootView.getContext(),"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
            }
        });
        rv.setHasFixedSize(true);


        return rootView;
    }




}
