package com.example.ethan.bitmapdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.*;

/**
 * @author HUIZHENG
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载外部储存设备的图像文件
        Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/DCIM/Camera/test.jpg");
        Log.i("wechat", "压缩前图片的大小" + (bitmap.getByteCount()/1024/1024) + "M,宽度为" +
                bitmap.getWidth() + ",高度为" + bitmap.getHeight());
        //字节数据输出流
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        //1.质量压缩
        int quality=10;
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);
        byte[] bytes=baos.toByteArray();
        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
                + "M,宽度为" + bm.getWidth() + ",高度为" + bm.getHeight()
                + ",bytes.length= " + (bytes.length / 1024) + "KB,"
                + "quality=" + quality);
        saveBitmapFile(bm,"test1");

        //2.采样率压缩
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bm2 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/DCIM/Camera/test.jpg", options);
        Log.i("wechat", "压缩后图片的大小" + (bm2.getByteCount() / 1024 / 1024)
                + "M,宽度为" + bm2.getWidth() + ",高度为" + bm2.getHeight());
        saveBitmapFile(bm2,"test2");

        //3.缩放压缩
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        Bitmap bm3 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        Log.i("wechat", "压缩后图片的大小" + (bm3.getByteCount() / 1024 / 1024) + "M,宽度为"
                + bm3.getWidth() + ",高度为" + bm3.getHeight());
        saveBitmapFile(bm3,"test3");

        //4.RGB_565法
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bm4 = BitmapFactory.decodeFile(Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/DCIM/Camera/test.jpg", options2);
        Log.i("wechat", "压缩后图片的大小" + (bm4.getByteCount() / 1024 / 1024)
                + "M,宽度为" + bm4.getWidth() + ",高度为" + bm4.getHeight());
        saveBitmapFile(bm4,"test4");

        //5.createScaledBitmap
        Bitmap bm5 = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
        Log.i("wechat", "压缩后图片的大小" + (bm5.getByteCount() / 1024) + "KB,宽度为"
                + bm5.getWidth() + ",高度为" + bm5.getHeight());
        saveBitmapFile(bm5,"test5");

        //计算图片的内存大小（方法一）
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/DCIM/Camera/test.jpg");
        Log.i("wechat", "file.length()=" + file.length() / 1024);

        //计算图片的内存大小（方法二）
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Log.i("wechat", "fis.available()=" + fis.available() / 1024);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Bitmap对象保存图像文件
     * @param bitmap 对应的bitmap对象
     * @param fileName 对应的文件名
     */
    public void saveBitmapFile(Bitmap bitmap,String fileName){
        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/DCIM/Camera/"+fileName+".jpg");
        try {
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
