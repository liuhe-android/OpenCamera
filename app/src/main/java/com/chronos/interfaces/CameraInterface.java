package com.chronos.interfaces;

import android.view.SurfaceView;

/**
 * Created by Liuhe on 2017/3/2.
 */
public interface CameraInterface {

    void onCreate(SurfaceView svMain);
    void onDestroy();
    void onResume();
    void onPause();

}
