package com.kkensu.www.sample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kkensu.www.imagepager.ImagePager;
import com.kkensu.www.imagepager.PhotoViewPager;
import com.kkensu.www.imagepager.model.ImageData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageData> imageList = new ArrayList<>();

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initResources();
    }

    private void initData() {
        initItems();
    }

    private void initResources() {
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this)
                .load(imageList.get(0).getImage())
                .apply(options)
                .into(imageView);

        textView.setText("+ " + imageList.size());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePager.with(MainActivity.this)
                        .setImageList(imageList)
                        .setTitle("이미지 페이징")
                        .setIsShowBottomView(true)
                        .setIsShowPosition(true)
                        .start();
            }
        });
    }

    private void initItems() {
        setItem("http://img.hani.co.kr/imgdb/resize/2018/0328/00500164_20180328.JPG");
        setItem("http://pds.joins.com//news/component/htmlphoto_mmdata/201710/09/c04f1f6e-8bae-4e74-8e76-b76ab877b29b.jpg");
        setItem(R.drawable.ic_launcher_foreground);
    }

    private void setItem(Object imageUrl) {
        ImageData imageInfo = new ImageData();
        imageInfo.setImage(imageUrl);
        imageList.add(imageInfo);
    }
}
