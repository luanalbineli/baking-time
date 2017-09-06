package com.udacity.bakingtime.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.udacity.bakingtime.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseRecyclerViewFragment<TView> extends BaseFragment<TView> {
    @BindView(R.id.rvFragment)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureRecyclerView(mRecyclerView);
    }

    protected void useLinearLayoutManager() {
        checkRecyclerViewInitialized();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    protected GridLayoutManager useGridLayoutManager(int numberOfColumns) {
        checkRecyclerViewInitialized();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mRecyclerView.getContext(), numberOfColumns, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        return gridLayoutManager;
    }

    protected void useDividerItemDecoration() {
        checkRecyclerViewInitialized();
        if (mRecyclerView.getLayoutManager() == null) {
            throw new UnsupportedOperationException("You must call this method after set the Layout Manager on the RecyclerView.");
        }

        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);
        } else {
            Timber.w("Your Layout Manager is not a LinearLayoutManager");
        }
    }

    private void checkRecyclerViewInitialized() {
        if (mRecyclerView == null) {
            throw new UnsupportedOperationException("You must call this method after the RecyclerView has initialized (on configureRecyclerView() or on/after onViewCreated().");
        }
    }

    protected abstract void configureRecyclerView(RecyclerView recyclerView);
}
