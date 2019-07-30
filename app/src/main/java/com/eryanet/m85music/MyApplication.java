package com.eryanet.m85music;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.eryanet.m85musicsdk.sdk.M85MusicSDK;

import java.util.List;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getProcessName(this);
        Log.e("music", "processName = " + processName);
        if (processName != null) {
            if (getPackageName().equals(processName)) {
                M85MusicSDK.getInstance(getApplicationContext()).init(this);
            }
        }
    }
    private String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }

        }
        return null;
    }

}
