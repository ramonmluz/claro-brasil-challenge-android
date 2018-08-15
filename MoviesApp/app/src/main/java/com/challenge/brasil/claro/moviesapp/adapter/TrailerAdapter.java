package com.challenge.brasil.claro.moviesapp.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.challenge.brasil.claro.moviesapp.model.vo.Movie;
import com.challenge.brasil.claro.moviesapp.model.vo.Trailer;
import com.challenge.brasil.claro.moviesapp.view.MovieItemView;
import com.challenge.brasil.claro.moviesapp.view.MovieItemView_;
import com.challenge.brasil.claro.moviesapp.view.TrailerItemView;
import com.challenge.brasil.claro.moviesapp.view.TrailerItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;


@EBean
public class TrailerAdapter extends RecyclerViewAdapterBase<Trailer, TrailerItemView> {

    @RootContext
    Context context;

    @Override
    protected TrailerItemView onCreateItemView(ViewGroup parent, int viewType) {
        return TrailerItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<TrailerItemView> holder, int position) {
        TrailerItemView view = holder.getView();
        Trailer trailer = items.get(position);
        view.bind(trailer, position, items);
    }

    @Override
    public void setItems(List<Trailer> items) {
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
