package com.challenge.brasil.claro.moviesapp.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.challenge.brasil.claro.moviesapp.MovieDatailActivity_;
import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.model.vo.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.view_movie_item)
public class MovieItemView extends FrameLayout {

    @ViewById
    protected ImageView movieImageGrid;

    private ViewGroup.MarginLayoutParams layoutParams;

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

    public void bind(Movie movie, int position) {
        if (movie != null) {
            movieImageGrid.setTag(movie);
            String imageUrl = getContext().getString(R.string.BASE_URL_IMAGE) + movie.getPosterPath() ;
            Picasso.with(movieImageGrid.getContext()).load(imageUrl).placeholder(R.mipmap.local_movies).error(R.mipmap.ic_launcher)
                    .into(movieImageGrid,
                    new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("TAG", "onSuccess");
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    @Click(R.id.movieImageGrid)
    void initMovieDetail() {
        MovieDatailActivity_.intent(getContext())
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .movie((Movie) movieImageGrid.getTag())
                .start();
    }

}
