package com.benmu.wx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.weex.plugin.annotation.WeexComponent;
import com.daimajia.swipe.SwipeLayout;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;

/**
 * Created by Carry on 2018/5/5.
 */
public class SwipeLayoutComponent extends WXVContainer<SwipeLayout> {
    private SwipeLayout mContainer;

    public SwipeLayoutComponent(WXSDKInstance instance, WXDomObject dom, WXVContainer parent,
                                String instanceId, boolean isLazy) {
        super(instance, dom, parent, instanceId, isLazy);
    }

    public SwipeLayoutComponent(WXSDKInstance instance, WXDomObject node, WXVContainer parent,
                                boolean lazy) {
        super(instance, node, parent, lazy);
    }

    public SwipeLayoutComponent(WXSDKInstance instance, WXDomObject node, WXVContainer parent) {
        super(instance, node, parent);
    }

    @Override
    protected SwipeLayout initComponentHostView(@NonNull Context context) {
        Log.e("SwipeLayoutComponent", "init");
        mContainer = new SwipeLayout(context);
        mContainer.setShowMode(SwipeLayout.ShowMode.PullOut);
        mContainer.setRightSwipeEnabled(false);
        mContainer.setLeftSwipeEnabled(false);
        mContainer.setTopSwipeEnabled(false);
        mContainer.setBottomSwipeEnabled(false);
        return mContainer;
    }

    @Override
    public void addChild(WXComponent child, int index) {
        super.addChild(child, index);
    }


    @JSMethod(uiThread = true)
    public void openEdge(String edge) {
        mContainer.open(getEdge(edge));
    }

    @JSMethod
    public void close() {
        mContainer.close();
    }



    private SwipeLayout.DragEdge getEdge(String edge) {
        switch (edge) {
            case "left":
                return SwipeLayout.DragEdge.Left;
            case "right":
                return SwipeLayout.DragEdge.Right;
            case "top":
                return SwipeLayout.DragEdge.Top;
            case "bottom":
                return SwipeLayout.DragEdge.Bottom;
            default:
                return SwipeLayout.DragEdge.Left;
        }
    }


}
