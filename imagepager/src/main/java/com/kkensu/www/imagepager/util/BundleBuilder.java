package com.kkensu.www.imagepager.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Bundle을 쉽게 만들기 위한 Builder 패턴
 */
@SuppressWarnings("unused")
public class BundleBuilder {
    Bundle bundle = new Bundle();

    public BundleBuilder put(String key, String value) {
        bundle.putString(key, value);
        return this;
    }

    public BundleBuilder put(String key, boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }

    public Bundle build() {
        return bundle;
    }

    public BundleBuilder put(String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public BundleBuilder put(String key, Serializable value) {
        bundle.putSerializable(key, value);
        return this;
    }

    public BundleBuilder put(Bundle bundle) {
        this.bundle.putAll(bundle);
        return this;
    }

    public BundleBuilder put(String key, byte value) {
        bundle.putByte(key, value);
        return this;
    }

    public BundleBuilder put(String key, char value) {
        bundle.putChar(key, value);
        return this;
    }

    public BundleBuilder put(String key, short value) {
        bundle.putShort(key, value);
        return this;
    }

    public BundleBuilder put(String key, float value) {
        bundle.putFloat(key, value);
        return this;
    }

    public BundleBuilder put(String key, CharSequence value) {
        bundle.putCharSequence(key, value);
        return this;
    }

    public BundleBuilder put(String key, Parcelable value) {
        bundle.putParcelable(key, value);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BundleBuilder put(String key, Size value) {
        bundle.putSize(key, value);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BundleBuilder put(String key, SizeF value) {
        bundle.putSizeF(key, value);
        return this;
    }

    public BundleBuilder put(String key, Parcelable[] value) {
        bundle.putParcelableArray(key, value);
        return this;
    }

    public BundleBuilder putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(key, value);
        return this;
    }

    public BundleBuilder putSparseParcelableArray(String key, SparseArray<? extends Parcelable> value) {
        bundle.putSparseParcelableArray(key, value);
        return this;
    }

    public BundleBuilder putIntegerArrayList(String key, ArrayList<Integer> value) {
        bundle.putIntegerArrayList(key, value);
        return this;
    }

    public BundleBuilder putStringArrayList(String key, ArrayList<String> value) {
        bundle.putStringArrayList(key, value);
        return this;
    }

    public BundleBuilder put(String key, ArrayList<CharSequence> value) {
        bundle.putCharSequenceArrayList(key, value);
        return this;
    }


    public BundleBuilder put(String key, byte[] value) {
        bundle.putByteArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, short[] value) {
        bundle.putShortArray(key, value);

        return this;
    }

    public BundleBuilder put(String key, char[] value) {
        bundle.putCharArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, float[] value) {
        bundle.putFloatArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, CharSequence[] value) {
        bundle.putCharSequenceArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, Bundle value) {
        bundle.putBundle(key, value);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public BundleBuilder put(String key, IBinder value) {
        bundle.putBinder(key, value);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BundleBuilder put(PersistableBundle bundle) {
        this.bundle.putAll(bundle);
        return this;
    }


    public BundleBuilder put(String key, long value) {
        bundle.putLong(key, value);
        return this;
    }

    public BundleBuilder put(String key, double value) {
        bundle.putDouble(key, value);
        return this;
    }


    public BundleBuilder put(String key, boolean[] value) {
        bundle.putBooleanArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, int[] value) {
        bundle.putIntArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, long[] value) {
        bundle.putLongArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, double[] value) {
        bundle.putDoubleArray(key, value);
        return this;
    }

    public BundleBuilder put(String key, String[] value) {
        bundle.putStringArray(key, value);
        return this;
    }
}
