package com.albineli.udacity.popularmovies.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albineli.udacity.popularmovies.R;
import com.albineli.udacity.popularmovies.base.BaseFragment;
import com.albineli.udacity.popularmovies.base.BasePresenter;
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerFragmentComponent;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.util.ApiUtil;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.ivMovieDetailBackdrop)
    ImageView mBackdropImageView;

    @BindView(R.id.lbMovieDetailFavorite)
    LikeButton mFavoriteButton;

    @BindView(R.id.ivMovieDetailPoster)
    ImageView mPosterImageView;

    @BindView(R.id.tvMovieDetailTitle)
    TextView mTitleTextView;

    @BindView(R.id.mrbMovieDetailRatingStar)
    MaterialRatingBar mRatingBar;

    @BindView(R.id.tvMovieDetailRating)
    TextView mRatingTextView;

    @BindView(R.id.tvMovieDetailSynopsis)
    TextView mSynopsisTextView;

    @Override
    public void showMovieDetail(MovieModel movieModel) {
        String posterWidth = ApiUtil.getDefaultPosterSize(mPosterImageView.getWidth());
        String posterUrl = ApiUtil.buildPosterImageUrl(movieModel.getPosterPath(), posterWidth);
        Picasso.with(getActivity())
                .load(posterUrl)
                .into(mPosterImageView);

        String backdropWidth = ApiUtil.getDefaultPosterSize(mBackdropImageView.getWidth());
        String backdropUrl = ApiUtil.buildPosterImageUrl(movieModel.getBackdropPath(), backdropWidth);
        Picasso.with(getActivity())
                .load(backdropUrl)
                .into(mBackdropImageView);

        mTitleTextView.setText(movieModel.getTitle());
        mSynopsisTextView.setText(movieModel.getOverview());

        float rating = (float) movieModel.getVoteAverage() / 2f;
        mRatingBar.setRating(rating);

        mRatingTextView.setText(String.valueOf(movieModel.getVoteAverage()));

        /*mUserRateTextView.setText(String.valueOf(movieModel.getVoteAverage()));
        mReleaseDateTextView.setText(movieModel.getReleaseDate());*/
    }

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

        mPresenter.start(mMovieModel);
    }

    @Override
    public void setTitle() {
        getActivity().setTitle(R.string.movie_detail);
    }
}
