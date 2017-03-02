package com.chronos.camera;

import android.hardware.Camera;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Liuhe on 2017/3/2.
 */
public class SurfaceViewManager implements SurfaceHolder.Callback{

    private Camera.Parameters mParameters;
    private SurfaceHolder holder;
    private SurfaceView surfaceView;
    private Camera mCamera;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        onBind();
    }

    public void onBind(){
        if (mCamera != null) {
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

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void onCreate(SurfaceView surfaceView,Camera camera) {
        this.surfaceView = surfaceView;
        this.holder=surfaceView.getHolder();
        this.mCamera = camera;
    }

    public SurfaceHolder getHolder() {
        return holder;
    }

    // handle button auto focus
    private void doAutoFocus() {
        mParameters = mCamera.getParameters();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mCamera.enableShutterSound(false);
        }
        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(mParameters);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦。
                    if (!Build.MODEL.equals("KORIDY H30")) {
                        mParameters = camera.getParameters();
                        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 1连续对焦
                        camera.setParameters(mParameters);
                    } else {
                        mParameters = camera.getParameters();
                        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                        camera.setParameters(mParameters);
                    }
                }
            }
        });
    }
}
