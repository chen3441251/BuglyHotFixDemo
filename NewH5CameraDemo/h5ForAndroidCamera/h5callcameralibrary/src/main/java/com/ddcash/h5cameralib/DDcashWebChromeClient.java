package com.ddcash.h5cameralib;

import android.app.Activity;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2018/3/28 0028 10:02
 * @ Description :     h5ForAndroidCamera
 */

public abstract class DDcashWebChromeClient extends WebChromeClient {
    private final String   mUrl;
    private       Activity mActivity;

    public abstract void onCustomShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams);

    public abstract void openCustomFileChooser(ValueCallback<Uri> filePathCallback);

    public abstract void openCustomFileChooser(ValueCallback filePathCallback, String acceptType);

    public abstract void openCustomFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture);

    public DDcashWebChromeClient(Activity activity, String url) {
        this.mActivity = activity;
        this.mUrl = url;
    }

    //android 5.0+
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        startCamera(filePathCallback);
        if (!mUrl.contains(DDcashCameraHelper.DDCASHREGEX)) {
            onCustomShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
        return true;
    }

    /**
     * 过时的方法：openFileChooser
     */

    public void openFileChooser(ValueCallback<Uri> filePathCallback) {
        startCamera(filePathCallback);
        if (!mUrl.contains(DDcashCameraHelper.DDCASHREGEX)) {
            openCustomFileChooser(filePathCallback);
        }
    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback filePathCallback, String acceptType) {
        startCamera(filePathCallback);
        if (!mUrl.contains(DDcashCameraHelper.DDCASHREGEX)) {
            openCustomFileChooser(filePathCallback, acceptType);
        }
    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
        startCamera(filePathCallback);
        if (!mUrl.contains(DDcashCameraHelper.DDCASHREGEX)) {
            openCustomFileChooser(filePathCallback, acceptType, capture);
        }
    }

    private void startCamera(ValueCallback mUploadMessage) {
        DDcashCameraHelper.getInstance().startCamera(mActivity);
        DDcashCameraHelper.getInstance().mUploadMessage = mUploadMessage;
    }
}
