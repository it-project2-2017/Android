package slu.com.pandora.adapter;

/**
 * Created by Pro Game on 3/8/2017.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.holder.RecyclerViewHolders;
import slu.com.pandora.model.ItemObject;

public class FinishedRecylerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<ItemObject> itemList;

    public FinishedRecylerViewAdapter(List<ItemObject> itemList){
        this.itemList = itemList;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType){
        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_adapter, parent, false);
        //set the view's size, margins, paddings and layout parameters
        RecyclerViewHolders vh = new RecyclerViewHolders(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position){
        holder.tablenum.setText(itemList.get(position).getTableNo());
        holder.prod_id.setText(itemList.get(position).getProdId());
        holder.prodName.setText(itemList.get(position).getName());
        holder.qty.setText(itemList.get(position).getQty());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }
}
