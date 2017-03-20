package slu.com.pandora.holder;

/**
 * Created by Pro Game on 3/3/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import slu.com.pandora.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder{

    public TextView tablenum;
    public TextView prod_id;
    public TextView prodName;
    public TextView qty;

    public ListView products;

    public RecyclerViewHolders(View itemView){
        super(itemView);
        tablenum = (TextView)itemView.findViewById(R.id.table_no);
        prodName = (TextView)itemView.findViewById(R.id.product_name);
        qty = (TextView)itemView.findViewById(R.id.quantity);
        products = (ListView)itemView.findViewById(R.id.orders_layout);

    }


}
