package com.kkensu.www.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kkensu.www.imagepager.ImagePagerActivity;
import com.kkensu.www.imagepager.PhotoViewPager;
import com.kkensu.www.imagepager.model.ImageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ImageInfo> imageInfoList;
    private Button button;
    private PhotoViewPager viewPager;
    private ImagePageAdapter imagePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initResources();
    }

    private void initResources() {
        imageInfoList = new ArrayList<>();
        setItems();

        viewPager = findViewById(R.id.viewPager);
        imagePageAdapter = new ImagePageAdapter(getSupportFragmentManager(), imageInfoList);
        viewPager.setAdapter(imagePageAdapter);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImagePagerActivity.class);
                intent.putExtra(ImagePagerActivity.ARG_IMAGE_LIST, (Serializable) imageInfoList);
                startActivity(intent);
            }
        });
    }

    private void setItems() {
        setItem("http://img.hani.co.kr/imgdb/resize/2018/0328/00500164_20180328.JPG");
        setItem("http://pds.joins.com//news/component/htmlphoto_mmdata/201710/09/c04f1f6e-8bae-4e74-8e76-b76ab877b29b.jpg");
    }

    private void setItem(String imageUrl) {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setImgUrl(imageUrl);
        imageInfoList.add(imageInfo);
    }
}
