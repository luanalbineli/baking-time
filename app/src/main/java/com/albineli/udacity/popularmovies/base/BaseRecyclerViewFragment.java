package com.albineli.udacity.popularmovies.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albineli.udacity.popularmovies.R;

import java.security.InvalidParameterException;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    protected abstract void configureRecyclerView(RecyclerView recyclerView);
}
