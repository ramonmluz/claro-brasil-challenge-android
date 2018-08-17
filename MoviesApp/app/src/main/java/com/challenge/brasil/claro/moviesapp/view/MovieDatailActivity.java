package com.challenge.brasil.claro.moviesapp.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.adapter.TrailerAdapter;
import com.challenge.brasil.claro.moviesapp.model.bo.ApiCallBack;
import com.challenge.brasil.claro.moviesapp.model.bo.MovieBO;
import com.challenge.brasil.claro.moviesapp.model.bo.TrailerBO;
import com.challenge.brasil.claro.moviesapp.model.vo.Movie;
import com.challenge.brasil.claro.moviesapp.model.vo.Trailer;
import com.challenge.brasil.claro.moviesapp.util.ViewUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

@EActivity(R.layout.activity_movie_datail)
public class MovieDatailActivity extends AppCompatActivity {

    @Bean
    protected MovieBO movieBO;

    @Bean
    protected TrailerBO trailerBO;

    @Bean
    protected TrailerAdapter trailerAdapter;

    @ViewById
    protected ImageView movieImageDetail;

    @ViewById
    protected ImageView favoriteMovieDetail;

    @ViewById
    protected TextView releaseDate;

    @ViewById
    protected TextView overview;

    @ViewById
    protected RecyclerView trailerRecyclerView;

    @ViewById
    protected View trailerProgress;

    @ViewById
    protected View trailerAreaErro;

    @ViewById
    protected TextView textTrailerMsgErroView;

    private List<Trailer> trailers;


    @Extra
    @InstanceState
    protected Movie movie;

    @InstanceState
    protected boolean isFavoriteOn = false;

    private LinearLayoutManager mLayoutManager;

    @AfterViews
    void init() {
        textTrailerMsgErroView.setText(getString(R.string.error_listing_trailers));
        initFields();
        loadMovieImage();
        initRecyclerView();
        searchTrailers();
        searchSavedMovie();
    }

    private void initFields() {
        getSupportActionBar().setTitle(movie.getOriginalTitle());
        releaseDate.setText(movie.getReleaseDate());
        overview.setText(movie.getOverview());
    }

    private void loadMovieImage() {
        String imageUrl = getString(R.string.BASE_URL_IMAGE) + movie.getPosterPath();
        Picasso.with(movieImageDetail.getContext()).load(imageUrl).placeholder(R.mipmap.local_movies).error(R.mipmap.ic_launcher)
                .into(movieImageDetail,
                        new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("TAG", "onSuccess");
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(getBaseContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        trailerRecyclerView.setNestedScrollingEnabled(false);
        trailerRecyclerView.setLayoutManager(mLayoutManager);
        trailerRecyclerView.setAdapter(trailerAdapter);
        trailerRecyclerView.addItemDecoration(new DividerItemDecoration(trailerRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }


    private void searchTrailers() {

        showView(trailerProgress);
        trailerBO.requestTrailers(new ApiCallBack() {
            @Override
            public void onSuccess(Object response) {
                showTralers((List<Trailer>) response);
            }

            @Override
            public void onError(Map error) {
                showError(error);
            }
        }, movie.getId());
    }


    void showView(View view) {
        trailerProgress.setVisibility(View.GONE);
        trailerRecyclerView.setVisibility(View.GONE);
        trailerAreaErro.setVisibility(View.GONE);

        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    @UiThread
    protected void showTralers(List<Trailer> trailers) {
        this.trailers = trailers;
        if (CollectionUtils.isNotEmpty(this.trailers)) {
            trailerAdapter.setItems(trailers);
            trailerAdapter.notifyDataSetChanged();
            showView(trailerRecyclerView);
        } else {
            showView(null);
            ViewUtil.alert(this, getString(R.string.trailers_not_found));
        }
    }

    @UiThread
    protected void showError(Map errorMap) {
        showView(trailerAreaErro);
    }

    @Click(R.id.trailerAreaErro)
    protected void reloadTrailers() {
        searchTrailers();
    }

    private void searchSavedMovie() {
        String movieId = movieBO.findSavedMovieId(movie.getId());
        if (!TextUtils.isEmpty(movieId)) {
            enableFavoriteMovie(true, android.R.drawable.star_on);
        } else {
            enableFavoriteMovie(false, android.R.drawable.star_off);
        }
    }


    @Click(R.id.favoriteMovieDetail)
    public void maintainMovie() {
        if (isFavoriteOn) {
            enableFavoriteMovie(false, android.R.drawable.star_off);
            movieBO.delete(movie);
        } else {
            enableFavoriteMovie(true, android.R.drawable.star_on);
            movieBO.insert(movie);
        }
    }

    private void enableFavoriteMovie(boolean isFavorite, int imageDrawble) {
        isFavoriteOn = isFavorite;
        favoriteMovieDetail.setImageResource(imageDrawble);
    }

}
