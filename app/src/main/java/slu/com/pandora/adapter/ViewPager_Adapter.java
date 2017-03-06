package slu.com.pandora.adapter;

/**
 * Created by Pro Game on 3/6/2017.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import slu.com.pandora.model.ViewPagerObject;

public class ViewPager_Adapter extends PagerAdapter {
    private Context context;

    public ViewPager_Adapter(Context context){
        this.context = context;
    }

   @Override
    public Object instantiateItem(ViewGroup collection, int postion){
        ViewPagerObject viewPagerObject = ViewPagerObject.values()[postion];
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(viewPagerObject.getLayoutResId(), collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int postion, Object view){
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return ViewPagerObject.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ViewPagerObject customPagerEnum = ViewPagerObject.values()[position];
        return context.getString(customPagerEnum.getTitleResId());
    }

}
