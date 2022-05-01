package com.mixno.elementinspector_pro.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Proxy;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.webkit.WebView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ProxySettings {

    public static String PROXY_DEFAULT_HOST = "15.235.41.55";
    public static int PROXY_DEFAULT_PORT = 8990;

    @SuppressLint("NewApi")
    @SuppressWarnings("all")
    public static boolean setProxyWebView(WebView webView, String host, int port, String applicationClassName) throws Exception {
        Context appContext = webView.getContext().getApplicationContext();
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", port + "");
        System.setProperty("https.proxyHost", host);
        System.setProperty("https.proxyPort", port + "");
        try {
            Class applictionCls = Class.forName(applicationClassName);
            Field loadedApkField = applictionCls.getField("mLoadedApk");
            loadedApkField.setAccessible(true);
            Object loadedApk = loadedApkField.get(appContext);
            Class loadedApkCls = Class.forName("android.app.LoadedApk");
            Field receiversField = loadedApkCls.getDeclaredField("mReceivers");
            receiversField.setAccessible(true);
            ArrayMap receivers = (ArrayMap) receiversField.get(loadedApk);
            for (Object receiverMap : receivers.values()) {
                for (Object rec : ((ArrayMap) receiverMap).keySet()) {
                    Class clazz = rec.getClass();
                    if (clazz.getName().contains("ProxyChangeListener")) {
                        Method onReceiveMethod = clazz.getDeclaredMethod("onReceive", Context.class, Intent.class);
                        Intent intent = new Intent(Proxy.PROXY_CHANGE_ACTION);
                        /*** optional, may be need in future ***/
                        final String CLASS_NAME = "android.net.ProxyProperties";
                        Class cls = Class.forName(CLASS_NAME);
                        Constructor constructor = cls.getConstructor(String.class, Integer.TYPE, String.class);
                        constructor.setAccessible(true);
                        Object proxyProperties = constructor.newInstance(host, port, null);
                        intent.putExtra("proxy", (Parcelable) proxyProperties);
                        /*** optional, may be need in future ***/
                        onReceiveMethod.invoke(rec, appContext, intent);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            Toast.makeText(appContext, "Proxy error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
