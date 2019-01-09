package com.ddcash.h5cameralib;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.ddcash.h5cameralib.h5callcameralibrary.R;

import java.io.File;

import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2018/3/28 0020 15:28
 * @ Description :     H5调用camera
 */

public class DDcashCallCameraActivity extends Activity {
    private static final int PERMISSION_REQUESTCODE_CAMERA = 1;
    private final        int REQUEST_CODE_TAKE_PHOTO       = 1001;//拍照
    public static File mPhotoFile;
    public static Uri  mContentUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void checkPermission() {
        boolean bcamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED;
        //如果camera或者读取权限没有授权
        if (DDcashCameraHelper.isCameraUseable() && bcamera) {
            //已经授权，直接调用相机
            goToTakePhoto();
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Toast.makeText(this, getResources().getString(R.string.camera_tips), Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUESTCODE_CAMERA);
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUESTCODE_CAMERA:
                if (grantResults.length >= 1 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    goToTakePhoto();
                } else {
                    //如果拒绝勾选了不再提示
                    Toast.makeText(this, getResources().getString(R.string.camera_tips), Toast.LENGTH_LONG).show();
                    if (DDcashCameraHelper.getInstance().mUploadMessage != null) {
                        DDcashCameraHelper.getInstance().mUploadMessage.onReceiveValue(null);
                        DDcashCameraHelper.getInstance().mUploadMessage = null;
                    }
                    finish();
                }
                break;

        }
    }

    /**
     * 调用系统相机
     */
    private void goToTakePhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mPhotoFile = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            try {
                if (mPhotoFile.exists()) {
                    mPhotoFile.delete();
                }
                mPhotoFile.createNewFile();
            } catch (Exception e) {
                Toast.makeText(this, getResources().getString(R.string.camera_tips), Toast.LENGTH_LONG).show();
            }
            if (Build.VERSION.SDK_INT > 23) {
                /**Android 7.0以上的方式**/
                mContentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider",
                        mPhotoFile);
                grantUriPermission(getPackageName(), mContentUri, Intent
                        .FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                /**Android 7.0以下的方式**/
                mContentUri = Uri.fromFile(mPhotoFile);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mContentUri);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
        } catch (Exception e) {
            int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (PackageManager.PERMISSION_GRANTED != flag) {
                Toast.makeText(this, getResources().getString(R.string.camera_tips), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照
            case REQUEST_CODE_TAKE_PHOTO:
                if (resultCode != RESULT_OK) {
                    if (DDcashCameraHelper.getInstance().mUploadMessage != null) {
                        DDcashCameraHelper.getInstance().mUploadMessage.onReceiveValue(null);
                        DDcashCameraHelper.getInstance().mUploadMessage = null;
                    }
                } else {
                    if (DDcashCameraHelper.getInstance().mUploadMessage != null) {
                        if (Build.VERSION.SDK_INT > KITKAT) {
                            DDcashCameraHelper.getInstance().mUploadMessage.onReceiveValue(new Uri[]{mContentUri});
                        } else {
                            DDcashCameraHelper.getInstance().mUploadMessage.onReceiveValue(mContentUri);
                        }
                    }

                }
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPhotoFile != null) {
            outState.putSerializable("photoFile", mPhotoFile);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPhotoFile == null) {
            mPhotoFile = (File) savedInstanceState.getSerializable("photoFile");
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                /**Android 7.0以上的方式**/
                mContentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider",
                        mPhotoFile);
                grantUriPermission(getPackageName(), mContentUri, Intent
                        .FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                /**Android 7.0以下的方式**/
                mContentUri = Uri.fromFile(mPhotoFile);
            }
        }

    }
}
