package com.benmu.wx;

import android.app.Application;
import android.util.Log;

import com.benmu.framework.BMInitConfig;
import com.benmu.framework.BMWXApplication;
import com.benmu.framework.BMWXEngine;
import com.taobao.weex.utils.FontDO;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Carry on 2017/8/23.
 */

public class App extends BMWXApplication implements Serializable{
    public Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}
