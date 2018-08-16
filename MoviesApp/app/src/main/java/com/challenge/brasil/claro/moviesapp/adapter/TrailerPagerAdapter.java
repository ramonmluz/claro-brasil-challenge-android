package com.challenge.brasil.claro.moviesapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.challenge.brasil.claro.moviesapp.model.vo.Trailer;
import com.challenge.brasil.claro.moviesapp.view.TrailerFragment_;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * Created by ramon on 16/08/18.
 */

public class TrailerPagerAdapter extends FragmentStatePagerAdapter {

    private List<Trailer> trailers;

    public TrailerPagerAdapter(FragmentManager fm, List<Trailer> trailers) {
        super(fm);
        this.trailers = trailers;
    }

    @Override
    public Fragment getItem(int position) {
        Trailer trailer = null;
        if (CollectionUtils.isNotEmpty(trailers)) {
            trailer = trailers.get(position);
        }
        return TrailerFragment_.builder().trailer(trailer).build();
    }

    @Override
    public int getCount() {
        return CollectionUtils.isNotEmpty(trailers) ? trailers.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Trailer trailer = trailers.get(position);
        return trailer.getName();
    }
}
