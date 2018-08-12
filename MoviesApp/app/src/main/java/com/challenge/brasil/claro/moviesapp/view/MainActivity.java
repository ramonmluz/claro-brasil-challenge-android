package com.challenge.brasil.claro.moviesapp.view;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.challenge.brasil.claro.moviesapp.R;
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
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
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
    protected TextView textMsgErroView;

    @ViewById
    protected Toolbar toolbar;

    @InstanceState
    protected List<Movie> movies;

    @InstanceState
    protected String nameQuery;

    private GridLayoutManager mLayoutManager;

    private  List<Movie> popularMovies;

    private boolean isListPopularMoviesCloned = false;

    @AfterViews
    void init() {
        textMsgErroView.setText(getString(R.string.error_listing_movies));
        setSupportActionBar(toolbar);
        initRecyclerView();
        if (CollectionUtils.isNotEmpty(movies)) {
            showList(movies);
        } else {
            searchMovies();
        }
    }

    @OptionsMenuItem(R.id.menuSearch)
    void singleInjection(MenuItem item) {
        setOnActionExpandListner(item);
        SearchView searchView  = (SearchView) item.getActionView();
        initTextSearchQuery(searchView);
    }

    private void setOnActionExpandListner(MenuItem item) {
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                fillItemsRecyclerView(popularMovies);
                showView(recyclerView);
                return true;
            }
        });
    }

    private void initTextSearchQuery(SearchView searchView) {
        // TODO - Mudar de pesqusair
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(!isListPopularMoviesCloned && CollectionUtils.isNotEmpty(movies)){
                    popularMovies = (List) ((ArrayList)movies).clone();
                    isListPopularMoviesCloned = true;
                }

                if(newText.length() <3 ){
                    return false;
                }
                nameQuery = newText;
                searchMovies();

                return true;
            }
        });

    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int moviePosition = parent.getChildLayoutPosition(view);

                if(moviePosition == 0 || moviePosition == 1 ){
                    outRect.top = getAnIntDp(8);
                    defineMarginBottom(outRect);
                }else {
                    defineMarginBottom(outRect);
                }

                if (moviePosition % 2 == 0) {
                    defineMargin(outRect, 8,4);
                } else {
                    defineMargin(outRect, 4,8);
                }
            }
        });
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

        if (CollectionUtils.isNotEmpty(movies)) {
            showView(recyclerView);
        }
    }

    @UiThread
    protected void showList(List<Movie> movies) {
        this.movies = movies;
        fillItemsRecyclerView(this.movies);
        showView(recyclerView);
    }

    private void fillItemsRecyclerView(List<Movie> movies) {
        movieAdapter.setItems(movies);
        movieAdapter.notifyDataSetChanged();
    }

    private void defineMarginBottom(Rect outRect) {
        outRect.bottom = getAnIntDp(8);
    }

    private void defineMargin(Rect outRect ,int marginLeft, int marginRight ) {
        outRect.left =  getAnIntDp(marginLeft);
        outRect.right =  getAnIntDp(marginRight);
    }

    private int getAnIntDp(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
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
