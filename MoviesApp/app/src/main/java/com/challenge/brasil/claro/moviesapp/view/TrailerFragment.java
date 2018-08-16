package com.challenge.brasil.claro.moviesapp.view;


import android.net.Uri;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.model.vo.Trailer;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_trailer)
public class TrailerFragment extends Fragment implements ExoPlayer.EventListener {

    private SimpleExoPlayer player;

    @FragmentArg
    Trailer trailer;

    @ViewById
    SimpleExoPlayerView playerView;

    @AfterViews
    void init() {
        initPlayer();
    }

    /**
     * Inicia o player
     */
    private void initPlayer() {
        playerView.setVisibility(View.VISIBLE);
        // get uri
        Uri mediaUri = getMediaUri();
        if (player == null && trailer != null && mediaUri != null) {
            // Creating ExoPlayer instance.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(player);

            // fill listner
            player.addListener(this);

            // Prepare o MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "MoviesApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        } else {
            playerView.setVisibility(View.GONE);
        }

    }

    private Uri getMediaUri() {
        Uri uri = null;
        if (!TextUtils.isEmpty(trailer.getKey())) {
            uri = Uri.parse(getString(R.string.trailer_url).concat(trailer.getKey()));
        }
        return uri;
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        //
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
