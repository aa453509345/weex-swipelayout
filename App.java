package com.benmu.wx;

import android.app.Application;

import com.benmu.framework.BMInitConfig;
import com.benmu.framework.BMWXApplication;
import com.benmu.framework.BMWXEngine;
import com.benmu.wx.extend.component.DraggableListComponent;
import com.benmu.wx.extend.component.GestureLayoutComponent;
import com.benmu.wx.extend.component.SwipeBottomComponent;
import com.benmu.wx.extend.component.SwipeLayoutComponent;
import com.benmu.wx.extend.component.SwipeSurfaceComponent;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.dom.WXListDomObject;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.ui.component.list.WXListComponent;

/**
 * Created by Carry on 2017/8/23.
 */

public class App extends BMWXApplication {
    public Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        try {
            WXSDKEngine.registerComponent("bottom-view", SwipeBottomComponent.class);
            WXSDKEngine.registerComponent("swipe-layout", SwipeLayoutComponent.class);
            WXSDKEngine.registerComponent("surface-view", SwipeSurfaceComponent.class);
            WXSDKEngine.registerComponent(DraggableListComponent.class, false, "drag-list");
            WXSDKEngine.registerComponent("div-gesture", GestureLayoutComponent.class);
            WXSDKEngine.registerDomObject("drag-list", WXListDomObject.class);
        } catch (WXException e) {
            e.printStackTrace();
        }

    }

}
