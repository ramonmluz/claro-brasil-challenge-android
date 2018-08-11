package com.challenge.brasil.claro.moviesapp.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.challenge.brasil.claro.moviesapp.model.vo.Movie;
import com.challenge.brasil.claro.moviesapp.view.MovieItemView;
import com.challenge.brasil.claro.moviesapp.view.MovieItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.parceler.Repository;

import java.util.List;


@EBean
public class MovieAdapter extends RecyclerViewAdapterBase<Movie, MovieItemView> {

    @RootContext
    Context context;

    @Override
    protected MovieItemView onCreateItemView(ViewGroup parent, int viewType) {
        return MovieItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<MovieItemView> holder, int position) {
        MovieItemView view = holder.getView();
        Movie movie = items.get(position);
        view.bind(movie, position);
    }

    @Override
    public void setItems(List<Movie> items) {
        super.setItems(items);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
