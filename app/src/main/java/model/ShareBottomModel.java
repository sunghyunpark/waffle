package model;

import android.support.annotation.DrawableRes;

/**
 * Created by SungHyun on 2017-11-08.
 */

public class ShareBottomModel {
    public String title;
    public int iconRes;

    public ShareBottomModel(String title, @DrawableRes int iconRes) {
        this.title = title;
        this.iconRes = iconRes;
    }
}
