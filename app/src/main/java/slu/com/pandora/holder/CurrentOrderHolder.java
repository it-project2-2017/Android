package slu.com.pandora.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import slu.com.pandora.R;

/**
 * Created by Pro Game on 4/1/2017.
 */

public class CurrentOrderHolder extends RecyclerView.ViewHolder {
    private TextView prodName;
    private TextView qty;
    private TextView tableNo;

    // Get the id of the layout which will be used in displaying the product name, quantity and table no.
    public CurrentOrderHolder (View itemView) {
        super(itemView);

        prodName = (TextView) itemView.findViewById(R.id.current_prod);
        qty = (TextView) itemView.findViewById(R.id.current_qty);

        tableNo = (TextView) itemView.findViewById(R.id.current_header);
    }
}
