package com.kkensu.www.imagepager;

import android.content.Context;

public class ImagePager {
    public static final String TAG = ImagePager.class.getSimpleName();

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder extends ImagePagerBuilder<Builder> {

        private Builder(Context context) {
            super(context);
        }

        public void start() {
            startImagePager();
        }

    }
}
