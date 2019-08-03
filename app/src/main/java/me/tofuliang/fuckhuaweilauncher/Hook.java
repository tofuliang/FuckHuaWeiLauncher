package me.tofuliang.fuckhuaweilauncher;

import android.util.Log;

import java.util.Arrays;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {
    public static final String TAG = "FuckHuaWeiLauncher";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        final String pn = "|" + loadPackageParam.packageName;

        XposedBridge.hookAllMethods(loadPackageParam.classLoader.loadClass("android.app.ActivityManager"), "checkUidPermission", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                Log.d(TAG + pn, "ActivityManager checkUidPermission params : " + Arrays.toString(param.args));
                if (param.args[0] == "com.huawei.permission.sec.SDK_LAUNCHER")
                    param.setResult(0);
            }
        });
    }
}
