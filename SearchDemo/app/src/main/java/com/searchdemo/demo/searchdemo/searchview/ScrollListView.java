package com.searchdemo.demo.searchdemo.searchview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2018/3/19 0019 16:47
 * @ Description :     兼容scrollview中的listview
 */

public class ScrollListView extends ListView {
    public ScrollListView(Context context) {
        this(context,null);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
