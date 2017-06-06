package com.albineli.udacity.popularmovies.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class UIUtil {
    private UIUtil() {}

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics;
    }
}
