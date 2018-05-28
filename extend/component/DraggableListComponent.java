package com.benmu.wx.extend.component;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.benmu.framework.extend.hook.HookConstants;
import com.benmu.framework.extend.hook.ui.view.HookBounceRecyclerView;
import com.benmu.framework.extend.hook.ui.view.refresh.bmrefresh.BMBaseRefresh;
import com.benmu.framework.extend.hook.ui.view.refresh.bmrefresh.BMLoadingRefresh;
import com.benmu.wx.extend.helper.DefaultDragHelper;
import com.benmu.wx.extend.helper.DragHelper;
import com.benmu.wx.extend.helper.EventTrigger;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.ImmutableDomObject;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXBaseRefresh;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXLoading;
import com.taobao.weex.ui.component.WXRefresh;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.component.list.WXCell;
import com.taobao.weex.ui.component.list.WXListComponent;
import com.taobao.weex.ui.view.listview.adapter.ListBaseViewHolder;
import com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * Created by Carry on 2018/5/23.
 */

public class DraggableListComponent extends WXListComponent {
    private String TAG = getClass().getName();
    /**
     * exclude cell when dragging(attributes for cell)
     */
    private static final String EXCLUDED = "dragExcluded";

    /**
     * the type to trigger drag-drop
     */
    private static final String DRAG_TRIGGER_TYPE = "dragTriggerType";

    private static final String DEFAULT_TRIGGER_TYPE = DraggableListComponent.DragTriggerType
            .LONG_PRESS;
    private static final boolean DEFAULT_EXCLUDED = false;

    private static final String DRAG_ANCHOR = "dragAnchor";


    private boolean mAddCustomRefresh;
    private BMBaseRefresh mBMRefresh;
    private DragHelper mDragHelper;
    private String mTriggerType;
    private boolean mDragable = false;

    public DraggableListComponent(WXSDKInstance instance, WXDomObject dom, WXVContainer parent,
                                  String
                                          instanceId, boolean isLazy) {
        super(instance, dom, parent, instanceId, isLazy);
    }


    public DraggableListComponent(WXSDKInstance instance, WXDomObject node, WXVContainer parent,
                                  boolean lazy) {
        super(instance, node, parent, lazy);
    }

    @Override
    protected void onHostViewInitialized(BounceRecyclerView host) {
        super.onHostViewInitialized(host);
        mDragHelper = new DefaultDragHelper(mChildren, getHostView().getInnerView(), new
                EventTrigger() {
                    @Override
                    public void triggerEvent(String type, Map<String, Object> args) {
                        fireEvent(type, args);
                    }
                });
        mTriggerType = getTriggerType(getDomObject());

    }


    private String getTriggerType(@Nullable ImmutableDomObject domObject) {
        String triggerType = DEFAULT_TRIGGER_TYPE;
        if (domObject == null) {
            return triggerType;
        }
        triggerType = WXUtils.getString(domObject.getAttrs().get(DRAG_TRIGGER_TYPE),
                DEFAULT_TRIGGER_TYPE);
        if (!DragTriggerType.LONG_PRESS.equals(triggerType) && !DragTriggerType.PAN.equals
                (triggerType)) {
            triggerType = DEFAULT_TRIGGER_TYPE;
        }

        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d(TAG, "trigger type is " + triggerType);
        }

        return triggerType;
    }


    @WXComponentProp(name = HookConstants.NAME.SHOW_REFRESH)
    public void setBMRefresh(String showRefresh) {
        this.mAddCustomRefresh = WXUtils.getBoolean(showRefresh, false);
    }


    @Override
    protected BounceRecyclerView generateListView(Context context, int orientation) {
        BounceRecyclerView bounceRecyclerView = new HookBounceRecyclerView(context, mLayoutType,
                mColumnCount, mColumnGap, orientation);
        if (bounceRecyclerView.getSwipeLayout() != null) {
            if (WXUtils.getBoolean(getDomObject().getAttrs().get(Constants.Name
                    .NEST_SCROLLING_ENABLED), false)) {
                bounceRecyclerView.getSwipeLayout().setNestedScrollingEnabled(true);
            }
        }
        return bounceRecyclerView;
    }

    @Override
    public void addChild(WXComponent child, int index) {
        super.addChild(child, index);
        addCustomRefresh();
    }


    public void addCustomRefresh() {
        if (!mAddCustomRefresh || mBMRefresh != null) return;
        mBMRefresh = new BMLoadingRefresh(getContext(), this);
        getHostView().setOnRefreshListener(mBMRefresh);
        getHostView().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getHostView() instanceof HookBounceRecyclerView) {
                    ((HookBounceRecyclerView) getHostView()).setCustomHeaderView(mBMRefresh);
                }
            }
        }, 100);
    }

    @JSMethod
    public void refreshEnd() {
        if (mBMRefresh != null && mBMRefresh.mCurrentState == BMBaseRefresh.STATE_REFRESHING) {
            mBMRefresh.onRefreshComplete();
        }
    }


    @WXComponentProp(name = Constants.Name.DRAGGABLE)
    public void setDraggable(boolean isDraggable) {
        if (isDraggable & mDragable) {
            return;
        }

        this.mDragable = isDraggable;
        if (mDragHelper != null) {
            mDragHelper.setDraggable(mDragable);
        }
        if (getHostView() != null && getHostView().getRecyclerViewBaseAdapter() != null) {

            getHostView().getRecyclerViewBaseAdapter().notifyDataSetChanged();
        }

        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d("set draggable : " + isDraggable);
        }

    }

    @WXComponentProp(name = "vibrate")
    public void setDragVibrate(boolean vibrate) {
        if (mDragHelper != null) {
            mDragHelper.setVibrate(vibrate);
        }
    }


    @Override
    public void onBindViewHolder(final ListBaseViewHolder holder, int position) {
        if (holder == null) return;
        holder.setComponentUsing(true);
        WXComponent component = getChild(position);
        if (component == null
                || (component instanceof WXRefresh)
                || (component instanceof WXLoading)
                || (component.getDomObject() != null && component.getDomObject().isFixed())
                ) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d(TAG, "Bind WXRefresh & WXLoading " + holder);
            }
            if (component instanceof WXBaseRefresh
                    && holder.getView() != null
                    && component.getDomObject() != null
                    && (component.getDomObject().getAttrs().get("holderBackground") != null)) {
                Object holderBackground = component.getDomObject().getAttrs().get
                        ("holderBackground");
                int color = WXResourceUtils.getColor(holderBackground.toString(), Color.WHITE);
                holder.getView().setBackgroundColor(color);
                holder.getView().setVisibility(View.VISIBLE);
                holder.getView().postInvalidate();
            }
            return;
        }

        if (holder.getComponent() != null && holder.getComponent() instanceof WXCell) {
            if (holder.isRecycled()) {
                holder.bindData(component);
                component.onRenderFinish(STATE_UI_FINISH);
            }
            if (mDragHelper == null || !mDragHelper.isDraggable()) {
                return;
            }
            mTriggerType = (mTriggerType == null) ? DEFAULT_TRIGGER_TYPE : mTriggerType;

            WXCell cell = (WXCell) holder.getComponent();
            boolean isExcluded = DEFAULT_EXCLUDED;
            if (cell.getDomObject() != null) {
                WXAttr cellAttrs = cell.getDomObject().getAttrs();
                isExcluded = WXUtils.getBoolean(cellAttrs.get(EXCLUDED), DEFAULT_EXCLUDED);
            }

            mDragHelper.setDragExcluded(holder, isExcluded);

            //NOTICE: event maybe consumed by other views
            if (DraggableListComponent.DragTriggerType.PAN.equals(mTriggerType)) {
                mDragHelper.setLongPressDragEnabled(false);

                WXComponent anchorComponent = findComponentByAnchorName(cell, DRAG_ANCHOR);

                if (anchorComponent != null && anchorComponent.getHostView() != null &&
                        !isExcluded) {
                    View anchor = anchorComponent.getHostView();
                    anchor.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (MotionEventCompat.getActionMasked(event) == MotionEvent
                                    .ACTION_DOWN) {
                                mDragHelper.startDrag(holder);
                            }
                            return true;
                        }
                    });
                } else {
                    if (WXEnvironment.isApkDebugable()) {
                        if (!isExcluded) {
                            WXLogUtils.e(TAG, "[error] onBindViewHolder: the anchor component or " +
                                    "view is not found");
                        } else {
                            WXLogUtils.d(TAG, "onBindViewHolder: position " + position + " is " +
                                    "drag excluded");
                        }
                    }
                }

            } else if (DraggableListComponent.DragTriggerType.LONG_PRESS.equals(mTriggerType)) {
                mDragHelper.setLongPressDragEnabled(true);
            }
        }
    }

    @JSMethod
    public void validDragMode() {
        if (mDragable) return;
        if (mDragHelper != null) {
            mDragHelper.setDraggable(true);
        }
        getHostView().getRecyclerViewBaseAdapter().notifyDataSetChanged();
    }

    @JSMethod
    public void invalidDragMode() {
        if (!mDragable) return;
        if (mDragHelper != null) {
            mDragHelper.setDraggable(false);
        }
        getHostView().getRecyclerViewBaseAdapter().notifyDataSetChanged();
    }

    /**
     * gesture type which can trigger drag&drop
     */
    interface DragTriggerType {
        String PAN = "pan";
        String LONG_PRESS = "longpress";
    }


    private WXComponent findComponentByAnchorName(@NonNull WXComponent root, @NonNull String
            anchorName) {
        long start = 0;
        if (WXEnvironment.isApkDebugable()) {
            start = System.currentTimeMillis();
        }

        Deque<WXComponent> deque = new ArrayDeque<>();
        deque.add(root);
        while (!deque.isEmpty()) {
            WXComponent curComponent = deque.removeFirst();
            ImmutableDomObject object = curComponent.getDomObject();
            if (object != null) {
                String isAnchorSet = WXUtils.getString(object.getAttrs().get(anchorName), null);

                //hit
                if (isAnchorSet != null && isAnchorSet.equals("true")) {
                    if (WXEnvironment.isApkDebugable()) {
                        WXLogUtils.d("dragPerf", "findComponentByAnchorName time: " + (System
                                .currentTimeMillis() - start) + "ms");
                    }
                    return curComponent;
                }
            }
            if (curComponent instanceof WXVContainer) {
                WXVContainer container = (WXVContainer) curComponent;
                for (int i = 0, len = container.childCount(); i < len; i++) {
                    WXComponent child = container.getChild(i);
                    deque.add(child);
                }
            }
        }

        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d("dragPerf", "findComponentByAnchorName elapsed time: " + (System
                    .currentTimeMillis() - start) + "ms");
        }
        return null;

    }


}
