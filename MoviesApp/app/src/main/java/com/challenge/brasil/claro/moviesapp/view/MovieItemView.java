package com.challenge.brasil.claro.moviesapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.model.vo.Movie;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.view_movie_item)
public class MovieItemView extends FrameLayout {

    @ViewById
    protected ImageView movieImageGrid;

    public MovieItemView(Context context) {
        super(context);
    }

    public MovieItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void init() {
    }

    public void bind(Movie movie) {
        if (movie != null) {
            String imgageUrl = getContext().getString(R.string.BASE_URL_IMAGE) + movie.getPosterPath() ;

            // Obtem a imagem do objeto user e a insere no componente de imagem instanciado
            Picasso.with(movieImageGrid.getContext()).load(imgageUrl).into(movieImageGrid);
        }
    }

    @Click(R.id.movieImageGrid)
    void initMovieDetail() {
        // TODO - Call Movie Detail
    }

}
