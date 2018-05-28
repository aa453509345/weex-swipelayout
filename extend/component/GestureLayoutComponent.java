package com.benmu.wx.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

import com.benmu.wx.extend.component.view.GestureLayout;
import com.benmu.wx.extend.helper.SimpleGesture;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.view.WXFrameLayout;
import com.taobao.weex.ui.view.gesture.WXGesture;

/**
 * Created by Carry on 2018/5/25.
 */

public class GestureLayoutComponent extends WXVContainer<GestureLayout> {
    private GestureLayout mWxFrameLayout;

    public GestureLayoutComponent(WXSDKInstance instance, WXDomObject dom, WXVContainer parent,
                                  String instanceId, boolean isLazy) {
        super(instance, dom, parent, instanceId, isLazy);
    }

    public GestureLayoutComponent(WXSDKInstance instance, WXDomObject node, WXVContainer parent,
                                  boolean lazy) {
        super(instance, node, parent, lazy);
    }

    public GestureLayoutComponent(WXSDKInstance instance, WXDomObject node, WXVContainer parent) {
        super(instance, node, parent);
    }


    @Override
    protected GestureLayout initComponentHostView(@NonNull Context context) {
        mWxFrameLayout = new GestureLayout(context);
        initGesture(context);
        return mWxFrameLayout;
    }


    @WXComponentProp(name = "forceTouch")
    public void setForceTouch(boolean force) {
        if (mWxFrameLayout != null) {
            mWxFrameLayout.forceTouch(force);
        }
    }

    private void initGesture(Context context) {
        mGesture = new SimpleGesture(this, context);
        if (mWxFrameLayout != null) {
            mWxFrameLayout.setClickable(true);
            mWxFrameLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return mGesture.onTouch(v, event);
                }
            });
        }
    }
}
