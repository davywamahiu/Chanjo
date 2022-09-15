package com.example.desagu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation extends Animation {

    private ProgressBar progressBar;
    private Context context;
    private TextView textView;
    float from;
    float to;

    public ProgressBarAnimation(Context  context, ProgressBar progressBar, TextView textView, float from,float to){

        this.context = context;
        this.progressBar = progressBar;
        this.textView =textView;
        this.from = from;
        this.to = to;

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        try {
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
            textView.setText((int) value + " %");

            if (value == to) {



            }
        }catch (Exception e){
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
