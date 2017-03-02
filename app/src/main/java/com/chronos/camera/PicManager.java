package com.chronos.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Liuhe on 2017/3/2.
 */
public class PicManager {

    private static PicManager instance;

    private PicManager(){}

    public synchronized static PicManager getInstance(){
        if (instance==null){
            instance=new PicManager();
        }
        return instance;
    }

    public String save(byte[] data) {
        String path = Environment.getExternalStorageDirectory() + "/pic/VCG_" + System.currentTimeMillis() + ".jpg";
        try {
            //判断是否装有SD卡
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //判断SD卡上是否有足够的空间
                String storage = Environment.getExternalStorageDirectory().toString();
                StatFs fs = new StatFs(storage);
                long available = fs.getAvailableBlocks() * fs.getBlockSize();
                if (available < data.length) {
                    //空间不足直接返回空
                    return null;
                }
                File dir = new File(Environment.getExternalStorageDirectory() + "/pic");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(path);
                if (!file.exists())
                    //创建文件
                    file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                Bitmap bitmap = rotateImageByte(data);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                fos.write(data);
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    private Bitmap rotateImageByte(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
