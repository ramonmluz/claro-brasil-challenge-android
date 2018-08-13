package com.challenge.brasil.claro.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.challenge.brasil.claro.moviesapp.model.bo.MovieBO;
import com.challenge.brasil.claro.moviesapp.model.vo.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_movie_datail)
public class MovieDatailActivity extends AppCompatActivity {

    @Bean
    protected MovieBO movieBO;

    @ViewById
    protected ImageView movieImageDetail;

    @ViewById
    protected ImageView favoriteMovieDetail;

    @ViewById
    protected TextView releaseDate;

    @ViewById
    protected TextView overview;

    @Extra
    @InstanceState
    protected Movie movie;

    @InstanceState
    protected boolean isFavoriteOn = false;

    @AfterViews
    void init (){
        if (movie != null) {
            loadMovieImage();
            initFields();
        }
    }

    private void initFields() {
        getSupportActionBar().setTitle(movie.getOriginalTitle());
        releaseDate.setText(movie.getReleaseDate());
        overview.setText(movie.getOverview());
    }

    private void loadMovieImage() {
        String imageUrl = getString(R.string.BASE_URL_IMAGE) + movie.getPosterPath() ;
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

    @Click(R.id.favoriteMovieDetail)
    public void maintainMovie(){
        if(isFavoriteOn){
            isFavoriteOn = false;
            favoriteMovieDetail.setImageResource(android.R.drawable.star_off);
            movieBO.delete(movie);
        } else {
            isFavoriteOn = true;
            favoriteMovieDetail.setImageResource(android.R.drawable.star_on);
            movieBO.insert(movie);
        }
    }

}
