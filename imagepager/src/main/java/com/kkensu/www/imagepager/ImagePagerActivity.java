package com.kkensu.www.imagepager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.kkensu.www.imagepager.base.BaseActivity;
import com.kkensu.www.imagepager.event.ImageMenuLayoutShowHideEvent;
import com.kkensu.www.imagepager.event.MoreButtonEvent;
import com.kkensu.www.imagepager.model.ImageInfo;
import com.kkensu.www.imagepager.util.DownloadUtil;
import com.kkensu.www.imagepager.util.Util;

import java.util.List;


public class ImagePagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    public static final String ARG_IMAGE_LIST = "ARG_IMAGE_LIST";
    public static final String ARG_POSITION = "ARG_POSITION";

    private ViewGroup container;

    private ImageView btnBack;
    private ImageView btnMore;
    private List<ImageInfo> imageInfoList;
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

        initData();
        initResources();

        btnBack = findViewById(R.id.btnBack);
        btnMore = findViewById(R.id.btnMore);

//        setToggleButton(imageModelRetro.getList().size());

        viewPager = findViewById(R.id.viewPager);
        imagePageAdapter = new ImagePageAdapter(getSupportFragmentManager(), imageInfoList);
        viewPager.setAdapter(imagePageAdapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(position);

        layout_top = findViewById(R.id.layoutTop);
        layout_circle = findViewById(R.id.layoutCircle);
    }


    private void initData() {
        if (Util.hasArg(activity, ARG_IMAGE_LIST)) {
            imageInfoList = Util.getSerializableArg(activity, ARG_IMAGE_LIST);
        }

        if (Util.hasArg(activity, ARG_POSITION)) {
            position = Util.getIntegerArg(activity, ARG_POSITION);
        }
    }

    private void initResources() {
        Util.onClick(activity, new int[]{R.id.btnBack, R.id.btnMore}, new OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.btnBack) {
                    finish();
                } else if (i == R.id.btnMore) {
                    popupMenu();
                }
            }
        });
    }

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
                    DownloadUtil.downloadData(ImagePagerActivity.this, imageInfoList.get(viewPager.getCurrentItem()).getImgUrl());

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

//    public abstract static class BaseBuilder<T extends BaseBuilder> {
//
//        public int previewMaxCount = 25;
//        public Drawable cameraTileDrawable;
//        public Drawable galleryTileDrawable;
//        public Drawable selectedForegroundDrawable;
//        public ImageProvider imageProvider;
//        public boolean showCamera = true;
//        public boolean showGallery = true;
//        public int cameraTileBackgroundResId = R.color.tedbottompicker_camera;
//        public int galleryTileBackgroundResId = R.color.tedbottompicker_gallery;
//        @MediaType
//        public int mediaType = MediaType.IMAGE;
//        protected BaseActivity activity;
//        OnImageSelectedListener onImageSelectedListener;
//        OnMultiImageSelectedListener onMultiImageSelectedListener;
//        OnErrorListener onErrorListener;
//        private String title;
//        private boolean showTitle = true;
//        private List<Uri> selectedUriList;
//        private Uri selectedUri;
//        private Drawable deSelectIconDrawable;
//        private int spacing = 1;
//        private boolean includeEdgeSpacing = false;
//        private int peekHeight = -1;
//        private int titleBackgroundResId;
//        private int selectMaxCount = Integer.MAX_VALUE;
//        private int selectMinCount = 0;
//        private String completeButtonText;
//        private String emptySelectionText;
//        private String selectMaxCountErrorText;
//        private String selectMinCountErrorText;
//
//        public BaseBuilder(@NonNull FragmentActivity fragmentActivity) {
//
//            this.fragmentActivity = fragmentActivity;
//
//            setCameraTile(R.drawable.ic_camera);
//            setGalleryTile(R.drawable.ic_gallery);
//            setSpacingResId(R.dimen.tedbottompicker_grid_layout_margin);
//        }
//
//        public T setCameraTile(@DrawableRes int cameraTileResId) {
//            setCameraTile(ContextCompat.getDrawable(fragmentActivity, cameraTileResId));
//            return (T) this;
//        }
//
//        public BaseBuilder<T> setGalleryTile(@DrawableRes int galleryTileResId) {
//            setGalleryTile(ContextCompat.getDrawable(fragmentActivity, galleryTileResId));
//            return this;
//        }
//
//        public T setSpacingResId(@DimenRes int dimenResId) {
//            this.spacing = fragmentActivity.getResources().getDimensionPixelSize(dimenResId);
//            return (T) this;
//        }
//
//        public T setCameraTile(Drawable cameraTileDrawable) {
//            this.cameraTileDrawable = cameraTileDrawable;
//            return (T) this;
//        }
//
//        public T setGalleryTile(Drawable galleryTileDrawable) {
//            this.galleryTileDrawable = galleryTileDrawable;
//            return (T) this;
//        }
//
//        public T setDeSelectIcon(@DrawableRes int deSelectIconResId) {
//            setDeSelectIcon(ContextCompat.getDrawable(fragmentActivity, deSelectIconResId));
//            return (T) this;
//        }
//
//        public T setDeSelectIcon(Drawable deSelectIconDrawable) {
//            this.deSelectIconDrawable = deSelectIconDrawable;
//            return (T) this;
//        }
//
//        public T setSelectedForeground(@DrawableRes int selectedForegroundResId) {
//            setSelectedForeground(ContextCompat.getDrawable(fragmentActivity, selectedForegroundResId));
//            return (T) this;
//        }
//
//        public T setSelectedForeground(Drawable selectedForegroundDrawable) {
//            this.selectedForegroundDrawable = selectedForegroundDrawable;
//            return (T) this;
//        }
//
//        public T setPreviewMaxCount(int previewMaxCount) {
//            this.previewMaxCount = previewMaxCount;
//            return (T) this;
//        }
//
//        public T setSelectMaxCount(int selectMaxCount) {
//            this.selectMaxCount = selectMaxCount;
//            return (T) this;
//        }
//
//        public T setSelectMinCount(int selectMinCount) {
//            this.selectMinCount = selectMinCount;
//            return (T) this;
//        }
//
//        public T setOnImageSelectedListener(OnImageSelectedListener onImageSelectedListener) {
//            this.onImageSelectedListener = onImageSelectedListener;
//            return (T) this;
//        }
//
//        public T setOnMultiImageSelectedListener(OnMultiImageSelectedListener onMultiImageSelectedListener) {
//            this.onMultiImageSelectedListener = onMultiImageSelectedListener;
//            return (T) this;
//        }
//
//        public T setOnErrorListener(OnErrorListener onErrorListener) {
//            this.onErrorListener = onErrorListener;
//            return (T) this;
//        }
//
//        public T showCameraTile(boolean showCamera) {
//            this.showCamera = showCamera;
//            return (T) this;
//        }
//
//        public T showGalleryTile(boolean showGallery) {
//            this.showGallery = showGallery;
//            return (T) this;
//        }
//
//        public T setSpacing(int spacing) {
//            this.spacing = spacing;
//            return (T) this;
//        }
//
//        public T setIncludeEdgeSpacing(boolean includeEdgeSpacing) {
//            this.includeEdgeSpacing = includeEdgeSpacing;
//            return (T) this;
//        }
//
//        public T setPeekHeight(int peekHeight) {
//            this.peekHeight = peekHeight;
//            return (T) this;
//        }
//
//        public T setPeekHeightResId(@DimenRes int dimenResId) {
//            this.peekHeight = fragmentActivity.getResources().getDimensionPixelSize(dimenResId);
//            return (T) this;
//        }
//
//        public T setCameraTileBackgroundResId(@ColorRes int colorResId) {
//            this.cameraTileBackgroundResId = colorResId;
//            return (T) this;
//        }
//
//        public T setGalleryTileBackgroundResId(@ColorRes int colorResId) {
//            this.galleryTileBackgroundResId = colorResId;
//            return (T) this;
//        }
//
//        public T setTitle(String title) {
//            this.title = title;
//            return (T) this;
//        }
//
//        public T setTitle(@StringRes int stringResId) {
//            this.title = fragmentActivity.getResources().getString(stringResId);
//            return (T) this;
//        }
//
//        public T showTitle(boolean showTitle) {
//            this.showTitle = showTitle;
//            return (T) this;
//        }
//
//        public T setCompleteButtonText(String completeButtonText) {
//            this.completeButtonText = completeButtonText;
//            return (T) this;
//        }
//
//        public T setCompleteButtonText(@StringRes int completeButtonResId) {
//            this.completeButtonText = fragmentActivity.getResources().getString(completeButtonResId);
//            return (T) this;
//        }
//
//        public T setEmptySelectionText(String emptySelectionText) {
//            this.emptySelectionText = emptySelectionText;
//            return (T) this;
//        }
//
//        public T setEmptySelectionText(@StringRes int emptySelectionResId) {
//            this.emptySelectionText = fragmentActivity.getResources().getString(emptySelectionResId);
//            return (T) this;
//        }
//
//        public T setSelectMaxCountErrorText(String selectMaxCountErrorText) {
//            this.selectMaxCountErrorText = selectMaxCountErrorText;
//            return (T) this;
//        }
//
//        public T setSelectMaxCountErrorText(@StringRes int selectMaxCountErrorResId) {
//            this.selectMaxCountErrorText = fragmentActivity.getResources().getString(selectMaxCountErrorResId);
//            return (T) this;
//        }
//
//        public T setSelectMinCountErrorText(String selectMinCountErrorText) {
//            this.selectMinCountErrorText = selectMinCountErrorText;
//            return (T) this;
//        }
//
//        public T setSelectMinCountErrorText(@StringRes int selectMinCountErrorResId) {
//            this.selectMinCountErrorText = fragmentActivity.getResources().getString(selectMinCountErrorResId);
//            return (T) this;
//        }
//
//        public T setTitleBackgroundResId(@ColorRes int colorResId) {
//            this.titleBackgroundResId = colorResId;
//            return (T) this;
//        }
//
//        public T setImageProvider(ImageProvider imageProvider) {
//            this.imageProvider = imageProvider;
//            return (T) this;
//        }
//
//        public T setSelectedUriList(List<Uri> selectedUriList) {
//            this.selectedUriList = selectedUriList;
//            return (T) this;
//        }
//
//        public T setSelectedUri(Uri selectedUri) {
//            this.selectedUri = selectedUri;
//            return (T) this;
//        }
//
//        public T showVideoMedia() {
//            this.mediaType = MediaType.VIDEO;
//            return (T) this;
//        }
//
//        public TedBottomSheetDialogFragment create() {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
//                    && ContextCompat.checkSelfPermission(fragmentActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                throw new RuntimeException("Missing required WRITE_EXTERNAL_STORAGE permission. Did you remember to request it first?");
//            }
//
//            if (onImageSelectedListener == null && onMultiImageSelectedListener == null) {
//                throw new RuntimeException("You have to use setOnImageSelectedListener() or setOnMultiImageSelectedListener() for receive selected Uri");
//            }
//
//            TedBottomSheetDialogFragment customBottomSheetDialogFragment = new TedBottomSheetDialogFragment();
//            customBottomSheetDialogFragment.builder = (T) this;
//            return customBottomSheetDialogFragment;
//        }
//
//        @Retention(RetentionPolicy.SOURCE)
//        @IntDef({MediaType.IMAGE, MediaType.VIDEO})
//        public @interface MediaType {
//            int IMAGE = 1;
//            int VIDEO = 2;
//        }
//
//
//    }
//
//    public void show() {
//        startActivity(new Intent(this, ImagePagerActivity.class));
//    }
}
