package slu.com.pandora.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.holder.SaycoOrderHolder;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.ProdList;

/**
 * Created by matt on 3/23/2017.
 */

public class SaycoOrderAdapter extends RecyclerView.Adapter<SaycoOrderHolder> {

    List<ListOrder> listOrder;
    List<ProdList> prodLists;
    List<ProdList> prodRow = null;
    int ctrl = 0;
    int sizeOfList;
    Context context;

    RecyclerView recyclerView;

    public SaycoOrderAdapter(List<ListOrder> listOrder, Context context){
        this.listOrder = listOrder;
        this.context = context;
        sizeOfList = listOrder.size();
        sizeOfList--;
    }

    @Override
    public SaycoOrderHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_transaction_list_adapter,parent,false);
        SaycoOrderHolder syc = new SaycoOrderHolder(v);

        /*View list = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_transaction_orderlist_adapter,parent,false);

        recyclerView = (RecyclerView) list.findViewById(R.id.order_transaction_orderlist_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext()));*/



        return syc;
    }

    @Override
    public void onBindViewHolder(SaycoOrderHolder holder, int position){
        //prodLists = getRow();
        holder.prodName.setText(listOrder.get(position).getProdlist().get(0).getKey().toString());
        holder.quantity.setText(listOrder.get(position).getProdlist().get(0).getValue().toString());


        /*prodLists = listOrder.get(position).getProdlist();
        new SaycoPerItemAdapter(prodLists);
        recyclerView.setAdapter( new SaycoPerItemAdapter(prodLists));*/
    }

    @Override
    public int getItemCount(){
        return this.listOrder.size();
    }

    public List<ProdList> getRow(){
        if (ctrl<(sizeOfList)) {
            prodRow = listOrder.get(ctrl).getProdlist();
        }
        ctrl++;
        return prodRow;
    }

}