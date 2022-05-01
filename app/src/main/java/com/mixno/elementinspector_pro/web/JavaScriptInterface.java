package com.mixno.elementinspector_pro.web;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.mixno.elementinspector_pro.MainActivity;
import com.mixno.elementinspector_pro.dialog.CodeDialog;

public class JavaScriptInterface {
    Context context;

    public JavaScriptInterface(Context c) {
        context = c;
    }

    @JavascriptInterface
    public void processHTML(String html) {
        try {
            new CodeDialog().setCodeDialog(context, "", html, 1, new MainActivity().mWeb);
        } catch (Exception e) {}
    }
    @JavascriptInterface
    public void cookieManagerApp(int id, String name, String value, String cookie) {
//        Toast.makeText(context, "id: "+id+" name: "+name+" value: "+value, Toast.LENGTH_SHORT).show();
//        CookieManagerDialog.list.add(new CookieManagerModel(id, name, value, cookie, ""));
    }
    @JavascriptInterface
    public void toast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void getThemeColorSite(String color) {
        new MainActivity().setThemeColorApp(context, color, color);
    }
}
