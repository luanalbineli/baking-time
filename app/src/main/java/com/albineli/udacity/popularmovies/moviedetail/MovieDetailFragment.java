package com.albineli.udacity.popularmovies.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.base.BaseFragment;
import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.event.FavoriteMovieEvent;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;
import com.albineli.udacity.popularmovies.model.MovieTrailerModel;
import com.albineli.udacity.popularmovies.moviedetail.review.MovieReviewAdapter;
import com.albineli.udacity.popularmovies.moviedetail.review.MovieReviewListDialog;
import com.albineli.udacity.popularmovies.moviedetail.trailer.MovieTrailerAdapter;
import com.albineli.udacity.popularmovies.moviedetail.trailer.MovieTrailerListDialog;
import com.albineli.udacity.popularmovies.ui.NonScrollableLLM;
import com.albineli.udacity.popularmovies.util.ApiUtil;
import com.albineli.udacity.popularmovies.util.UIUtil;
import com.albineli.udacity.popularmovies.util.YouTubeUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.greenrobot.eventbus.EventBus;

import java.security.InvalidParameterException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;


public class MovieDetailFragment extends BaseFragment<MovieDetailContract.View> implements MovieDetailContract.View {
    public static MovieDetailFragment getInstance(MovieModel movieModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_KEY, movieModel);

        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setArguments(bundle);
        return movieDetailFragment;
    }

    @Override
    protected BasePresenter<MovieDetailContract.View> getPresenterImplementation() {
        return mPresenter;
    }

    @Override
    protected MovieDetailContract.View getViewImplementation() {
        return this;
    }

    private static String MOVIE_KEY = "movie_model";

    @Inject
    MovieDetailPresenter mPresenter;

    private MovieModel mMovieModel;

    @BindView(R.id.clMovieDetailContainer)
    View mContainer;

    @BindView(R.id.ivMovieDetailBackdrop)
    SimpleDraweeView mBackdropSimpleDraweeView;

    @BindView(R.id.lbMovieDetailFavorite)
    LikeButton mFavoriteButton;

    @BindView(R.id.sdvMovieDetailPoster)
    SimpleDraweeView mPosterSimpleDraweeView;

    @BindView(R.id.tvMovieDetailTitle)
    TextView mTitleTextView;

    @BindView(R.id.mrbMovieDetailRatingStar)
    MaterialRatingBar mRatingBar;

    @BindView(R.id.tvMovieDetailRating)
    TextView mRatingTextView;

    @BindView(R.id.tvMovieDetailSynopsis)
    TextView mSynopsisTextView;

    @BindView(R.id.rvMovieDetailReviews)
    RecyclerView mReviewRecyclerView;

    @BindView(R.id.tvMovieDetailShowAllReviews)
    TextView mShowAllReviewsTextView;

    @BindView(R.id.rvMovieDetailTrailers)
    RecyclerView mTrailerRecyclerView;

    @BindView(R.id.tvMovieDetailShowAllTrailers)
    TextView mShowAllTrailersTextView;

    private MovieReviewAdapter mMovieReviewAdapter;
    private MovieTrailerAdapter mMovieTrailerAdapter;

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null || !getArguments().containsKey(MOVIE_KEY)) {
            throw new InvalidParameterException("movie");
        }
        mMovieModel = getArguments().getParcelable(MOVIE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureRecyclerViews();

        mPresenter.start(mMovieModel);
    }

    private void configureRecyclerViews() {
        setUpDefaultRecyclerViewConfiguration(mReviewRecyclerView);
        mMovieReviewAdapter = new MovieReviewAdapter(R.string.there_is_no_reviews_to_show, mPresenter::tryToLoadReviewAgain);
        mReviewRecyclerView.setAdapter(mMovieReviewAdapter);

        setUpDefaultRecyclerViewConfiguration(mTrailerRecyclerView);
        mMovieTrailerAdapter = new MovieTrailerAdapter(R.string.there_is_no_trailers_to_show, mPresenter::tryToLoadTrailersAgain);
        mTrailerRecyclerView.setAdapter(mMovieTrailerAdapter);
        mMovieTrailerAdapter.setOnItemClickListener((position, item) -> YouTubeUtil.openYouTubeVideo(getActivity(), item.getKey()));
    }

    private void setUpDefaultRecyclerViewConfiguration(RecyclerView recyclerView) {
        NonScrollableLLM linearLayoutManager = new NonScrollableLLM(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @OnClick(R.id.tvMovieDetailShowAllReviews)
    void onShowAllReviewsButtonClick() {
        mPresenter.showAllReviews();
    }

    @OnClick(R.id.tvMovieDetailShowAllTrailers)
    void onShowAllTrailersButtonClick() {
        mPresenter.showAllTrailers();
    }

    @Override
    public void showMovieDetail(final MovieModel movieModel) {
        String posterWidth = ApiUtil.getDefaultPosterSize(mPosterSimpleDraweeView.getWidth());
        String posterUrl = ApiUtil.buildPosterImageUrl(movieModel.getPosterPath(), posterWidth);
        mPosterSimpleDraweeView.setImageURI(posterUrl);

        String backdropWidth = ApiUtil.getDefaultPosterSize(UIUtil.getDisplayMetrics(mPosterSimpleDraweeView.getContext()).widthPixels);
        String backdropUrl = ApiUtil.buildPosterImageUrl(movieModel.getBackdropPath(), backdropWidth);
        mBackdropSimpleDraweeView.setImageURI(backdropUrl);


        mTitleTextView.setText(movieModel.getTitle());
        mSynopsisTextView.setText(movieModel.getOverview());

        float rating = (float) movieModel.getVoteAverage() / 2f; // The range of vote average is 0..10, and of the rating is 0..5
        mRatingBar.setRating(rating);

        mRatingTextView.setText(String.valueOf(movieModel.getVoteAverage()));

        mFavoriteButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                mPresenter.saveFavoriteMovie(movieModel);
                EventBus.getDefault().post(new FavoriteMovieEvent(movieModel, true));
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                mPresenter.removeFavoriteMovie(movieModel);
                EventBus.getDefault().post(new FavoriteMovieEvent(movieModel, false));
            }


        });
    }

    @Override
    public void showMovieReview(List<MovieReviewModel> movieReviewModelList) {
        mMovieReviewAdapter.addItems(movieReviewModelList);
        mMovieReviewAdapter.hideRequestStatus();
    }

    @Override
    public void showMovieTrailer(List<MovieTrailerModel> movieTrailerList) {
        mMovieTrailerAdapter.addItems(movieTrailerList);
        mMovieTrailerAdapter.hideRequestStatus();
    }

    @Override
    public void setFavoriteButtonState(boolean favorite) {
        mFavoriteButton.setLiked(favorite);
    }

    @Override
    public void showSuccessMessageAddFavoriteMovie() {
        showToastMessage(R.string.success_add_favorite_movie);
    }

    @Override
    public void showSuccessMessageRemoveFavoriteMovie() {
        showToastMessage(R.string.success_remove_favorite_movie);
    }

    @Override
    public void showErrorMessageAddFavoriteMovie() {
        showToastMessage(R.string.error_add_favorite_movie);
    }

    @Override
    public void showErrorMessageRemoveFavoriteMovie() {
        showToastMessage(R.string.error_remove_favorite_movie);
    }

    @Override
    public void showErrorMessageLoadReviews() {
        mMovieReviewAdapter.showErrorMessage();
    }

    @Override
    public void showErrorMessageLoadTrailers() {
        mMovieTrailerAdapter.showErrorMessage();
    }

    @Override
    public void setShowAllReviewsButtonVisibility(boolean visible) {
        mShowAllReviewsTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setShowAllTrailersButtonVisibility(boolean visible) {
        mShowAllTrailersTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLoadingReviewsIndicator() {
        mMovieReviewAdapter.showLoading();
    }

    @Override
    public void showLoadingTrailersIndicator() {
        mMovieTrailerAdapter.showLoading();
    }

    @Override
    public void showAllReviews(List<MovieReviewModel> movieReviewList, boolean hasMore) {
        MovieReviewListDialog movieReviewListDialog = MovieReviewListDialog.getInstance(movieReviewList, mMovieModel.getId(), hasMore);
        movieReviewListDialog.show(getChildFragmentManager(), "movie_review_dialog");
    }

    @Override
    public void showAllTrailers(List<MovieTrailerModel> movieTrailerList) {
        MovieTrailerListDialog movieTrailerListDialog = MovieTrailerListDialog.getInstance(movieTrailerList);
        movieTrailerListDialog.show(getChildFragmentManager(), "movie_trailer_dialog");
    }

    @Override
    public void showEmptyReviewListMessage() {
        mMovieReviewAdapter.showEmptyMessage();
    }

    @Override
    public void showEmptyTrailerListMessage() {
        mMovieTrailerAdapter.showEmptyMessage();
    }

    private void showToastMessage(@StringRes int messageResId) {
        Snackbar.make(mContainer, messageResId, Snackbar.LENGTH_SHORT).show();
    }
}
