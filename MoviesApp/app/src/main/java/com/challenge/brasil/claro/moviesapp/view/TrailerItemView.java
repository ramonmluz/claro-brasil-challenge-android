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

    public void bind(Trailer trailer) {
        if (trailer != null) {
            trailerName.setText(trailer.getName());
            trailerName.setTag(trailer);
        }
    }


    @Click(R.id.trailerName)
    void showTrailers() {
        TrailerActivity_.intent(getContext())
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .trailer((Trailer) trailerName.getTag())
                .start();

    }

}
