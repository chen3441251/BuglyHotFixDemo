package com.ddcash.h5cameralib;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.webkit.ValueCallback;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2018/3/28 0028 10:09
 * @ Description :     h5ForAndroidCamera
 */

public class DDcashCameraHelper {
    /**
     * 图片选择回调
     */
    protected      ValueCallback      mUploadMessage;
    private static DDcashCameraHelper instance;
    //    private ddcashInterface ddcashH5Listenner;
    public static final String DDCASHREGEX = "ddcash.cn";
    //    private final        int REQUEST_CODE_TAKE_PHOTO = 1001;//拍照
    //    private static final int REQUEST_CODE_PERMISSION = 0x110;

    private DDcashCameraHelper() {
    }

    public static DDcashCameraHelper getInstance() {
        if (instance == null) {
            synchronized (DDcashCameraHelper.class) {
                if (instance == null) {
                    instance = new DDcashCameraHelper();
                }
            }
        }
        return instance;
    }

    protected void startCamera(Activity activity) {
        activity.startActivity(new Intent(activity, DDcashCallCameraActivity.class));
    }

    /**
     * 判断是否有camera权限（主要在6.0以下国产手机手机）
     *
     * @return
     */
    public static boolean isCameraUseable() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
            mCamera = null;
        }

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }

        return canUse;
    }

}
