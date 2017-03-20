package slu.com.pandora.model;

import java.util.ArrayList;
import java.util.List;

import slu.com.pandora.R;

/**
 * Created by Pro Game on 3/6/2017.
 */

public enum ViewPagerObject {

    queueOrder(R.string.queue_title, R.layout.queue_orders),
    currentOrder(R.string.currentOrder_title, R.layout.current_order),
    finishedOrder(R.string.finishedOrder_title, R.layout.finished_order);

    private int mTitleResId;
    private int mLayoutResId;

    ViewPagerObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }



}
