package com.albineli.udacity.popularmovies.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.enums.RequestStatusDescriptor;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class RequestStatusView extends FrameLayout {
    @BindView(R.id.llRequestStatusError)
    LinearLayout mErrorContainerView;

    @BindView(R.id.btRequestStatusRetry)
    Button mTryAgainButton;

    @BindView(R.id.pbRequestStatusLoading)
    ProgressBar mLoadingProgressBar;

    @BindView(R.id.tvRequestStatusEmptyMessage)
    TextView mEmptyMessageTextView;
    private @RequestStatusDescriptor.RequestStatus
    int mRequestStatus;

    public RequestStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.request_status, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    public void setRequestStatus(int requestStatus, boolean matchParentHeight) {
        this.mRequestStatus = requestStatus;
        redrawStatus(matchParentHeight);
    }

    public void setEmptyMessage(@StringRes int messageResId) {
        mEmptyMessageTextView.setText(messageResId);
    }

    private void redrawStatus(boolean matchParentHeight) {
        toggleStatus(mRequestStatus == RequestStatusDescriptor.LOADING,
                mRequestStatus == RequestStatusDescriptor.ERROR,
                mRequestStatus == RequestStatusDescriptor.EMPTY,
                matchParentHeight ? MATCH_PARENT : WRAP_CONTENT);
    }

    private void toggleStatus(boolean loadingVisible, boolean errorVisible, boolean emptyMessageVisible, int viewHeight) {
        mLoadingProgressBar.setVisibility(loadingVisible ? View.VISIBLE : View.INVISIBLE);
        mErrorContainerView.setVisibility(errorVisible ? View.VISIBLE : View.INVISIBLE);
        mEmptyMessageTextView.setVisibility(emptyMessageVisible ? View.VISIBLE : View.INVISIBLE);

        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        layoutParams.height = viewHeight;
        this.setLayoutParams(layoutParams);
    }

    interface onTryAgainListener {
        void tryAgain();
    }
}
