package com.albineli.udacity.popularmovies.ui.recyclerview;

import android.view.View;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.enums.RequestStatusDescriptor;
import com.albineli.udacity.popularmovies.ui.RequestStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GridStatusViewHolder extends CustomRecyclerViewHolder {
    private ITryAgainClick mTryAgainClick;

    @BindView(R.id.rsvRequestStatus)
    RequestStatusView mRequestStatusView;

    public GridStatusViewHolder(View itemView, ITryAgainClick tryAgainClick) {
        super(itemView);

        mTryAgainClick = tryAgainClick;

        ButterKnife.bind(this, itemView);
    }

    public void bind(@RequestStatusDescriptor.RequestStatus int requestStatus, int numberOfItems) {
        mRequestStatusView.setRequestStatus(requestStatus, numberOfItems == 0);
        //mRequestStatusView.seton
    }

    public interface ITryAgainClick {
        void tryAgain();
    }
}
