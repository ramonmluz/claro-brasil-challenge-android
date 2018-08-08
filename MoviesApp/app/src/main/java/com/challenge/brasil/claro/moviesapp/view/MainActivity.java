package com.challenge.brasil.claro.moviesapp.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.adapter.MovieAdapter;
import com.challenge.brasil.claro.moviesapp.model.bo.ApiCallBack;
import com.challenge.brasil.claro.moviesapp.model.bo.MovieBO;
import com.challenge.brasil.claro.moviesapp.model.vo.Movie;
import com.challenge.brasil.claro.moviesapp.util.ViewUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bean
    MovieBO movieBO;

    @Bean
    MovieAdapter movieAdapter;

    @ViewById
    protected View progress;

    @InstanceState
    protected List<Movie> movies;

    @ViewById
    protected View areaErro;

    @ViewById
    protected TextView textMsgErroView;

    @ViewById
    protected RecyclerView recyclerView;

    private GridLayoutManager mLayoutManager;

    @AfterViews
    void init() {
        textMsgErroView.setText(getString(R.string.error_listing_movies));
        initRecyclerView();

        if (CollectionUtils.isNotEmpty(movies)) {
            showList(movies);
        } else {
            searchMovies();
        }
    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(movieAdapter);
    }

    private void searchMovies() {
        showView(progress);
        movieBO.requestMovies(new ApiCallBack() {
            @Override
            public void onSuccess(Object response) {
                showList((List<Movie>) response);
            }

            @Override
            public void onError(Map error) {
                showError(error);
            }
        });
    }

    @UiThread
    protected void showList(List<Movie> movies) {
        showView(recyclerView);
        this.movies = movies;
        movieAdapter.setItems(this.movies);
        movieAdapter.notifyDataSetChanged();
    }

    void showView(View view) {
        recyclerView.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        areaErro.setVisibility(View.GONE);

        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    @UiThread
    protected void showError(Map errorMap) {
        showView(areaErro);
    }

    @Click(R.id.areaErro)
    protected void reloadMovies() {
        searchMovies();
    }

}
