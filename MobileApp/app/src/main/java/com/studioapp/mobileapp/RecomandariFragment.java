package com.studioapp.mobileapp;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.jar.Attributes;

public class RecomandariFragment extends Fragment {

    private static View rootView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<RecomandareItem> recomandareItemArrayList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recomandari,container,false);

//        ArrayList<RecomandareItem> recomandareItemArrayList = new ArrayList<>();


//        recomandareItemArrayList.add(new RecomandareItem(R.drawable.ic_add_alert_black_24dp,"Titlu recomandare","Text despre recomandare"));


        recyclerView = rootView.findViewById(R.id.recycleview_recomandari);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecomandariItemAdapter(recomandareItemArrayList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter) ;

        try {
            getAndPreviwRecomandation();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public void getAndPreviwRecomandation() throws SQLException {
        Statement sql;
        sql = Profil.getInstance().conectionclass().createStatement();

        Log.e("Recomandation","getAndPreviwRecomandation");
        ResultSet rs;

        rs = sql.executeQuery("select * from [dbo].[UserRecomandation]");

        while (rs.next())
        {
            if(String.valueOf(rs.getInt("id")).equals(Profil.getInstance().getID()))
            {
                String titlu = rs.getString("titleRecomandation");
                String recomandarea = rs.getString("recomandation");
                recomandareItemArrayList.add(new RecomandareItem(R.drawable.ic_add_alert_black_24dp,titlu,recomandarea));
            }
        }
        Profil.getInstance().conectionclass().close();
    }


}
