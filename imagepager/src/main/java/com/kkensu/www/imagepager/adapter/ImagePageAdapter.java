package com.kkensu.www.imagepager.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import android.view.ViewGroup;

import com.kkensu.www.imagepager.fragment.PhotoViewFragment;
import com.kkensu.www.imagepager.model.ImageInfo;

import java.util.List;

/**
 * Created by urchin on 07/09/16.
 */
public class ImagePageAdapter extends FragmentStatePagerAdapter {

//    private ImageModelRetro imageModelRetro;
    private List<ImageInfo> imageInfoList;
    private PhotoViewFragment fragment;
    private int size;

    public ImagePageAdapter(FragmentManager fm, List<ImageInfo> imageInfoList) {
        super(fm);
        this.imageInfoList = imageInfoList;
        this.size = imageInfoList.size();
    }

    @Override
    public Fragment getItem(int position) {
        fragment = new PhotoViewFragment().newInstance(imageInfoList.get(position));
        return fragment;
    }

//    @Override
//    public Fragment getItem(int position) {
//        return FavoritesFragment.newInstance(position);
//    }

    @Override
    public int getCount() {
//        return Integer.MAX_VALUE;
        return imageInfoList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        if (object instanceof android.app.Fragment) {
            fragment = (PhotoViewFragment) object;
            FragmentManager fragmentManager = fragment.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }
}
