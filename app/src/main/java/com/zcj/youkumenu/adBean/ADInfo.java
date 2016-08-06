package com.zcj.youkumenu.adBean;


public class ADInfo {
    private int iconResId;
    private String info;

    public ADInfo(int iconResId, String info) {
        this.iconResId = iconResId;
        this.info = info;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
