package slu.com.pandora.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//para sa finished products
/*import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import slu.com.pandora.model.ItemObject;
import slu.com.pandora.adapter.RecyclerViewAdapter;*/

import java.util.ArrayList;
import java.util.List;

import slu.com.pandora.R;
import slu.com.pandora.adapter.RecyclerViewAdapter;
import slu.com.pandora.model.ItemObject;

public class MainActivity extends AppCompatActivity{
    LinearLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Login
        //setContentView(R.layout.login);

        //OrderLayout
        setContentView(R.layout.order_layout);
        //AppBar
        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        //Current Order
        /*setContentView(R.layout.current_order);
        setTitle("Current Oders");
        //AppBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        //Queue and finished
        /*
        setContentView(R.layout.finished_order);
        setTitle("Finished Orders");
        //AppBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Objects
        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(this);

        //Recycle View Main Display
        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        //puting the cardview inside the recyle view
        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(this, rowListItem);
        rView.setAdapter(rcAdapter);*/
    }

    //Adds res/menu/appbar_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }
    //selection of action in the action bar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private List<ItemObject> getAllItemList(){
        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("Table No.1","ID","Product Name","Quantity"));
        allItems.add(new ItemObject("Table No.2","ID","Product Name","Quantity"));
        allItems.add(new ItemObject("Table No.3","ID","Product Name","Quantity"));
        allItems.add(new ItemObject("Table No.4","ID","Product Name","Quantity"));

        return allItems;
    }

}
