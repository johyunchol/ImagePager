package com.kkensu.www.imagepager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.kkensu.www.imagepager.fragment.PhotoViewFragment;
import com.kkensu.www.imagepager.model.ImageModel;

import java.util.List;

/**
 * Created by urchin on 07/09/16.
 */
public class ImagePageAdapter extends FragmentStatePagerAdapter {

//    private ImageModelRetro imageModelRetro;
    private List<ImageModel> imageModelList;
    private PhotoViewFragment fragment;
    private int size;

    public ImagePageAdapter(FragmentManager fm, List<ImageModel> imageModelList) {
        super(fm);
        this.imageModelList = imageModelList;
        this.size = imageModelList.size();
    }

    @Override
    public Fragment getItem(int position) {
        fragment = new PhotoViewFragment().newInstance(imageModelList.get(position));
        return fragment;
    }

//    @Override
//    public Fragment getItem(int position) {
//        return FavoritesFragment.newInstance(position);
//    }

    @Override
    public int getCount() {
//        return Integer.MAX_VALUE;
        return imageModelList.size();
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
