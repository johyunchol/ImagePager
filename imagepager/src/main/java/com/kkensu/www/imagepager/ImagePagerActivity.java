package com.kkensu.www.imagepager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ToggleButton;

import com.kkensu.www.imagepager.adapter.ImagePageAdapter;
import com.kkensu.www.imagepager.event.ImageMenuLayoutShowHideEvent;
import com.kkensu.www.imagepager.event.MoreButtonEvent;
import com.kkensu.www.imagepager.model.ImageModel;
import com.kkensu.www.imagepager.util.DownloadUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class ImagePagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    public static final String ARG_IMAGE_MODEL_LIST = "ARG_IMAGE_MODEL_LIST";

    private ImageView btnBack;
    private ImageView btnMore;
    private List<ImageModel> imageModelList;
    private int position;
    private PhotoViewPager viewPager;
    private ImagePageAdapter imagePageAdapter;
    private ToggleButton toggleButtons[];

    private ViewGroup layout_top, layout_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        EventBus.getDefault().register(this);

        /* 상태바 색상변경 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));
        }

        Intent intent = getIntent();
        imageModelList = (List<ImageModel>) intent.getSerializableExtra(ARG_IMAGE_MODEL_LIST);
        position = intent.getExtras().getInt("POSITION");

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMore = findViewById(R.id.btnMore);
        btnMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu();
            }
        });

//        setToggleButton(imageModelRetro.getList().size());

        viewPager = findViewById(R.id.viewPager);
        imagePageAdapter = new ImagePageAdapter(getSupportFragmentManager(), imageModelList);
        viewPager.setAdapter(imagePageAdapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(position);

        layout_top = findViewById(R.id.layoutTop);
        layout_circle = findViewById(R.id.layoutCircle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showHideMenuLayout(ImageMenuLayoutShowHideEvent event) {
        if (layout_top.getVisibility() == View.GONE) {
            Animation showAnim = new AlphaAnimation(0, 1);
            showAnim.setDuration(500);
            layout_top.setVisibility(View.VISIBLE);
            layout_top.setAnimation(showAnim);
        } else {
            Animation hideAnim = new AlphaAnimation(1, 0);
            hideAnim.setDuration(500);
            layout_top.setVisibility(View.GONE);
            layout_top.setAnimation(hideAnim);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showPopupMenu(MoreButtonEvent moreButtonEvent) {
        if (layout_top.getVisibility() == View.GONE) {
            Animation showAnim = new AlphaAnimation(0, 1);
            showAnim.setDuration(500);
            layout_top.setVisibility(View.VISIBLE);
            layout_top.setAnimation(showAnim);
        }
        popupMenu();
    }

    private void popupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btnMore);
        getMenuInflater().inflate(R.menu.imageview_more_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.btnSave) {
                    DownloadUtil.downloadData(ImagePagerActivity.this, imageModelList.get(viewPager.getCurrentItem()).getImgUrl());

                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("POSITION", viewPager.getCurrentItem());
        setResult(RESULT_OK, intent);
        super.finish();
    }

    // view pager ToggleButton 을 동적으로 생성해준다.
    private void setToggleButton(int viewPagerNum) {
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.layoutCircle);

        // LayoutParams 객체 생성 및 값 설정
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(8 * dm.density);                                             // size = 10dp
        int marginSize = Math.round(2 * dm.density);                                        // marginSize = 2dp
        LinearLayout.LayoutParams btnLayoutLp = new LinearLayout.LayoutParams(size, size);  // layout_width = "10dp", layout_height = "10dp"
        btnLayoutLp.setMargins(marginSize, marginSize, marginSize, marginSize);             // layout_margin = "2dp"

        toggleButtons = new ToggleButton[viewPagerNum];

        for (int i = 0; i < viewPagerNum; i++) {
            toggleButtons[i] = new ToggleButton(this);
            toggleButtons[i].setBackgroundResource(R.drawable.common_btn_circle);
            toggleButtons[i].setEnabled(false);
            // Toggle 버튼 텍스트 삭제
            toggleButtons[i].setTextOff("");
            toggleButtons[i].setTextOn("");
            toggleButtons[i].setText("");

            viewGroup.addView(toggleButtons[i], btnLayoutLp);
        }
        if (viewPagerNum > 0) {
            toggleButtons[0].setChecked(true);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int point = (viewPager.getCurrentItem());
//        for (int i = 0; i < imageModelRetro.getList().size(); i++) {
//            if (i != point) {
//                toggleButtons[i].setChecked(false);
//            } else {
//                toggleButtons[i].setChecked(true);
//            }
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
