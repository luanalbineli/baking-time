package com.udacity.bakingtime.ui.recyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingtime.R;
import com.udacity.bakingtime.enums.RequestStatusDescriptor;
import com.udacity.bakingtime.ui.RequestStatusView;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomRecyclerViewAdapter<TItem, THolder extends CustomRecyclerViewHolder> extends RecyclerView.Adapter<CustomRecyclerViewHolder> {
    private final List<TItem> mItems;
    private IListRecyclerViewItemClick<TItem> mOnItemClickListener;
    private @RequestStatusDescriptor.RequestStatus int mRequestStatus = RequestStatusDescriptor.HIDDEN;

    private RequestStatusView.ITryAgainClickListener mTryAgainClickListener;
    private int mEmptyMessageResId = R.string.the_list_is_empty;

    protected CustomRecyclerViewAdapter() {
        this(new ArrayList<>());
    }

    protected CustomRecyclerViewAdapter(RequestStatusView.ITryAgainClickListener tryAgainClickListener) {
        this();

        mTryAgainClickListener = tryAgainClickListener;
    }

    protected CustomRecyclerViewAdapter(@StringRes int emptyMessageResId, RequestStatusView.ITryAgainClickListener tryAgainClickListener) {
        this();

        mEmptyMessageResId = emptyMessageResId;
        mTryAgainClickListener = tryAgainClickListener;
    }

    private CustomRecyclerViewAdapter(@NonNull List<TItem> items) {
        mItems = items;
    }

    public interface ViewType {
        int GRID_STATUS = 0;
        int ITEM = 1;
    }

    @Override
    public final CustomRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ViewType.GRID_STATUS) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_status, parent, false);
            return new GridStatusViewHolder(itemView, mTryAgainClickListener, mEmptyMessageResId);
        }
        return onCreateItemViewHolder(parent);
    }

    @Override
    public final void onBindViewHolder(final CustomRecyclerViewHolder holder, int position) {
        if (holder.getItemViewType() == ViewType.GRID_STATUS) {
            GridStatusViewHolder gridStatusViewHolder = (GridStatusViewHolder) holder;
            gridStatusViewHolder.bind(mRequestStatus, mItems.size());
            return;
        }

        //noinspection unchecked
        onBindItemViewHolder((THolder) holder, position);
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(holder.getAdapterPosition(), mItems.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public final int getItemCount() {
        return mItems.size() + (mRequestStatus == RequestStatusDescriptor.HIDDEN ? 0 : 1); // List status.
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mItems.size()) {
            return ViewType.GRID_STATUS;
        }
        return ViewType.ITEM;
    }

    protected TItem getItemByPosition(int position) {
        return mItems.get(position);
    }

    public final void addItems(List<TItem> items) {
        int itemCount = mItems.size();
        mItems.addAll(items);
        // Notify that the itens had changed.
        notifyItemRangeInserted(itemCount, items.size());
    }

    public final void replaceItems(List<TItem> items) {
        clearItems();

        mItems.addAll(items);
        notifyItemRangeInserted(0, items.size());
    }

    public final void clearItems() {
        int itemCount = mItems.size();
        if (itemCount > 0) {
            mItems.clear();
            notifyItemRangeRemoved(0, itemCount);
        }
    }

    public final void removeItemByIndex(int index) {
        mItems.remove(index);
        notifyItemRemoved(index);
    }

    public final void insertItemByIndex(TItem item, int index) {
        mItems.add(index, item);
        notifyItemInserted(index);
    }

    public final void showLoading() {
        redrawGridStatus(RequestStatusDescriptor.LOADING);
    }

    public void hideRequestStatus() {
        redrawGridStatus(RequestStatusDescriptor.HIDDEN);
    }

    public final void showEmptyMessage() {
        redrawGridStatus(RequestStatusDescriptor.EMPTY);
    }

    public final void showErrorMessage() {
        redrawGridStatus(RequestStatusDescriptor.ERROR);
    }

    private void redrawGridStatus(int gridStatus) {
        int previousRequestStatus = mRequestStatus;
        mRequestStatus = gridStatus;
        if (mRequestStatus == RequestStatusDescriptor.HIDDEN) {
            notifyItemRemoved(mItems.size());
        } else if (previousRequestStatus == RequestStatusDescriptor.HIDDEN) {
            notifyItemInserted(mItems.size());
        } else {
            notifyItemChanged(mItems.size());
        }
    }

    public final void setOnItemClickListener(IListRecyclerViewItemClick<TItem> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface IListRecyclerViewItemClick<TItemInternal> {
        void onClick(int position, TItemInternal item);
    }

    protected abstract THolder onCreateItemViewHolder(ViewGroup parent);

    protected abstract void onBindItemViewHolder(THolder holder, int position);
}
