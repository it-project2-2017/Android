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
import slu.com.pandora.adapter.UseThisRecyclerViewAdapter;
import slu.com.pandora.model.ItemObject;
import slu.com.pandora.model.OrderResponse;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

/**
 * Created by Pro Game on 3/8/2017.
 */

public class QueueOrdersFragment extends Fragment {
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

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        List<ItemObject> rowListItem = getAllItemList();
        UseThisRecyclerViewAdapter adapter = new UseThisRecyclerViewAdapter(rowListItem);
        rv.setAdapter(adapter);



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
}
