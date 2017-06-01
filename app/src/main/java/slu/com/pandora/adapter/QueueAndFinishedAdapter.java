package slu.com.pandora.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import slu.com.pandora.R;
import slu.com.pandora.holder.QueueAndFinishedHolder;
import slu.com.pandora.model.ListOrder;

/**
 * Created by Pro Game on 3/27/2017.
 */

public class QueueAndFinishedAdapter extends RecyclerView.Adapter<QueueAndFinishedHolder> {
    private List<ListOrder> listOrder;
    private Set<Integer> headerPosition = new HashSet<>();

    private static final int header = 1;
    private static final int list = 2;

    public QueueAndFinishedAdapter(List<ListOrder> listOrder){
        this.listOrder = listOrder;
    }

    private class HeaderHolder extends QueueAndFinishedHolder {
        private HeaderHolder (View view){
            super(view);
        }
    }

    private class ListHolder extends QueueAndFinishedHolder {
        private ListHolder (View view){
            super(view);
        }
    }

    private class CardHolder extends QueueAndFinishedHolder {
        private CardHolder (View view){
            super(view);
        }
    }

    @Override
    public QueueAndFinishedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case header:
                v = inflater.inflate(R.layout.queue_finished_header,parent,false);
                HeaderHolder headerHolder = new HeaderHolder(v);
                return headerHolder;
            case list:
                v = inflater.inflate(R.layout.queue_finished_list,parent,false);
                ListHolder listHolder = new ListHolder(v);
                return listHolder;
            default:
                v = inflater.inflate(R.layout.cardview_adapter,parent,false);
                CardHolder cardHolder = new CardHolder(v);
                return cardHolder;
        }
    }

    @Override
    public void onBindViewHolder(QueueAndFinishedHolder holder, int position) {
        try{
            if (holder instanceof HeaderHolder){
                HeaderHolder headerHolder = (HeaderHolder) holder;
                headerHolder.bindViewHeader(position,listOrder,headerPosition);
            }else if(holder instanceof  ListHolder){
                ListHolder listHolder = (ListHolder) holder;
                listHolder.bindViewList(position,listOrder,headerPosition);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int variable = 0;
        headerPosition.add(variable);
        try {
            for (int ctrl = 0; ctrl < listOrder.size();ctrl++){
                variable++;
                variable = variable + listOrder.get(ctrl).getProdlist().size();
                headerPosition.add(variable);
            }
        }catch (Exception e){

        }
        return variable;
    }

    @Override
    public int getItemViewType(int position){
        int viewType = list;

        //Set
        if(headerPosition.contains(position))
            viewType = header;

        return viewType;
    }
}
