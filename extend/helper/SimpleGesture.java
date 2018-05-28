package com.benmu.wx.extend.helper;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;

import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.view.gesture.WXGesture;

import static com.benmu.wx.extend.helper.SimpleGesture.Event.ONCLICK;
import static com.benmu.wx.extend.helper.SimpleGesture.Event.ONDOUBLECLICK;
import static com.benmu.wx.extend.helper.SimpleGesture.Event.ONLONGPRESS;

/**
 * Created by Carry on 2018/5/25.
 */

public class SimpleGesture extends WXGesture {
    private Context mContext;
    private WXComponent mComponent;

    public SimpleGesture(WXComponent wxComponent, Context context) {
        super(wxComponent, context);
        this.mContext = context;
        this.mComponent = wxComponent;
    }

    interface Event {
        String ONLONGPRESS = "onlongpress";
        String ONCLICK = "onclick";
        String ONDOUBLECLICK = "ondoubleclick";
    }

    @Override
    public void onLongPress(MotionEvent e) {
        fireEvent(ONLONGPRESS);
        super.onLongPress(e);
    }


    private void fireEvent(String type) {
        if (mComponent != null && mComponent.getInstance() != null) {
            mComponent.getInstance().fireEvent(mComponent.getRef(), type);
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        fireEvent(ONCLICK);
        return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        fireEvent(ONDOUBLECLICK);
        return super.onDoubleTap(e);
    }


    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return super.onDoubleTapEvent(e);
    }


}
