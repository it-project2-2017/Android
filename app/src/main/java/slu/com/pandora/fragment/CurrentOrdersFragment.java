package slu.com.pandora.fragment;

/**
 * Created by Pro Game on 3/8/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.model.ItemObject;

public class CurrentOrdersFragment extends Fragment {
    public CurrentOrdersFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.current_order, container, false);

        /*RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);*/
        /*List<ItemObject> rowListItem = getAllItemList();
        UseThisRecyclerViewAdapter adapter = new UseThisRecyclerViewAdapter(rowListItem);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);*/

        return rootView;
    }


}
