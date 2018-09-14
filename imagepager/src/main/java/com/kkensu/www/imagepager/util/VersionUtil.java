package com.kkensu.www.imagepager.util;

public class VersionUtil {
    private int major;
    private int minor;
    private int hotfix;

    public VersionUtil(String versionName) {
        String[] version = versionName.split("\\.");

        if (version.length != 3) return;

        this.major = Integer.parseInt(version[0]);
        this.minor = Integer.parseInt(version[1]);
        this.hotfix = Integer.parseInt(version[2]);
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getHotfix() {
        return hotfix;
    }

    public void setHotfix(int hotfix) {
        this.hotfix = hotfix;
    }

    @Override
    public String toString() {
        return "VersionUtil{" +
                "major=" + major +
                ", minor=" + minor +
                ", hotfix=" + hotfix +
                '}';
    }
}
