package com.albineli.udacity.popularmovies.ui.recyclerview;

import android.support.annotation.StringRes;
import android.view.View;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.enums.RequestStatusDescriptor;
import com.albineli.udacity.popularmovies.ui.RequestStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class GridStatusViewHolder extends CustomRecyclerViewHolder {

    @BindView(R.id.rsvRequestStatus)
    RequestStatusView mRequestStatusView;

    GridStatusViewHolder(View itemView, RequestStatusView.ITryAgainClickListener tryAgainClick, @StringRes int emptyMessageResId) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        mRequestStatusView.setEmptyMessage(emptyMessageResId);
        mRequestStatusView.setTryAgainClickListener(tryAgainClick);
    }

    public void bind(@RequestStatusDescriptor.RequestStatus int requestStatus, int numberOfItems) {
        mRequestStatusView.setRequestStatus(requestStatus, numberOfItems == 0);
        Timber.i("REDRAWING GRID STATUS: " + requestStatus);
    }
}
