package com.studioapp.mobileapp;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.jar.Attributes;

public class RecomandariFragment extends Fragment {

    private static View rootView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recomandari,container,false);

        View linearLayout = rootView.findViewById(R.id.linearLayout_recomandari);
        int i=1;
        while(i!=90) {

            TextView textView = new TextView(getContext());
            textView.setText("Recomandarea "+ i);
            textView.setTextSize(28);
            textView.setPadding(200,80,0,0);


            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            ((LinearLayout) linearLayout).addView(textView);
            i=i+1;
        }

        return rootView;
    }
}
