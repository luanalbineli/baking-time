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
    private ITryAgainClick mTryAgainClick;

    @BindView(R.id.rsvRequestStatus)
    RequestStatusView mRequestStatusView;

    public GridStatusViewHolder(View itemView, ITryAgainClick tryAgainClick) {
        super(itemView);

        mTryAgainClick = tryAgainClick;

        ButterKnife.bind(this, itemView);
    }

    public void bind(@RequestStatusDescriptor.RequestStatus int requestStatus, int numberOfItems, @StringRes int emptyMessageResId) {
        mRequestStatusView.setEmptyMessage(emptyMessageResId);
        mRequestStatusView.setRequestStatus(requestStatus, numberOfItems == 0);
        Timber.i("REDRAWING GRID STATUS: " + requestStatus);
    }

    public interface ITryAgainClick {
        void tryAgain();
    }
}
