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
import com.albineli.udacity.popularmovies.injector.components.ApplicationComponent;
import com.albineli.udacity.popularmovies.injector.components.DaggerMovieDetailComponent;
import com.albineli.udacity.popularmovies.injector.modules.MovieDetailModule;
import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.util.ApiUtil;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.albineli.udacity.popularmovies.util.LogUtils.LOGI;
import static com.albineli.udacity.popularmovies.util.LogUtils.makeLogTag;


public class MovieDetailFragment extends BaseFragment implements MovieDetailContract.View {
    public static MovieDetailFragment getInstance(MovieModel movieModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_KEY, movieModel);

        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setArguments(bundle);
        return movieDetailFragment;
    }

    private static String MOVIE_KEY = "movie_model";
    private static String TAG = makeLogTag(MovieDetailFragment.class);

    @Inject
    MovieDetailContract.Presenter mPresenter;

    private MovieModel mMovieModel;

    @BindView(R.id.iv_movie_detail_poster)
    ImageView mPosterImageView;

    @BindView(R.id.tv_movie_detail_original_title)
    TextView mOriginalTitleTextView;

    @BindView(R.id.tv_movie_detail_synopsis)
    TextView mSynopsisTextView;

    @BindView(R.id.tv_movie_detail_user_rate)
    TextView mUserRateTextView;

    @BindView(R.id.tv_movie_detail_release_date)
    TextView mReleaseDateTextView;

    @Override
    public void showMovieDetail(MovieModel movieModel) {
        String posterWidth = ApiUtil.getDefaultPosterSize(mPosterImageView.getWidth());
        String posterUrl = ApiUtil.buildPosterImageUrl(movieModel.getPosterPath(), posterWidth);
        Picasso.with(getActivity())
                .load(posterUrl)
                .into(mPosterImageView);

        LOGI(TAG, "Url: " + posterUrl);

        mOriginalTitleTextView.setText(movieModel.getOriginalTitle());
        mSynopsisTextView.setText(movieModel.getOverview());

        mUserRateTextView.setText(String.valueOf(movieModel.getVoteAverage()));
        mReleaseDateTextView.setText(movieModel.getReleaseDate());
    }

    @Override
    protected void onInjectDependencies(ApplicationComponent applicationComponent) {
        DaggerMovieDetailComponent.builder()
                .applicationComponent(applicationComponent)
                .movieDetailModule(new MovieDetailModule(this))
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
