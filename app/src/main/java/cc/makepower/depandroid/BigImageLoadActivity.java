package cc.makepower.depandroid;

import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class BigImageLoadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitimage);
        BigImgView bitImageView=findViewById(R.id.bitImageView);
        try {
            InputStream inputStream=getAssets().open("test.JPEG");
            bitImageView.setImage(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
