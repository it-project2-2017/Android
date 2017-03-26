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
import slu.com.pandora.model.ListOrder;

public class UseThisRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<ListOrder> listOrder;
    private List<ItemObject> rowOrder;

    //Provide a suitable constructor
    public UseThisRecyclerViewAdapter(List<ListOrder> listOrder){
        this.listOrder = listOrder;
        //this.rowOrder = rowOrder;
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
        /*holder.tablenum.setText("Table No. "+String.valueOf(listOrder.get(position).getTablenum()));
        holder.prodName.setText(listOrder.get(position).getProdlist().get(0).getKey().toString());
        holder.qty.setText(listOrder.get(position).getProdlist().get(0).getValue().toString());*/

        holder.tablenum.setText("Table No. "+String.valueOf(listOrder.get(position).getTablenum()));
        holder.prodName.setText("Product Name");
        holder.qty.setText("Quantity");

        /*holder.tablenum.setText(rowOrder.get(position).getTableNo());
        holder.prodName.setText(rowOrder.get(position).getName());
        holder.qty.setText(rowOrder.get(position).getQty());*/
        //;holder.products.setAdapter(arrayAdapter.getItem(position));
    }

    @Override
    public int getItemCount(){
        return  listOrder.size();
    }



}
