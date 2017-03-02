package com.chronos.camera;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.ToneGenerator;
import android.view.SurfaceView;

import com.chronos.interfaces.CameraInterface;

/**
 * Created by Liuhe on 2017/3/2.
 */
public class CameraManager extends SurfaceViewManager implements CameraInterface {

    private ToneGenerator tone;
    private static CameraManager instance;

    private CameraManager() {
        super();
    }

    private int status = 0;
    private Camera mCamera;

    public synchronized static CameraManager getInstance() {
        if (instance == null) {
            instance = new CameraManager();
        }
        return instance;
    }

    @Override
    public void onCreate(SurfaceView svMain) {
        mCamera = Camera.open();
        svMain.setKeepScreenOn(true);
        super.onCreate(svMain, mCamera);
    }

    @Override
    public void onDestroy() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
        }
    }

    @Override
    public void onResume() {
        if (status != 0) {
            onBind();
        }
        status = 1;
    }

    @Override
    public void onPause() {
        if (mCamera != null) {
            mCamera.stopPreview();
        }
        status = 2;
    }

    public void onBind() {
        if (mCamera != null) {
            super.onBind();
        }
        status = 3;
    }

    public void takePhoto() {
        if (status == 3) {
            mCamera.takePicture(shutterCallback, null, jpegCallback);
        }
    }

    //返回照片的JPEG格式的数据
    private Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            Camera.Parameters ps = camera.getParameters();
            if (ps.getPictureFormat() == PixelFormat.JPEG) {
                //存储拍照获得的图片
                String path = PicManager.getInstance().save(data);
                //将图片交给Image程序处理
//                Uri uri = Uri.fromFile(new File(path));
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                intent.setDataAndType(uri, "image/jpeg");
//                startActivity(intent);
            }
        }
    };

    //快门按下的时候onShutter()被回调
    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            if (tone == null) {
                //发出提示用户的声音
//                tone = new ToneGenerator(AudioManager.STREAM_MUSIC,
//                        ToneGenerator.MAX_VOLUME);
            }
//            tone.startTone(ToneGenerator.TONE_PROP_BEEP2);

        }
    };
}
