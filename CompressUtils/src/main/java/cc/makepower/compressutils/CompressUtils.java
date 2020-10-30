package cc.makepower.compressutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class CompressUtils {

//    public static Bitmap compress(String oldImgFile) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 2;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(oldImgFile, options);
//
//        return bitmap;
//    }
    public static Bitmap compress(String oldImgFile) {

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPreferredConfig= Bitmap.Config.RGB_565;

        Bitmap bitmap = BitmapFactory.decodeFile(oldImgFile, options);

        return bitmap;
    }

}
