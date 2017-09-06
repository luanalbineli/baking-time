package com.udacity.bakingtime.ui.recyclerview;

import android.support.annotation.StringRes;
import android.view.View;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.enums.RequestStatusDescriptor;
import com.udacity.bakingtime.ui.RequestStatusView;

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
