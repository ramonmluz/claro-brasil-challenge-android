package com.challenge.brasil.claro.moviesapp.view;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.adapter.TrailerPagerAdapter;
import com.challenge.brasil.claro.moviesapp.model.vo.Trailer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_trailer)
public class TrailerActivity extends AppCompatActivity {


    @ViewById
    protected ViewPager viewPager;

    @ViewById
    protected PagerSlidingTabStrip tabs;

    @Extra
    protected List<Trailer> trailers;

    @Extra
    protected int position;

    private PagerAdapter pagerAdapter;

    @AfterViews
    void init() {

        pagerAdapter = new TrailerPagerAdapter(getSupportFragmentManager(), trailers);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position);
        tabs.setViewPager(viewPager);
    }

}
