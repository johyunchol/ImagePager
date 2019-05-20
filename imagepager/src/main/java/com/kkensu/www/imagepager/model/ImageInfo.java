package com.kkensu.www.imagepager.model;

import java.io.Serializable;

/**
 * Created by JoHyunChol on 2016-10-15.
 */

public class ImageInfo implements Serializable {
    String img_url;

    public String getImgUrl() {
        return img_url;
    }

    public void setImgUrl(String img_url) {
        this.img_url = img_url;
    }
}
