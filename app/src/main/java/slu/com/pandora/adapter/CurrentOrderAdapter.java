package slu.com.pandora.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import slu.com.pandora.R;
import slu.com.pandora.holder.CurrentOrderHolder;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.Product;

/**
 * Created by Pro Game on 4/1/2017.
 */

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderHolder> {
    private List<ListOrder> listOrder;
    private Set<Integer> headerPosition = new HashSet<>();
    private List<Integer> checkerList = new ArrayList<>();
    private ArrayList<Product> orders = new ArrayList<Product>();

    private static final int header = 1;
    private static final int list = 2;

    public CurrentOrderAdapter(List<ListOrder> listOrder){
        this.listOrder = listOrder;
    }

    private class HeaderHolder extends CurrentOrderHolder implements View.OnClickListener {

        private HeaderHolder (View view){
            super(view);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "" + getAdapterPosition() , Toast.LENGTH_LONG).show();
        }
    }

    private class ListHolder extends CurrentOrderHolder {
        private ListHolder (View view){
            super(view);
            final CheckBox checkbox = (CheckBox)view.findViewById(R.id.checkBox4);

            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int posOfOrders = 0;
                    int checker = 0;
                    int pos = getAdapterPosition();
                    int posSelectedListOrder = 0;
                    if(checkbox.isChecked()){

                        checkerList.add(pos);
                        List<Integer> listPostion = new ArrayList<>();
                        for(int eachInteger : headerPosition)
                        listPostion.add(eachInteger);
                        Collections.sort(listPostion);
                        listPostion.remove(listPostion.size()-1);
                        for(int ctrl = 0 ; ctrl < listPostion.size(); ctrl++){
                            if(pos > listPostion.get(ctrl)){
                                posSelectedListOrder = ctrl;

                                //gets header position
                                posOfOrders = listPostion.get(ctrl);
                                //checker for list position
                                checker = 0;
                                for(int i = 0; i < listOrder.get(ctrl).getProdlist().size(); i++ ){
                                    posOfOrders++;
                                    if(checkerList.contains(posOfOrders)){
                                        checker++;
                                    }
                                }
                            }

                        }



                        if (listOrder.get(posSelectedListOrder).getProdlist().size() == checker){
                            Toast.makeText(view.getContext(), " Success boy" + orders, Toast.LENGTH_LONG).show();
                            Product product = new Product();

                            LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View customView = inflater.inflate(R.layout.popup_finish_order, null);

                            for(int i = 0; i < listOrder.get(posSelectedListOrder).getProdlist().size(); i++){
                                Toast.makeText(view.getContext(), "" + listOrder.get(posSelectedListOrder).getProdlist().size(), Toast.LENGTH_LONG).show();

                                product.setName(listOrder.get(posSelectedListOrder).getProdlist().get(i).getKey());
                                product.setQty(listOrder.get(posSelectedListOrder).getProdlist().get(i).getValue());
                                orders.add(product);
                            }


                            final PopupWindow popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
                            popupWindow.setHeight(1200);
                            popupWindow.setWidth(900);
                            popupWindow.setOutsideTouchable(false);



                            ListView list = (ListView) customView.findViewById(R.id.popup_finish_lv);
                            final FinishOrderAdapter adapter = new FinishOrderAdapter(customView.getContext(), R.layout.popup_finish_order_row, orders);
                            list.setAdapter(adapter);

                            Button cancel = (Button)customView.findViewById(R.id.cancelBtn);

                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                }
                            });

                            Button done = (Button)customView.findViewById(R.id.doneBtn);
                            done.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //to do

                                }
                            });


                            popupWindow.showAtLocation(customView.findViewById(R.id.popup_finish_lv), Gravity.CENTER, 0,0);
                        }


                        Toast.makeText(view.getContext(), " "+checkerList.toString(), Toast.LENGTH_LONG).show();
                    }else{
                        int removePos = 0;
                        for (int i = 0; i < checkerList.size(); i++){
                            if(checkerList.get(i) == pos){
                                removePos = i;
                            }
                        }
                        checkerList.remove(removePos);
                        Toast.makeText(view.getContext(), " "+checkerList.toString(), Toast.LENGTH_LONG).show();
                    }



                }
            });
        }

    }



    private class CardHolder extends CurrentOrderHolder {
        private CardHolder (View view){
            super(view);
        }
    }

    @Override
    public CurrentOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case header:
                v = inflater.inflate(R.layout.current_header,parent,false);
                HeaderHolder headerHolder = new HeaderHolder(v);
                return headerHolder;

            case list:
                v = inflater.inflate(R.layout.current_list,parent,false);
                ListHolder listHolder = new ListHolder(v);
                return listHolder;

            default:
                v = inflater.inflate(R.layout.cardview_adapter,parent,false);
                CardHolder cardHolder = new CardHolder(v);
                return cardHolder;
        }
    }

    @Override
    public void onBindViewHolder( CurrentOrderHolder holder, int position) {
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
        for (int ctrl = 0; ctrl < listOrder.size();ctrl++){
            variable++;
            variable = variable + listOrder.get(ctrl).getProdlist().size();
            headerPosition.add(variable);
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
