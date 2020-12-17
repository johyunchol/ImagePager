package com.kkensu.www.imagepager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.StringRes;

import com.kkensu.www.imagepager.model.ImageData;
import com.kkensu.www.imagepager.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class ImagePagerBuilder<T extends ImagePagerBuilder> {

    private Context context;

    private List<ImageData> imageList = new ArrayList<>();
    private List<ImageData> thumbnailList = new ArrayList<>();

    private CharSequence title;
    private int position = 0;
    private boolean isShowPosition = false;
    private boolean isShowBottomView = false;
    private ImagePagerActivity.CloseType closeType = ImagePagerActivity.CloseType.TYPE_BACK;

    public ImagePagerBuilder(Context context) {
        this.context = context;
    }

    protected void startImagePager() {
        if (imageList.size() <= 0) {
            Log.e("ImagePagerBuilder", "imageList size is 0");
            return;
        }

        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.ARG_IMAGE_LIST, (Serializable) imageList);
        if (thumbnailList.size() > 0) {
            intent.putExtra(ImagePagerActivity.ARG_THUMBNAIL_LIST, (Serializable) thumbnailList);
        }
        intent.putExtra(ImagePagerActivity.ARG_TITLE, title);
        intent.putExtra(ImagePagerActivity.ARG_POSITION, position);
        intent.putExtra(ImagePagerActivity.ARG_IS_SHOW_POSITION, isShowPosition);
        intent.putExtra(ImagePagerActivity.ARG_IS_SHOW_BOTTOM_VIEW, isShowBottomView);
        intent.putExtra(ImagePagerActivity.ARG_CLOSE_TYPE, closeType.getValue());

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);

        context.startActivity(intent);
    }

    public T setImageList(List<Object> imageList) {
        for (int i = 0; i < imageList.size(); i++) {
            ImageData imageData = new ImageData();
            imageData.setIdx(i + 1);
            imageData.setImage(imageList.get(i));
            this.imageList.add(imageData);
        }
        return (T) this;
    }

    public T setImageList(Object... imageList) {
        for (int i = 0; i < imageList.length; i++) {
            ImageData imageData = new ImageData();
            imageData.setIdx(i + 1);
            imageData.setImage(imageList[i]);
            this.imageList.add(imageData);
        }
        return (T) this;
    }

    public T setThumbnailList(List<Object> imageList) {
        for (int i = 0; i < imageList.size(); i++) {
            ImageData imageData = new ImageData();
            imageData.setIdx(i + 1);
            imageData.setImage(imageList.get(i));
            this.thumbnailList.add(imageData);
        }
        return (T) this;
    }

    public T setThumbnailList(Object... imageList) {
        for (int i = 0; i < imageList.length; i++) {
            ImageData imageData = new ImageData();
            imageData.setIdx(i + 1);
            imageData.setImage(imageList[i]);
            this.thumbnailList.add(imageData);
        }
        return (T) this;
    }

    @SuppressLint("ResourceType")
    private CharSequence getText(@StringRes int stringRes) {
        if (stringRes <= 0) {
            throw new IllegalArgumentException("Invalid String resource id");
        }
        return context.getText(stringRes);
    }

    public T setTitle(@StringRes int stringRes) {
        return setTitle(getText(stringRes));
    }

    public T setTitle(CharSequence title) {
        this.title = title;
        return (T) this;
    }

    public T setFirstVisiblePosition(int position) {
        this.position = position;
        return (T) this;
    }

    public T setShowPageNumber(boolean isShowPosition) {
        this.isShowPosition = isShowPosition;
        return (T) this;
    }

    public T setShowBottomImageViews(boolean isShowBottomView) {
        this.isShowBottomView = isShowBottomView;
        return (T) this;
    }

    public T setCloseType(ImagePagerActivity.CloseType closeType) {
        this.closeType = closeType;
        return (T) this;
    }
}
