package com.kkensu.www.library.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Fragment 쉽게 만들기 위한 Builder 패턴
 */
@SuppressWarnings("unused")
public class FragmentBuilder<T extends Fragment> {
    T fragment;

    public FragmentBuilder(T fragment) {
        this.fragment = fragment;
    }

    public FragmentBuilder<T> arg(Bundle bundle) {
        fragment.setArguments(bundle);
        return this;
    }

    public FragmentBuilder<T> arg(BundleBuilder builder) {
        fragment.setArguments(builder.build());
        return this;
    }

    public T build() {
        return fragment;
    }
}
