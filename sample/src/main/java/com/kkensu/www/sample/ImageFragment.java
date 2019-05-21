package com.kkensu.www.sample;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kkensu.www.imagepager.ImagePagerActivity;
import com.kkensu.www.imagepager.R;
import com.kkensu.www.imagepager.model.ImageInfo;

import java.io.Serializable;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageFragment extends Fragment {
    private List<ImageInfo> imageInfoList;
    private int position;

    private ImageView imageView;
    private ProgressBar progressBar;

    private PhotoViewAttacher mAttacher;

    public ImageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ImageFragment newInstance(List<ImageInfo> imageInfoList, int position) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putSerializable("MAIN", (Serializable) imageInfoList);
        args.putInt("POSITION", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageInfoList = (List<ImageInfo>) getArguments().getSerializable("MAIN");
            position = getArguments().getInt("POSITION");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_imageview, container, false);

        imageView = view.findViewById(R.id.imageContent);
        progressBar = view.findViewById(R.id.progressBar);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this)
                .load(imageInfoList.get(position).getImgUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        if (mAttacher != null) {
                            mAttacher.update();
                        } else {
                            mAttacher = new PhotoViewAttacher(imageView);
                            mAttacher.update();

                            mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
//                                    EventBus.getDefault().post(new MoreButtonEvent());
                                    return false;
                                }
                            });

                            mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                                @Override
                                public void onPhotoTap(View view, float x, float y) {
                                    Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
                                    intent.putExtra(ImagePagerActivity.ARG_IMAGE_LIST, (Serializable) imageInfoList);
                                    startActivity(intent);
                                }

                                @Override
                                public void onOutsidePhotoTap() {

                                }
                            });
                        }
                        return false;
                    }
                })
                .apply(options)
                .into(imageView);

        return view;
    }
}
