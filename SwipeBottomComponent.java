package com.benmu.framework.extend.comoponents.swipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alibaba.weex.plugin.annotation.WeexComponent;
import com.daimajia.swipe.SwipeLayout;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

/**
 * Created by Carry on 2018/5/5.
 */
@WeexComponent(names = "bottom-view")
public class SwipeBottomComponent extends WXVContainer<FrameLayout> {
    private FrameLayout mBottom;
    private String mDragEdge;

    public SwipeBottomComponent(WXSDKInstance instance, WXDomObject dom, WXVContainer parent,
                                String instanceId, boolean isLazy) {
        super(instance, dom, parent, instanceId, isLazy);
    }

    public SwipeBottomComponent(WXSDKInstance instance, WXDomObject node, WXVContainer parent,
                                boolean lazy) {
        super(instance, node, parent, lazy);
    }

    public SwipeBottomComponent(WXSDKInstance instance, WXDomObject node, WXVContainer parent) {
        super(instance, node, parent);
    }


    @Override
    protected FrameLayout initComponentHostView(@NonNull Context context) {
        mBottom = new FrameLayout(context);
        return mBottom;
    }

    @WXComponentProp(name = "dragEdge")
    public void setDragEdge(String dragEdge) {
        this.mDragEdge = dragEdge;
        View hostView = getParent().getHostView();
        if (hostView instanceof SwipeLayout) {
            implementDrag((SwipeLayout) hostView, mDragEdge);
        }
    }

    private void implementDrag(SwipeLayout layout, String dragEdge) {
        switch (dragEdge) {
            case "right":
                layout.addDrag(SwipeLayout.DragEdge.Right, mBottom);
                layout.setRightSwipeEnabled(true);
                break;
            case "left":
                layout.addDrag(SwipeLayout.DragEdge.Left, mBottom);
                layout.setLeftSwipeEnabled(true);
                break;
            case "top":
                layout.addDrag(SwipeLayout.DragEdge.Top, mBottom);
                layout.setTopSwipeEnabled(true);
                break;
            case "bottom":
                layout.addDrag(SwipeLayout.DragEdge.Bottom, mBottom);
                layout.setBottomSwipeEnabled(true);
                break;
            default:
                layout.addDrag(SwipeLayout.DragEdge.Left, mBottom);
                layout.setLeftSwipeEnabled(true);
                break;
        }
    }
}
