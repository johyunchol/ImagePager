package com.kkensu.www.imagepager.model

import java.io.Serializable

data class ImageData(
        var idx: Int? = 0,
        var image: Any? = null,
        var isSelected: Boolean? = false
) : Serializable