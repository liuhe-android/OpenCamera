package com.chronos.camera;

import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.view.MotionEvent;

import com.chronos.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liuhe on 2017/3/2.
 */
public class TouchFocus {


    protected Camera mCamera;
    private Camera.Parameters mParameters;


    public void focusOnTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int screenWidth = App.getApplication().getScreenWidth();
        int screenHeight = App.getApplication().getScreenHeight();
        mParameters = mCamera.getParameters();
        if (mParameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            Rect area1 = new Rect(x - 100, x - 100, x + 100, x + 100);
            areas.add(new Camera.Area(area1, 600));
            Rect area2 = new Rect(0, screenWidth, 0, screenHeight);
            areas.add(new Camera.Area(area2, 400));
            mParameters.setMeteringAreas(areas);
        }
        mCamera.cancelAutoFocus();
        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(mParameters);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {

            }
        });
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
