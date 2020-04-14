package com.studioapp.mobileapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation extends Animation {

    private Context context;
    private ProgressBar progressBar;
    private float from;
    private float to;
    private Activity a;
    public ProgressBarAnimation(Context context, ProgressBar progressBar, float from, float to,Activity a)
    {
        this.context=context;
        this.progressBar=progressBar;
        this.from=from;
        this.to=to;
        this.a=a;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value= from + (to-from)*interpolatedTime;
        progressBar.setProgress((int)value);

        if(value==to)
        {
            context.startActivity(new Intent(context,Login.class));
            a.finish();
        }
    }
}
