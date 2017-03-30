package slu.com.pandora.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import slu.com.pandora.R;

/**
 * Created by matt on 3/23/2017.
 */

public class SaycoOrderHolder extends RecyclerView.ViewHolder {

    public TextView prodName;
    public TextView quantity;

    public SaycoOrderHolder(View view){
        super(view);
        prodName = (TextView) view.findViewById(R.id.sampleProdName);
        quantity = (TextView) view.findViewById(R.id.sampleQuantity);
    }
}