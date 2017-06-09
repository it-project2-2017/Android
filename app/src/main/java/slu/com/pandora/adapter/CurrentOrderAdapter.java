package slu.com.pandora.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.holder.CurrentOrderHolder;
import slu.com.pandora.model.Employee;
import slu.com.pandora.model.ListOrder;
import slu.com.pandora.model.Orders;
import slu.com.pandora.model.Product;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

/**
 * Created by Pro Game on 4/1/2017.
 */

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderHolder> {
    private List<ListOrder> listOrder;
    private List<Employee>  employeeList = null;
    //contains the position of all headers
    private Set<Integer> headerPosition = new HashSet<>();
    private List<Integer> checkerList = new ArrayList<>();
    //Employees  spinner
    private List<String> cookList = new ArrayList<String>();
    private List<String> baristaList = new ArrayList<String>();

    private int pos = 0;

    //used for checking items
    private int checker;
    private int posOfOrders;
    private int posOfSelectedHeader;
    private int posOfSelectedListOrder;

    //used for getting the position of last header
    private int lastHeader;

    //used for checking items
    private List<Integer> listPostion;
    private static String queueStatus = "paid";
    private final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    //For combobox
    private Spinner cookSpinner;
    private Spinner baristaSpinner;

    //For checking the number of new orders
    private int numNewOrders;


    private static final int header = 1;
    private static final int list = 2;


    public CurrentOrderAdapter(List<ListOrder> listOrder, List<Employee> employeeList ){
        this.listOrder = listOrder;
        this.employeeList = employeeList;

        for(Employee emp : employeeList) {
            if (emp.getPosition().equalsIgnoreCase("cook")) {
                cookList.add(emp.getName());
            } else if (emp.getPosition().equalsIgnoreCase("barista")) {
                baristaList.add(emp.getName());
            }
        }

    }

    private class HeaderHolder extends CurrentOrderHolder {

        private HeaderHolder (View view){
            super(view);

        }

    }

    private class ListHolder extends CurrentOrderHolder {
        private ListHolder (View view){
            super(view);
            final CheckBox checkbox = (CheckBox)view.findViewById(R.id.checkBox4);

            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posOfOrders = 0;
                    checker = 0;
                    pos = getAdapterPosition();
                    posOfSelectedListOrder = 0;
                    ArrayList<Product> orders = new ArrayList<Product>();

                    if(checkbox.isChecked()){

                        //checkerList gets all the pos of checkedbox
                        checkerList.add(pos);
                        //sorting of position
                        listPostion = new ArrayList<>();
                        for(int eachInteger : headerPosition)
                        listPostion.add(eachInteger);
                        Collections.sort(listPostion);
                        listPostion.remove(listPostion.size()-1);

                        //get the header postion
                        for(int ctrl = 0 ; ctrl < listPostion.size(); ctrl++){
                            if(pos > listPostion.get(ctrl)){
                                posOfSelectedListOrder = ctrl;
                                //posOfOrders header position
                                posOfOrders = listPostion.get(ctrl);
                                posOfSelectedHeader = listPostion.get(ctrl);
                                //checker for list position
                                checker = 0;
                                //getting the body position or order position
                                for(int i = 0; i < listOrder.get(ctrl).getProdlist().size(); i++ ){
                                    posOfOrders++;
                                    //checks if the order is checked.
                                    if(checkerList.contains(posOfOrders)){
                                        checker++;
                                    }
                                }
                            }

                        }


                        //checks if the selected order is equal to the number of checked box
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


                            //Adding elements to Spinner
                            final ArrayAdapter<String> spinnerArrayAdapterCook = new ArrayAdapter<String>(customView.getContext(),R.layout.spinner_item, cookList);
                            final ArrayAdapter<String> spinnerArrayAdapterBarista = new ArrayAdapter<String>(customView.getContext(),R.layout.spinner_item, baristaList);
                            spinnerArrayAdapterCook.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerArrayAdapterBarista.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cookSpinner.setAdapter(spinnerArrayAdapterCook);
                            baristaSpinner.setAdapter(spinnerArrayAdapterBarista);


                            final FinishOrderAdapter adapter = new FinishOrderAdapter(customView.getContext(), R.layout.popup_finish_order_row, orders);
                            list.setAdapter(adapter);

                            Button cancle = (Button)customView.findViewById(R.id.cancelBtn);
                            //clicked cancle
                            cancle.setOnClickListener(new View.OnClickListener() {
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
                            //clicked done
                            done.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View view) {
                                    /*String cookName = (String) cookSpinner.getSelectedItem();
                                    String baristaName = (String) baristaSpinner.getSelectedItem();*/
                                    int posCook = cookSpinner.getSelectedItemPosition();
                                    int posBarista = baristaSpinner.getSelectedItemPosition();

                                    //get ID
                                    int orderId = listOrder.get(finalPosOfListOrder).getId();
                                    int baristaId = employeeList.get(posBarista).getId();
                                    int cookId = employeeList.get(posCook).getId();

                                    //remove orders
                                    checkerList = removeSelectedOrderInArray(posOfSelectedHeader, checker, checkerList);
                                    headerPosition = removeHeaderPosInArray(posOfSelectedHeader,checker, headerPosition);
                                    removeDoneItem(posOfSelectedHeader,checker,posOfSelectedListOrder,view);

                                    //update orders
                                    if(listOrder.size() < 4){
                                        updateOrders(view);
                                        //headerPosition = updateHeaderPosInArray(headerPosition, listOrder);
                                    }





                                    //changeStatus to finish
                                    Call<String> call = apiService.finishStatus(orderId,baristaId,cookId);
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            System.out.println("Error");
                                        }
                                    });

                                    popupWindow.dismiss();


                                }
                            });
                            popupWindow.showAtLocation(customView.findViewById(R.id.popup_finish_lv), Gravity.CENTER, 0,0);
                        }
                        // /Toast.makeText(view.getContext(), " "+checkerList.toString(), Toast.LENGTH_LONG).show();
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

            holder.checkBox.setOnCheckedChangeListener(null);
            holder.checkBox.setChecked(checkerList.contains(position));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int variable = 0;
        headerPosition.add(variable);
        try{
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

    //remove header recycler
    private void removeDoneItem(int headerPos, int numOfOrders,int posOfOrderInArray, View view){
        listOrder.remove(posOfOrderInArray);
        int orderPos = headerPos + numOfOrders;
        notifyItemRangeRemoved(headerPos,orderPos);
        notifyDataSetChanged();
    }

    //clean headerPos
    private Set<Integer> removeHeaderPosInArray(int headerPos,int numOfOrders, Set<Integer> headerPosList){
        List<String> removeStringList = new ArrayList<>();
        List<String> updateStringList = new ArrayList<>();
        Set<Integer> cleanHeaderPosSet = new HashSet<>();
        int numOfElement = numOfOrders+1;

        int highest = 0;

        for(int elem : headerPosList)
            if(elem > highest)
                highest = elem;
        //lastHeader = highest;
        headerPosList.remove(highest);

        //store values to string list
        for(int eachInt : headerPosList)
            removeStringList.add(String.valueOf(eachInt));

        //remove the selected header
        removeStringList.remove(String.valueOf(headerPos));

        //update all headers
        for(int ctrl = 0; ctrl < removeStringList.size(); ctrl++){
            int eachPosElement = Integer.parseInt(removeStringList.get(ctrl));
            //if(eachPosElement >= numOfElement){
            if(eachPosElement >= headerPos){
                eachPosElement = eachPosElement - numOfElement;
                updateStringList.add(String.valueOf(eachPosElement));
            }else{
                updateStringList.add(String.valueOf(eachPosElement));
            }
        }



        //store in set
        for(String eachString : updateStringList)
            cleanHeaderPosSet.add(Integer.parseInt(eachString));

        highest = 0;
        for(int elem : cleanHeaderPosSet)
            if(elem > highest)
                highest = elem;
        lastHeader = highest;

        return cleanHeaderPosSet;
    }

    //clean checkedArray
    private List<Integer> removeSelectedOrderInArray(int headerPos, int numOfOrders,List<Integer> checkerList ){
        int posOfLastOrder = headerPos+numOfOrders;
        int numOfElements = numOfOrders+1;
        List <Integer> cleanCheckerList = new ArrayList<>();
        List<String> removeStringList = new ArrayList<>();
        List<String> updateStringList = new ArrayList<>();

        for(int eachInt : checkerList)
            removeStringList.add(String.valueOf(eachInt));

        //remove the position of marked done orders
        for(int ctrl = headerPos; ctrl <= posOfLastOrder; ctrl++){
            String num = String.valueOf(ctrl);//(char)ctrl;
            if(removeStringList.contains(num))
                removeStringList.remove(num);
        }

        //update all the position
        for(int ctrl = 0; ctrl < removeStringList.size(); ctrl++){
            int eachPosElement = Integer.parseInt(removeStringList.get(ctrl));
            if(eachPosElement > posOfLastOrder){
                eachPosElement = eachPosElement - numOfElements;
                updateStringList.add(String.valueOf(eachPosElement));
            }else{
                updateStringList.add(String.valueOf(eachPosElement));
            }
        }

        //updating the position of marked items
        for(String eachString : updateStringList)
            cleanCheckerList.add(Integer.parseInt(eachString));

        return cleanCheckerList;
    }

    //update orders
    private void updateOrders (final View view){
        Call<Orders> call = apiService.getOrders(queueStatus);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                List<ListOrder> queueOrderList = response.body().getOrderList().getListOrder();
                numNewOrders = 0;
                try{
                    for(int i = 0; i < queueOrderList.size(); i++){
                        if(listOrder.size() < 4){
                            numNewOrders++;
                            listOrder.add(queueOrderList.get(i));
                        }
                    }

                    //change the status
                    for (int i = 0; i < listOrder.size(); i++){
                        int id = listOrder.get(i).getId();
                        Call<String> changeStatus = apiService.pendingStatus(id);
                        changeStatus.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                headerPosition = updateHeaderPosInArray(headerPosition, listOrder, numNewOrders);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                //response.body().toString();
                            }
                        });

                    }



                }catch(NullPointerException e){

                }
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                Toast.makeText(view.getContext(), "Could Not Connect To The Server", Toast.LENGTH_LONG).show();
            }
        });
    }

    //update headerPos
    private Set<Integer> updateHeaderPosInArray(Set<Integer> headerPos, List<ListOrder> listOfOrders, int numNewOrders){
        List<Integer> sortHeaderPos = new ArrayList<>();
        int lastElement = 0;
        int lastPos = 0;
        int sizeOfElem = 0;
        int numOfElement = 0;

        //sortheader
        for(int elem : headerPos)
            sortHeaderPos.add(elem);
        Collections.sort(sortHeaderPos);
        sizeOfElem = sortHeaderPos.size();

        //get the element
        for (int ctrl =  0; ctrl < sortHeaderPos.size(); ctrl ++){
            if (lastHeader == sortHeaderPos.get(ctrl))
                lastElement = ctrl;
        }


        try{
            lastPos = lastHeader+(listOfOrders.get(lastElement).getProdlist().size());
            sortHeaderPos.add(lastPos+1);
            int posOfHeader = lastPos+1;
            for(int ctrl = sizeOfElem; ctrl < 4; ctrl++){
                numOfElement = numOfElement + (listOfOrders.get(ctrl-1).getProdlist().size()) + 1;
                posOfHeader = posOfHeader + numOfElement + 1;
            }




            //notifyItemRangeInserted(lastHeader,numOfElement);
            notifyItemRangeInserted(lastPos,numOfElement);
            notifyDataSetChanged();
        }catch(Exception e){

        }
        for (int elem : sortHeaderPos)
            headerPos.add(elem);

        return headerPos;
    }

}
