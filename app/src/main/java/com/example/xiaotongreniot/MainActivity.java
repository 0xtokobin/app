package com.example.xiaotongreniot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toolbar;
import com.example.xiaotongreniot.MyWebChromeClient;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private static  final  boolean IsDebug = true ;
    private static final String TAG = "MainActivity";

    WebView webView_main ;
    Toolbar toolbar;
    Vibrator vibrator ;



    public class JS_to_native extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void Vibrator(int time) {
//            int time = Integer.parseInt(msg);
            vibrator.vibrate(time);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView_main = (WebView)findViewById(R.id.webview_main);
        webView_main.getSettings().setJavaScriptEnabled(true);
        webView_main.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView_main.getSettings().setSupportMultipleWindows(true);
        webView_main.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //no cache the resource
//        webView_main.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView_main.addJavascriptInterface(new JS_to_native(), "JS_to_native");
        webView_main.getSettings().setDomStorageEnabled(true); //local storage

//        //设置可自由缩放网页
//        webView_help.getSettings().setSupportZoom(true);
//        webView_help.getSettings().setBuiltInZoomControls(true);

        webView_main.setWebViewClient(new WebViewClient());
        webView_main.setWebChromeClient(new MyWebChromeClient());
//        webView_main.loadUrl(getResources().getString(R.string.main_webview_url));
        webView_main.loadUrl(getResources().getString(R.string.main_webview_url));


        checkPublishPermission();


    }




    //Build.VERSION.SDK_INT >= 23
    private boolean checkPublishPermission(){
        List<String> permissions = new ArrayList<>();
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.INTERNET)) {
            permissions.add(android.Manifest.permission.INTERNET);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA)) {
            permissions.add(android.Manifest.permission.CAMERA);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.RECORD_AUDIO)) {
            permissions.add(android.Manifest.permission.RECORD_AUDIO);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.MODIFY_AUDIO_SETTINGS)) {
            permissions.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.VIBRATE)) {
            permissions.add(Manifest.permission.VIBRATE);
        }
        if (permissions.size() != 0) {
            ActivityCompat.requestPermissions(MainActivity.this,(String[]) permissions.toArray(new String[0]),111);
            return false;
        }
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            new AlertDialog.Builder(this)
                    .setTitle(" ")
                    .setMessage("确定要退出吗？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    }).show();
            return false;

        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

}