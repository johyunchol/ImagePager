# ImagePager

[![Developed](https://img.shields.io/badge/Developed-JoHyunChol-green.svg?style=flat)](https://android-arsenal.com/details/1/7697)

[![ref](https://img.shields.io/static/v1.svg?label=AndroidStudio&message=4.0&color=blueviolet)](https://developer.android.com/studio) [![ref](https://img.shields.io/badge/platform-android-lightgrey.svg)](https://developer.android.com/) [![ref](https://img.shields.io/badge/Kotlin-1.3.72-orange.svg)](https://kotlinlang.org/) [![ref](https://img.shields.io/badge/gradle--wrapper-gradle--6.1.1--all-yellowgreen.svg)](https://gradle.org/) [![ref](https://img.shields.io/badge/gradle-4.0.0-blue.svg)](https://gradle.org/) [![ref](https://travis-ci.org/perelandrax/ReactorKit.svg?branch=master)](https://travis-ci.org/johyunchol/ImagePager) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

ImagePager는 ViewPager2를 이용한 이미지 페이징 효과를 주기 위한 라이브러리 입니다.

### Demo

<img src="https://github.com/johyunchol/ImagePager/blob/master/assets/sample_video_01.gif?raw=true" width="40%">


## Quick Setup

###  Gradle

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.johyunchol:imagepager:x.y.z'
}

```

</br></br>

## How to use

### 1.Start Imagepager


```
ImagePager.with(MainActivity.this)
        .setTitle(R.string.app_name)
        .setIsShowBottomView(true)
        .setIsShowPosition(true)
        .setImageList(list)
        .start();
```

## Customize

- You can customize what you want

