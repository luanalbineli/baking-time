package com.albineli.udacity.popularmovies.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;

public class RequestStatusDescriptor {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LOADING, ERROR, EMPTY, HIDDEN})
    public @interface RequestStatus {}

    public static final int LOADING = 0;
    public static final int ERROR = 1;
    public static final int EMPTY = 2;
    public static final int HIDDEN = 3;

    private RequestStatusDescriptor() {}
}
