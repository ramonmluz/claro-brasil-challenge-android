package com.challenge.brasil.claro.moviesapp.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.model.vo.Trailer;

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
            Object[] params = new Object[2];
            trailerName.setText(trailer.getName());
            params[0] = position;
            params[1] = trailers;
            trailerName.setTag(params);
        }
    }


    @Click(R.id.trailerName)
    void showTrailers() {
        // Show Traliers
        Object[] params = (Object[]) trailerName.getTag();

        //TODO
        TrailerActivity_.intent(getContext())
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .position((Integer) params[0])
                .trailers((List<Trailer>) params[1])
                .start();

    }

}
