package slu.com.pandora.holder;

/**
 * Created by Pro Game on 3/3/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import slu.com.pandora.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tablenum;
    public TextView prod_id;
    public TextView prodName;
    public TextView qty;

    public RecyclerViewHolders(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        tablenum = (TextView)itemView.findViewById(R.id.table_no);
        prod_id = (TextView)itemView.findViewById(R.id.ID);
        prodName = (TextView)itemView.findViewById(R.id.product_name);
        qty = (TextView)itemView.findViewById(R.id.quantity);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
