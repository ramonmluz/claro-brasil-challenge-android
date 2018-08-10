package com.challenge.brasil.claro.moviesapp.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.SettingsActivity;
import com.challenge.brasil.claro.moviesapp.adapter.MovieAdapter;
import com.challenge.brasil.claro.moviesapp.model.bo.ApiCallBack;
import com.challenge.brasil.claro.moviesapp.model.bo.MovieBO;
import com.challenge.brasil.claro.moviesapp.model.vo.Movie;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    @Bean
    MovieBO movieBO;

    @Bean
    MovieAdapter movieAdapter;

    @ViewById
    protected RecyclerView recyclerView;

    @ViewById
    protected View progress;

    @ViewById
    protected View areaErro;

    @ViewById
    protected EditText searchMovieText;

    @ViewById
    protected TextView textMsgErroView;

    @InstanceState
    protected List<Movie> movies;

    @InstanceState
    protected String nameQuery;

    private GridLayoutManager mLayoutManager;

    @AfterViews
    void init() {
        textMsgErroView.setText(getString(R.string.error_listing_movies));
        initRecyclerView();
        initTextChangeListner();
        showView(null);

        if (CollectionUtils.isNotEmpty(movies)) {
            showList(movies);
        }
    }

    private void initTextChangeListner() {
        searchMovieText.addTextChangedListener(new TextWatcher() {
            private boolean isChanged = false;


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                if(charSequence.length() < 3) {
                    return;
                }
                nameQuery = charSequence.toString();
                searchMovies();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
        }, nameQuery);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(CollectionUtils.isNotEmpty(movies)){
            showList(movies);
        }
    }

    @UiThread
    protected void showList(List<Movie> movies) {
        this.movies = movies;
        movieAdapter.setItems(this.movies);
        movieAdapter.notifyDataSetChanged();
        showView(recyclerView);
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

    private void seetupAcionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
