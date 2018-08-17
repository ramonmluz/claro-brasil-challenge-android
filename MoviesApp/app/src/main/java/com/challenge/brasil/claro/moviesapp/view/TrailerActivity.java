package com.challenge.brasil.claro.moviesapp.view;

import android.widget.Toast;

import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.model.vo.Trailer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_trailer)
public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {

    @Extra
    protected Trailer trailer;

    @ViewById
    protected YouTubePlayerView youTubePlayerView;


    @AfterViews
    void init() {
        youTubePlayerView.initialize(getString(R.string.KEY_YOUTUBE_API),this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo(trailer.getKey()); // Plays https://www.youtube.com/watch?v=sAOzrChqmd0'
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, 1).show();
        } else {
            String error = String.format("Error", errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

}
