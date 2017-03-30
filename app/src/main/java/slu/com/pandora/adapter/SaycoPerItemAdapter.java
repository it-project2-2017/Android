package slu.com.pandora.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.holder.SaycoOrderHolder;
import slu.com.pandora.model.ProdList;

/**
 * Created by Pro Game on 3/24/2017.
 */

public class SaycoPerItemAdapter extends RecyclerView.Adapter<SaycoOrderHolder> {
    private List<ProdList> prodLists;

    public SaycoPerItemAdapter (List<ProdList> prodLists){
        this.prodLists = prodLists;
    }

    @Override
    public SaycoOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_transaction_list_adapter,parent,false);
        SaycoOrderHolder syc = new SaycoOrderHolder(v);

        return syc;
    }

    @Override
    public void onBindViewHolder(SaycoOrderHolder holder, int position) {
        holder.prodName.setText(prodLists.get(position).getKey().toString());
        holder.quantity.setText(prodLists.get(position).getValue().toString());
    }

    @Override
    public int getItemCount() {
        return prodLists.size();
    }
}
