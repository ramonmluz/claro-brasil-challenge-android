package com.challenge.brasil.claro.moviesapp.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.challenge.brasil.claro.moviesapp.MovieDatailActivity_;
import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.model.vo.Movie;
import com.challenge.brasil.claro.moviesapp.model.vo.Trailer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;


@EViewGroup(R.layout.view_trailer_item)
public class TrailerItemView extends FrameLayout {

    @ViewById
    protected TextView trailerName;

    public TrailerItemView(Context context) {
        super(context);
    }

    public TrailerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrailerItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(Trailer trailer, int position, List<Trailer> trailers) {
        if (trailer != null) {
            Object[] params = new Object[3];
            trailerName.setText(trailer.getName());
            params[0] = trailer;
            params[1] = position;
            params[2] = trailers;
            trailerName.setTag(params);
        }
    }


    @Click(R.id.movieImageGrid)
    void showTrailers() {
        // TODO
    }

}
