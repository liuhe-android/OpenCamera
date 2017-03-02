package com.chronos.camera;

import android.hardware.Camera;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.chronos.utils.LogUtils;

import java.io.IOException;

/**
 * Created by Liuhe on 2017/3/2.
 */
public class SurfaceViewManager extends TouchFocus implements SurfaceHolder.Callback, View.OnTouchListener {

    private Camera.Parameters mParameters;
    private SurfaceHolder holder;
    private SurfaceView surfaceView;
    private boolean isAuto = true;//自动对焦

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.e("======surface=======");
        onBind();
    }

    public void onBind() {
        if (mCamera != null) {
            LogUtils.e("onbind");
            mCamera.setDisplayOrientation(90);
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 实现自动对焦
        if (isAuto) {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦
                        doAutoFocus();
                    }
                }
            });
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void onCreate(SurfaceView surfaceView, Camera camera) {
        this.surfaceView = surfaceView;
        this.holder = surfaceView.getHolder();
        this.mCamera = camera;
        surfaceView.setOnTouchListener(this);
        holder.addCallback(this);
    }

    public SurfaceHolder getHolder() {
        return holder;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //触摸对焦
        focusOnTouch(event);
        return true;
    }
}
