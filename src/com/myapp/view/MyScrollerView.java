package com.myapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by kuajie on 15/7/23.
 */
public class MyScrollerView extends ScrollView {

    private ScroviewListener scroviewListener = null;

    public MyScrollerView(Context context) {
        super(context);
    }


    public MyScrollerView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScroviewListener(ScroviewListener scroviewListener) {
        this.scroviewListener = scroviewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scroviewListener != null) {
            scroviewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}
