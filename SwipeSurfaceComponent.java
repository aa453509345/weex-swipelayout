package com.benmu.wx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.FrameLayout;

import com.alibaba.weex.plugin.annotation.WeexComponent;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXVContainer;

/**
 * Created by Carry on 2018/5/5.
 */
public class SwipeSurfaceComponent extends WXVContainer<FrameLayout> {
    private FrameLayout mSurfaceView;

    public SwipeSurfaceComponent(WXSDKInstance instance, WXDomObject dom, WXVContainer parent,
                                 String instanceId, boolean isLazy) {
        super(instance, dom, parent, instanceId, isLazy);
    }

    public SwipeSurfaceComponent(WXSDKInstance instance, WXDomObject node, WXVContainer parent,
                                 boolean lazy) {
        super(instance, node, parent, lazy);
    }

    public SwipeSurfaceComponent(WXSDKInstance instance, WXDomObject node, WXVContainer parent) {
        super(instance, node, parent);
    }

    @Override
    protected FrameLayout initComponentHostView(@NonNull Context context) {
        Log.e("SwipeSurfaceComponent","init");
        mSurfaceView = new FrameLayout(context);
        return mSurfaceView;
    }
}
