package com.kkensu.www.imagepager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.StringRes;

import com.kkensu.www.imagepager.model.ImageData;
import com.kkensu.www.imagepager.util.ObjectUtils;

import java.io.Serializable;
import java.util.List;

public abstract class ImagePagerBuilder<T extends ImagePagerBuilder> {

    private Context context;

    private List<ImageData> imageList;

    private CharSequence title;
    private int position;
    private boolean isShowPosition;
    private boolean isShowBottomView;
    private ImagePagerActivity.CloseType closeType;

    public ImagePagerBuilder(Context context) {
        this.context = context;
//        deniedCloseButtonText = context.getString(R.string.tedpermission_close);
//        rationaleConfirmText = context.getString(R.string.tedpermission_confirm);
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    protected void startImagePager() {
        if (imageList == null) {
            throw new IllegalArgumentException("You must setImageList() on ImagePager");
        } else if (ObjectUtils.isEmpty(imageList)) {
            throw new IllegalArgumentException("You must setPermissions() on ImagePager");
        }

        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.ARG_IMAGE_LIST, (Serializable) imageList);

        intent.putExtra(ImagePagerActivity.ARG_TITLE, title);
        intent.putExtra(ImagePagerActivity.ARG_POSITION, position);
        intent.putExtra(ImagePagerActivity.ARG_IS_SHOW_POSITION, isShowPosition);
        intent.putExtra(ImagePagerActivity.ARG_IS_SHOW_BOTTOM_VIEW, isShowBottomView);
        intent.putExtra(ImagePagerActivity.ARG_CLOSE_TYPE, ImagePagerActivity.CloseType.TYPE_BACK.getValue());

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);

        context.startActivity(intent);
//        ImagePagerActivity.startActivity(context, intent);
//        TedPermissionBase.setFirstRequest(context,permissions);
    }

    public T setImageList(List<ImageData> imageList) {
        this.imageList = imageList;
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

    public T setPosition(int position) {
        this.position = position;
        return (T) this;
    }

    public T setIsShowPosition(boolean isShowPosition) {
        this.isShowPosition = isShowPosition;
        return (T) this;
    }

    public T setIsShowBottomView(boolean isShowBottomView) {
        this.isShowBottomView = isShowBottomView;
        return (T) this;
    }
}
