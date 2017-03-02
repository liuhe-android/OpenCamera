package com.chronos.camera;

import android.hardware.Camera;
import android.os.Build;
import android.view.MotionEvent;


/**
 * Created by Liuhe on 2017/3/2.
 */
public class TouchFocus {

    protected Camera mCamera;
    private Camera.Parameters mParameters;


    public void focusOnTouch(MotionEvent event) {
//        设置触摸对焦

    }




    // handle button auto focus
    protected void doAutoFocus() {
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
