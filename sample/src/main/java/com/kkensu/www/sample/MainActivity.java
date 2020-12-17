package com.kkensu.www.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kkensu.www.imagepager.ImagePager;
import com.kkensu.www.imagepager.ImagePagerActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Object> imageList = new ArrayList<>();
    private List<Object> thumbnailList = new ArrayList<>();

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
        initImageList();
        initThumbnailList();
    }

    private void initResources() {
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this)
                .load(imageList.get(0))
                .apply(options)
                .into(imageView);

        textView.setText("+ " + imageList.size());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePager.with(MainActivity.this)
                        .setImageList(imageList)
//                        .setThumbnailList(thumbnailList)
                        .setTitle("이미지 페이징")
                        .setShowBottomImageViews(true)
                        .setShowPageNumber(true)
                        .setCloseType(ImagePagerActivity.CloseType.TYPE_CLOSE)
                        .start();
            }
        });
    }

    private void initImageList()
        setImage("http://img.hani.co.kr/imgdb/resize/2018/0328/00500164_20180328.JPG");
        setImage("http://pds.joins.com//news/component/htmlphoto_mmdata/201710/09/c04f1f6e-8bae-4e74-8e76-b76ab877b29b.jpg");
        setImage(R.drawable.ic_launcher_foreground);
        setImage("http://img.hani.co.kr/imgdb/resize/2018/0328/00500164_20180328.JPG");
        setImage("http://pds.joins.com//news/component/htmlphoto_mmdata/201710/09/c04f1f6e-8bae-4e74-8e76-b76ab877b29b.jpg");
        setImage(R.drawable.ic_launcher_foreground);
        setImage("http://img.hani.co.kr/imgdb/resize/2018/0328/00500164_20180328.JPG");
        setImage("http://pds.joins.com//news/component/htmlphoto_mmdata/201710/09/c04f1f6e-8bae-4e74-8e76-b76ab877b29b.jpg");
        setImage(R.drawable.ic_launcher_foreground);
        setImage("http://img.hani.co.kr/imgdb/resize/2018/0328/00500164_20180328.JPG");
        setImage("http://pds.joins.com//news/component/htmlphoto_mmdata/201710/09/c04f1f6e-8bae-4e74-8e76-b76ab877b29b.jpg");
        setImage(R.drawable.ic_launcher_foreground);
        setImage("http://img.hani.co.kr/imgdb/resize/2018/0328/00500164_20180328.JPG");
        setImage("http://pds.joins.com//news/component/htmlphoto_mmdata/201710/09/c04f1f6e-8bae-4e74-8e76-b76ab877b29b.jpg");
        setImage(R.drawable.ic_launcher_foreground);
    }

    private void initThumbnailList() {
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
        setThumbnail("https://i.ytimg.com/vi/D8r4qAI0uh0/maxresdefault.jpg");
    }

    private void setImage(Object imageUrl) {
        imageList.add(imageUrl);
    }

    private void setThumbnail(Object thumbnailUrl) {
        thumbnailList.add(thumbnailUrl);
    }
}
