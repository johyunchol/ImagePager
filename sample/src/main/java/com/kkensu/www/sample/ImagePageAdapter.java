package com.kkensu.www.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.kkensu.www.imagepager.fragment.PhotoViewFragment;
import com.kkensu.www.imagepager.model.ImageInfo;

import java.util.List;

/**
 * Created by urchin on 07/09/16.
 */
public class ImagePageAdapter extends FragmentStatePagerAdapter {

    private List<ImageInfo> imageInfoList;
    private Fragment fragment;
    private int size;

    public ImagePageAdapter(FragmentManager fm, List<ImageInfo> imageInfoList) {
        super(fm);
        this.imageInfoList = imageInfoList;
        this.size = imageInfoList.size();
    }

    @Override
    public Fragment getItem(int position) {
        fragment = new ImageFragment().newInstance(imageInfoList, position);
        return fragment;
    }

    @Override
    public int getCount() {
        return imageInfoList.size();
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
