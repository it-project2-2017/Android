/**
 * Created by matt on 2/14/2017.
 */

package slu.com.pandora.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import slu.com.pandora.R;

public class sampleXML extends AppCompatActivity {
    private final static String orderStatus = "unpaid";
    TextView prodName;
    TextView quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_view_orders_layout);
        Intent intent = getIntent();

        prodName = (TextView) findViewById(R.id.sampleProdName);
        quantity = (TextView) findViewById(R.id.sampleQuantity);
        prodName.setText("POG BA AKO?");
        quantity.setText("Pogi ako");
    }


}
