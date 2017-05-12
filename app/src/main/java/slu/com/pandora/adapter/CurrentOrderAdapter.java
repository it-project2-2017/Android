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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.holder.CurrentOrderHolder;
import slu.com.pandora.model.Employee;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.Product;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

/**
 * Created by Pro Game on 4/1/2017.
 */

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderHolder> {
    private List<ListOrder> listOrder;
    private List<Employee>  employeeList = null;
    private Set<Integer> headerPosition = new HashSet<>();
    private List<Integer> checkerList = new ArrayList<>();

    private int pos = 0;
    private final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private Spinner cookSpinner;
    private Spinner baristaSpinner;

    private static final int header = 1;
    private static final int list = 2;

    public CurrentOrderAdapter(List<ListOrder> listOrder, List<Employee> employeeList ){
        this.listOrder = listOrder;
        this.employeeList = employeeList;
    }

    private class HeaderHolder extends CurrentOrderHolder implements View.OnClickListener {

        private HeaderHolder (View view){
            super(view);

        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), "" + getAdapterPosition() , Toast.LENGTH_LONG).show();
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
                    pos = getAdapterPosition();
                    int posOfSelectedListOrder = 0;
                    ArrayList<Product> orders = new ArrayList<Product>();

                    if(checkbox.isChecked()){
                        checkerList.add(pos);
                        //sorting of position
                        List<Integer> listPostion = new ArrayList<>();
                        for(int eachInteger : headerPosition)
                        listPostion.add(eachInteger);
                        Collections.sort(listPostion);
                        listPostion.remove(listPostion.size()-1);

                        for(int ctrl = 0 ; ctrl < listPostion.size(); ctrl++){
                            if(pos > listPostion.get(ctrl)){
                                posOfSelectedListOrder = ctrl;
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



                        if (listOrder.get(posOfSelectedListOrder).getProdlist().size() == checker){
                            //Toast.makeText(view.getContext(), " Success boy" + orders, Toast.LENGTH_LONG).show();

                            LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View customView = inflater.inflate(R.layout.popup_finish_order, null);

                            //instantiate Spinner
                            cookSpinner = (Spinner)customView.findViewById(R.id.cookSpinner);
                            baristaSpinner = (Spinner)customView.findViewById(R.id.baristaSpinner);

                            for(int i = 0; i < listOrder.get(posOfSelectedListOrder).getProdlist().size(); i++){
                                Product product = new Product();
                                product.setName(listOrder.get(posOfSelectedListOrder).getProdlist().get(i).getKey());
                                product.setQty(listOrder.get(posOfSelectedListOrder).getProdlist().get(i).getValue());
                                orders.add(product);
                            }


                            final PopupWindow popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
                            popupWindow.setHeight(1200);
                            popupWindow.setWidth(900);
                            popupWindow.setOutsideTouchable(false);

                            ListView list = (ListView) customView.findViewById(R.id.popup_finish_lv);

                            //set List<String> for the names of the barista and the cook
                            List<String> cookList = new ArrayList<String>();
                            List<String> baristaList = new ArrayList<String>();

                            //Add content for List
                            for(Employee emp : employeeList) {
                                if (emp.getPosition().equalsIgnoreCase("cook")) {
                                    cookList.add(emp.getName());
                                } else if (emp.getPosition().equalsIgnoreCase("barista")) {
                                    baristaList.add(emp.getName());
                                }
                            }

                            final FinishOrderAdapter adapter = new FinishOrderAdapter(customView.getContext(), R.layout.popup_finish_order_row, orders);
                            list.setAdapter(adapter);

                            Button cancel = (Button)customView.findViewById(R.id.cancelBtn);

                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    checkbox.setChecked(false);
                                    int removePos = 0;
                                    for (int i = 0; i < checkerList.size(); i++){
                                        if(checkerList.get(i) == pos){
                                            removePos = i;
                                        }
                                    }
                                    checkerList.remove(removePos);
                                    popupWindow.dismiss();
                                }
                            });

                            Button done = (Button)customView.findViewById(R.id.doneBtn);
                            final int finalPosOfListOrder = posOfSelectedListOrder;
                            done.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int id = listOrder.get(finalPosOfListOrder).getId();
                                    /*String orderid = String.valueOf(listOrder.get(finalPosOfListOrder).getId());*/
                                    Call<String> call = apiService.finishStatus(id);
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            response.body().toString();
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            System.out.println("Error");
                                        }
                                    });
                                    //Toast.makeText(view.getContext(), ""+id, Toast.LENGTH_LONG).show();
                                    popupWindow.dismiss();
                                    String cookName = (String) cookSpinner.getSelectedItem();
                                    String baristaName = (String) baristaSpinner.getSelectedItem();
                                }
                            });


                            popupWindow.showAtLocation(customView.findViewById(R.id.popup_finish_lv), Gravity.CENTER, 0,0);
                        }


                        //Toast.makeText(view.getContext(), " "+checkerList.toString(), Toast.LENGTH_LONG).show();
                    }else{
                        int removePos = 0;
                        for (int i = 0; i < checkerList.size(); i++){
                            if(checkerList.get(i) == pos){
                                removePos = i;
                            }
                        }
                        checkerList.remove(removePos);
                        //Toast.makeText(view.getContext(), " "+checkerList.toString(), Toast.LENGTH_LONG).show();
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

    //remove recycler
    public void removeItem(int position){

    }
}
