package slu.com.pandora.adapter;

/**
 * Created by Pro Game on 3/7/2017.
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

public class UseThisRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<ItemObject> itemList;

    //Provide a reference to the views for each data item
    //Complex data items may need more than one view per item
    //provide access to all the views for a data item in a view holder

    /*public static class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView textView;
        public TextView tablenum;
        public TextView prod_id;
        public TextView prodName;
        public TextView qty;
        public MyViewHolder(View v){
            super(v);

            *//*cardView = (CardView) v.findViewById(R.id.card_view);
            textView = (TextView) v.findViewById(R.id.tv_text);*//*
            tablenum = (TextView)itemView.findViewById(R.id.table_no);
            prod_id = (TextView)itemView.findViewById(R.id.ID);
            prodName = (TextView)itemView.findViewById(R.id.product_name);
            qty = (TextView)itemView.findViewById(R.id.quantity);

        }
    }*/

    //Provide a suitable constructor
    public UseThisRecyclerViewAdapter(List<ItemObject> itemList){
        this.itemList = itemList;
    }


    // Create new views invoked by the layout manager
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
