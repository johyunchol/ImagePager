package com.kkensu.www.imagepager.event;


import com.kkensu.www.imagepager.model.ImageData;

public class PageEvent {
    private ImageData imageData;

    public PageEvent(ImageData imageData) {
        this.imageData = imageData;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }
}
