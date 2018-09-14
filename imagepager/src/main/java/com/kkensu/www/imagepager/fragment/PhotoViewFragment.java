package com.kkensu.www.imagepager.fragment;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kkensu.www.imagepager.R;
import com.kkensu.www.imagepager.event.ImageMenuLayoutShowHideEvent;
import com.kkensu.www.imagepager.event.MoreButtonEvent;
import com.kkensu.www.imagepager.model.ImageModel;

import org.greenrobot.eventbus.EventBus;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoViewFragment extends Fragment {
    private ImageModel imageModel;
    private ImageView imageView;
    private ProgressBar progressBar;

    private PhotoViewAttacher mAttacher;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    //private OnFragmentInteractionListener mListener;
    public PhotoViewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PhotoViewFragment newInstance(ImageModel imageModel) {
        PhotoViewFragment fragment = new PhotoViewFragment();
        Bundle args = new Bundle();
        args.putSerializable("MAIN", imageModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageModel = (ImageModel) getArguments().getSerializable("MAIN");
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
                .load(imageModel.getImgUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        if (mAttacher != null) {
                            mAttacher.update();
                        } else {
                            mAttacher = new PhotoViewAttacher(imageView);
                            mAttacher.update();

                            mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    EventBus.getDefault().post(new MoreButtonEvent());
                                    return false;
                                }
                            });

                            mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                                @Override
                                public void onPhotoTap(View view, float x, float y) {
                                    EventBus.getDefault().post(new ImageMenuLayoutShowHideEvent());
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


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
