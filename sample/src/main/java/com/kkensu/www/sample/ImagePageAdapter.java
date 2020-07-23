package com.kkensu.www.sample;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.kkensu.www.imagepager.model.ImageData;

import java.util.ArrayList;

/**
 * Created by urchin on 07/09/16.
 */
public class ImagePageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<ImageData> ImageDataList;
    private Fragment fragment;
    private int size;

    public ImagePageAdapter(FragmentManager fm, ArrayList<ImageData> ImageDataList) {
        super(fm);
        this.ImageDataList = ImageDataList;
        this.size = ImageDataList.size();
    }

    @Override
    public Fragment getItem(int position) {
        fragment = ImageFragment.newInstance(ImageDataList, position);
        return fragment;
    }

    @Override
    public int getCount() {
        return ImageDataList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        if (object instanceof android.app.Fragment) {
            fragment = (Fragment) object;
            FragmentManager fragmentManager = fragment.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }
}
