package com.studioapp.mobileapp;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class RecomandariFragment extends Fragment {

    private static View rootView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recomandari,container,false);

        ArrayList<RecomandareItem> recomandareItemArrayList = new ArrayList<>();

//        View linearLayout = rootView.findViewById(R.id.linearLayout_recomandari);
        int i=1;
//
//        while(i!=90) {
//            TextView textView = new TextView(getContext());
//            textView.setText("Recomandarea "+ i);
//            textView.setTextSize(28);
//            textView.setPadding(200,80,0,0);
//
//
//            textView.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            ));
//
//            ((LinearLayout) linearLayout).addView(textView);
//            i=i+1;
//        }
        while(i!=90)
        {
            recomandareItemArrayList.add(new RecomandareItem(R.drawable.ic_add_alert_black_24dp,"Recomandarea "+i,"Text despre recomandare"));
            i++;
        }


        recyclerView = rootView.findViewById(R.id.recycleview_recomandari);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecomandariItemAdapter(recomandareItemArrayList);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter) ;

        return rootView;
    }
}
