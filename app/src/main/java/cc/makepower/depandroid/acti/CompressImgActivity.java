package cc.makepower.depandroid.acti;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cc.makepower.compressutils.CompressUtils;
import cc.makepower.depandroid.R;

public class CompressImgActivity extends AppCompatActivity  {
    ImageView img_OldImg;
    ImageView img_NewImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compressimg);

         img_OldImg=findViewById(R.id.img_OldImg);
         img_NewImg=findViewById(R.id.img_NewImg);


     Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Pictures/"+"12345.jpg");
        Log.i("wechat", "压缩前图片的大小" + (bitmap.getByteCount() / 1024) + "KB宽度为"
                + bitmap.getWidth() + "高度为" + bitmap.getHeight());
        img_OldImg.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Pictures/"+"12345.jpg"));





        Bitmap newBitmap= CompressUtils.compress(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Pictures/"+"12345.jpg");
        Log.i("wechat", "压缩后图片的大小" + (newBitmap.getByteCount() / 1024) + "KB宽度为"
                + newBitmap.getWidth() + "高度为" + newBitmap.getHeight());
        img_NewImg.setImageBitmap(newBitmap);
    }

}
