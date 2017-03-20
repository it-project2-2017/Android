package slu.com.pandora.adapter;

/**
 * Created by Pro Game on 3/7/2017.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.holder.RecyclerViewHolders;
import slu.com.pandora.model.ItemObject;

public class UseThisRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<ItemObject> itemList;
    ArrayAdapter arrayAdapter;

    //Provide a suitable constructor
    public UseThisRecyclerViewAdapter(List<ItemObject> itemList){
        this.itemList = itemList;
    }


    // Create new views invoked by the layout manager
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType){
        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_adapter, parent, false);
        arrayAdapter = new ArrayAdapter<ItemObject>(parent.getContext(),R.layout.sample_listview,itemList);
        //set the view's size, margins, paddings and layout parameters
        RecyclerViewHolders vh = new RecyclerViewHolders(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position){
        holder.tablenum.setText(itemList.get(position).getTableNo());
        holder.prodName.setText(itemList.get(position).getName());
        holder.qty.setText(itemList.get(position).getQty());
        //;holder.products.setAdapter(arrayAdapter.getItem(position));
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }



}
