package slu.com.pandora.adapter;

/**
 * Created by Pro Game on 3/3/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.holder.RecyclerViewHolders;
import slu.com.pandora.model.ItemObject;

public class IndividualRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ItemObject> itemList;
    private Context context;

    public IndividualRecyclerViewAdapter(Context context, List<ItemObject> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType){

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_adapter, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
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
