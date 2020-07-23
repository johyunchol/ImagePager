package com.kkensu.www.sample;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kkensu.www.imagepager.PhotoViewPager;
import com.kkensu.www.imagepager.model.ImageData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageData> imageInfoList;
    private Button button;
    private PhotoViewPager viewPager;
    private ImagePageAdapter imagePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initResources();

//        TedPermission.with()
    }

    private void initResources() {
        imageInfoList = new ArrayList<>();
        setItems();

        viewPager = findViewById(R.id.viewPager);
        imagePageAdapter = new ImagePageAdapter(getSupportFragmentManager(), imageInfoList);
        viewPager.setAdapter(imagePageAdapter);
    }

    private void setItems() {
        setItem("http://img.hani.co.kr/imgdb/resize/2018/0328/00500164_20180328.JPG");
        setItem("http://pds.joins.com//news/component/htmlphoto_mmdata/201710/09/c04f1f6e-8bae-4e74-8e76-b76ab877b29b.jpg");
        setItem(R.drawable.ic_launcher_foreground);
    }

    private void setItem(Object imageUrl) {
        ImageData imageInfo = new ImageData();
        imageInfo.setImage(imageUrl);
        imageInfoList.add(imageInfo);
    }
}
