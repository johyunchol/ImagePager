package com.kkensu.www.imagepager.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kkensu.www.imagepager.R;
import com.kkensu.www.imagepager.event.PageEvent;
import com.kkensu.www.imagepager.model.ImageData;
import com.kkensu.www.imagepager.view.SquareImageView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_DIVIDER = 1;

    private List<InnerItem> listItem = new ArrayList<>();

    private class InnerItem {
        public int type;
        public Object item;

        public InnerItem(int type, Object obj) {
            this.type = type;
            this.item = obj;
        }
    }

    private LayoutInflater inflater;
    private Context context;
    private List<ImageData> list;

    private SimpleDateFormat dateformat;
    private SimpleDateFormat cardformat;

    public ImageListAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dateformat = new SimpleDateFormat("yyyy-mm-dd");
        cardformat = new SimpleDateFormat("mm/yy");
    }

    public void setItem(List<ImageData> list) {
        this.list = list;
        listItem.clear();

        if (list != null) {
            for (ImageData ImageData : list) {
                listItem.add(new InnerItem(TYPE_IMAGE, ImageData));
                listItem.add(new InnerItem(TYPE_DIVIDER, ImageData));
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case TYPE_IMAGE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_viewpager_bottom_item, parent, false);
                holder = new ImageViewHolder(v);
                break;
            case TYPE_DIVIDER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_viewpager_bottom_divider, parent, false);
                holder = new DividerViewHolder(v);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case TYPE_IMAGE:
                ((ImageViewHolder) holder).setItem((ImageData) listItem.get(position).item);
                break;
            case TYPE_DIVIDER:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listItem.get(position).type;
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        View v;

        public DividerViewHolder(@NonNull View v) {
            super(v);
            this.v = v;
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        View v;
        SquareImageView imageView;
        ImageView imgSelected;

        public ImageViewHolder(@NonNull View v) {
            super(v);
            this.v = v;
            imageView = v.findViewById(R.id.imageView);
            imgSelected = v.findViewById(R.id.imgSelected);
        }

        public void setItem(final ImageData item) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(context)
                    .load(item.getImage())
                    .thumbnail(0.2f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (resource instanceof GifDrawable) {
                                ((GifDrawable) resource).setLoopCount(1);
                            }

                            if (isFirstResource) return false;

                            return false;
                        }
                    })
                    .apply(options)
                    .into(imageView);

            if (item.isSelected()) {
                imgSelected.setVisibility(View.VISIBLE);
            } else {
                imgSelected.setVisibility(View.GONE);
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    allUnSelect();
                    item.setSelected(true);
                    EventBus.getDefault().post(new PageEvent(item));

                    notifyDataSetChanged();
                }
            });
        }
    }

    private void allUnSelect() {
        for (InnerItem innerItem : listItem) {
            if (innerItem.type == TYPE_IMAGE) {
                ((ImageData) innerItem.item).setSelected(false);
            }
        }
    }

}

