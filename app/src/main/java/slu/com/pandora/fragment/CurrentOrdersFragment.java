package slu.com.pandora.fragment;

/**
 * Created by matt on 3/8/2017.
 */

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
import slu.com.pandora.adapter.CurrentOrderAdapter;
import slu.com.pandora.model.Employee;
import slu.com.pandora.model.EmployeeArrayList;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.Orders;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

public class CurrentOrdersFragment extends Fragment{
    private final static String orderStatus = "pending";
    private final static String queueStatus = "paid";

    private List<ListOrder> currentOrder = new ArrayList<>();
    private List<ListOrder> queueOrderList = new ArrayList<>();
    private List<Employee> employeeArrayLists = new ArrayList<>();


    final private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private View rootView;
    private ProgressBar pb;
    private RecyclerView rv;
    private SwipeRefreshLayout refreshLayout;

    private ArrayList<ListOrder> returnQueueOrder;

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
        rootView = inflater.inflate(R.layout.fragment_blank, container, false);



        // Progress Bar
        pb = (ProgressBar) rootView.findViewById(R.id.progressBar);
        pb.setVisibility(ProgressBar.VISIBLE);

        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);

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
        Call<Orders> call = apiService.getOrders(orderStatus);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {

                currentOrder = response.body().getOrderList().getListOrder();

                //Employees
                Call<EmployeeArrayList> callEmployee = apiService.getEmployees();
                callEmployee.enqueue(new Callback<EmployeeArrayList>() {
                    @Override
                    public void onResponse(Call<EmployeeArrayList> call, Response<EmployeeArrayList> response) {
                        employeeArrayLists = response.body().getEmployeeList().getEmployeeList();

                        updateCurrentOrder();
                        pb.setVisibility(ProgressBar.INVISIBLE);
                        rv.setAdapter(new CurrentOrderAdapter(currentOrder , employeeArrayLists));
                        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                        rv.setLayoutManager(llm);
                        rv.getLayoutManager().findContainingItemView(getView());
                    }

                    @Override
                    public void onFailure(Call<EmployeeArrayList> call, Throwable t) {
                        Toast.makeText(rootView.getContext(),"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
                    }
                });


            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                Toast.makeText(rootView.getContext(),"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateCurrentOrder(){
        Call<Orders> callQueueOrders = apiService.getOrders(queueStatus);
        callQueueOrders.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                queueOrderList = response.body().getOrderList().getListOrder();
                returnQueueOrder = new ArrayList<ListOrder>();
                //check if current orders has 4 items
                try{
                    for(int i = 0; i < queueOrderList.size(); i++){
                        if(currentOrder.size() < 4){
                            currentOrder.add(queueOrderList.get(i));
                        }
                    }

                    //change the status
                    for (int i = 0; i < currentOrder.size(); i++){
                        int id = currentOrder.get(i).getId();
                        Call<String> changeStatus = apiService.pendingStatus(id);
                        changeStatus.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                //response.body().toString();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                //response.body().toString();
                            }
                        });

                    }

                    //get the remaining queueorder
                    /*for (int i = 0; i < queueOrderList.size(); i++){

                        if (!currentOrder.contains(queueOrderList.get(i))){
                            returnQueueOrder.add(queueOrderList.get(i));
                        }
                    }*/

                }catch(NullPointerException e){
                    //Toast.makeText(rootView.getContext()," "+ ,Toast.LENGTH_LONG).show();
                    //Toast.makeText(rootView.getContext(),"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                Toast.makeText(rootView.getContext(),"Could Not Connect To The Server",Toast.LENGTH_LONG).show();
            }
        });
    }

}
