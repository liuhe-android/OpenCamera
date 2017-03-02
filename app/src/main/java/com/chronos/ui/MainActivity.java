package com.chronos.ui;

import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.chronos.camera.CameraManager;

import chronos.bizhiquan.com.camera.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btTakePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initCamera();
    }

    private void initCamera() {
        CameraManager.getInstance().onCreate((SurfaceView) findViewById(R.id.sv_main));
    }

    private void initListener() {
        btTakePic.setOnClickListener(this);
    }

    private void initView() {
        btTakePic= (Button) findViewById(R.id.bt);
    }


    @Override
    protected void onDestroy() {
        CameraManager.getInstance().onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        CameraManager.getInstance().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        CameraManager.getInstance().onPause();
        super.onPause();
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                CameraManager.getInstance().takePhoto();
                break;
        }
    }

}
