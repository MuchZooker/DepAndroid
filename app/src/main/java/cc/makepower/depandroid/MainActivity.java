package cc.makepower.depandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cc.makepower.depandroid.acti.BigImageLoadActivity;
import cc.makepower.depandroid.acti.CompressImgActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_bigImageView = findViewById(R.id.btn_bigImageView);
        Button btn_CompressImg = findViewById(R.id.btn_CompressImg);

        btn_bigImageView.setOnClickListener(this);
        btn_CompressImg.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bigImageView:
                startActivity(new Intent(MainActivity.this, BigImageLoadActivity.class));
                break;
            case R.id.btn_CompressImg:
                startActivity(new Intent(MainActivity.this, CompressImgActivity.class));
                break;
        }
    }
}
