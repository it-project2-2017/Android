package slu.com.pandora.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import slu.com.pandora.fragment.CurrentOrdersFragment;
import slu.com.pandora.fragment.FinishedOrdersFragment;
import slu.com.pandora.fragment.QueueOrdersFragment;
import slu.com.pandora.model.ItemObject;

public class MainActivity extends AppCompatActivity{
    LinearLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Login
        //setContentView(R.layout.login);

        //OrderLayout
        //setContentView(R.layout.order_layout);

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

        //Finished Order
        /*setContentView(R.layout.finished_order);
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

        //Queue Order
       /* setContentView(R.layout.queue_orders);
        setTitle("Queue Orders");
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

        //trial for viewpager
        setContentView(R.layout.trial_viewpager);
        setTitle("Current Order");
        //AppBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        //Give the TabLayout to ViewPager
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        //Iterate over all tabs and set the custom view
        for(int ctrl = 0; ctrl < tabLayout.getTabCount(); ctrl++ ){
            TabLayout.Tab tab = tabLayout.getTabAt(ctrl);
            tab.setCustomView(pagerAdapter.getTabView(ctrl));
        }

        /*List<ItemObject> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(this);

        //Recycle View Main Display
        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        //puting the cardview inside the recyle view
        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(this, rowListItem);
        rView.setAdapter(rcAdapter);*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //Adds res/menu/appbar_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }
    //selection of action in the action bar.
    /*@Override
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
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        String tabTitles[] = new String[]{"Queue Orders", "Current Orders", "Finished Orders"};
        Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FinishedOrdersFragment();
                case 1:
                    return new CurrentOrdersFragment();
                case 2:
                    return new QueueOrdersFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
        //Create the tabs
        public View getTabView(int position) {
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
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
