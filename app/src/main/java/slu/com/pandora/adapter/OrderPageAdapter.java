package slu.com.pandora.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import slu.com.pandora.R;
import slu.com.pandora.fragment.CurrentOrdersFragment;
import slu.com.pandora.fragment.FinishedOrdersFragment;
import slu.com.pandora.fragment.QueueOrdersFragment;

/**
 * Created by Pro Game on 3/11/2017.
 */

public class OrderPageAdapter extends FragmentPagerAdapter {
    //Name of fragments
    String tabTitles[] = new String[]{"Queue Orders", "Current Orders", "Finished Orders"};
    Context context;

    public OrderPageAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    //Fragments to be fetch
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new QueueOrdersFragment();
            case 1:
                return new CurrentOrdersFragment();
            case 2:
                return new FinishedOrdersFragment();
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
        View tab = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) tab.findViewById(R.id.custom_text);
        tv.setText(tabTitles[position]);
        return tab;
    }
}
