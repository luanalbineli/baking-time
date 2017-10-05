package com.udacity.bakingtime.util;

import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;

public class UIUtil {
    private UIUtil() {}

    public static void paintDrawable(Drawable drawable, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setTint(color);
        } else {
            drawable.setColorFilter(new LightingColorFilter(color, color));
        }
    }
}
