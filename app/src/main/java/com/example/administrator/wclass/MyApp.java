package com.example.administrator.wclass;

import android.app.Application;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.example.administrator.wclass.data.bean.ClassBean;

/**
 * Created by Administrator on 2018/4/29.
 */

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"fKQp1zsWrrHNpLfyabLMimSt-gzGzoHsz","TJOeIonGOBMOWjPrNSd698Qa");
        AVOSCloud.setDebugLogEnabled(true);

    }
}
