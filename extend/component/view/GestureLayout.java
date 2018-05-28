package com.benmu.wx.extend.component.view;

import android.content.Context;
import android.view.MotionEvent;

import com.taobao.weex.ui.view.WXFrameLayout;

/**
 * Created by Carry on 2018/5/25.
 */

public class GestureLayout extends WXFrameLayout {
    private boolean mForceTouch = false;

    public GestureLayout(Context context) {
        super(context);
    }

    public void forceTouch(boolean force) {
        this.mForceTouch = force;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev) || mForceTouch;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event) || mForceTouch;
    }
}
