package slu.com.pandora.holder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import slu.com.pandora.R;
import slu.com.pandora.fragment.CurrentOrdersFragment;
import slu.com.pandora.model.ListOrder;

/**
 * Created by Pro Game on 4/1/2017.
 */

public class CurrentOrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView prodName;
    private TextView qty;
    private TextView tableNo;
    private TextView tableid;
    private Button finishBtn;
    public CheckBox checkBox;
    private List<ListOrder> listOrders;
    // Get the id of the layout which will be used in displaying the product name, quantity and table no.
    public CurrentOrderHolder (final View itemView) {
        super(itemView);

        prodName = (TextView) itemView.findViewById(R.id.current_prod);
        qty = (TextView) itemView.findViewById(R.id.current_qty);
        tableNo = (TextView) itemView.findViewById(R.id.current_header);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkBox4);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "" + getAdapterPosition() , Toast.LENGTH_SHORT).show();
    }

    //List all orders
    public void bindViewList(final int pos, List<ListOrder> listOrders, Set<Integer> position){
        //variable is used in getting the position of a certain product.

        int variable = 0;
        //positionOrder is used in getting the product list
        int positionOrder = 0;

        //Set
        List<Integer> listPostion = new ArrayList<>();
        for(int eachInteger : position)
            listPostion.add(eachInteger);
        Collections.sort(listPostion);
        listPostion.remove(listPostion.size()-1);
        //inorder to get each position we need to use a for loop.
        for (int ctrl = 0; ctrl < listPostion.size();ctrl++){
            if (pos > listPostion.get(ctrl)){
                variable = pos - listPostion.get(ctrl);
                //variable gets the value of pos - listPostion.get(ctrl) and then proceeds to deduct one inorder to get the exact position.
                variable--;
                //gets the position of the product list.
                positionOrder = ctrl;

            }
        }

        prodName.setText(listOrders.get(positionOrder).getProdlist().get(variable).getKey().toString());
        qty.setText(listOrders.get(positionOrder).getProdlist().get(variable).getValue().toString());

    }

    //For the Table No./ heading
    public void bindViewHeader(final int pos, List<ListOrder> listOrders, Set<Integer> position){
        //Set
        List<Integer> listPostion = new ArrayList<>();
        for(int eachInteger : position)
            listPostion.add(eachInteger);
        Collections.sort(listPostion);
        listPostion.remove(listPostion.size()-1);

        for(int ctrl = 0; ctrl < listPostion.size(); ctrl++){

            if(pos == listPostion.get(ctrl)) {
                tableNo.setText("Table No." + String.valueOf(listOrders.get(ctrl).getTablenum()));
                tableid.setText(String.valueOf(listOrders.get(ctrl).getId()));
            }

        }
    }
}
