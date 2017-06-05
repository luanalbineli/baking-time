package com.albineli.udacity.popularmovies.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class NonScrollableLLM extends LinearLayoutManager {
    public NonScrollableLLM(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);

    }

    // it will always pass false to RecyclerView when calling "canScrollVertically()" method.
    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
