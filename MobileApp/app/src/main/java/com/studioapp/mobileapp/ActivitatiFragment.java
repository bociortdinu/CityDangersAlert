package com.studioapp.mobileapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ActivitatiFragment extends Fragment {

    private static View rootView;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    private ArrayList<Event> events = new ArrayList<>();
    private TextView textViewMonth;

    private CompactCalendarView compactCalendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_activitati,container,false);

        compactCalendar = (CompactCalendarView) rootView.findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        compactCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        textViewMonth = rootView.findViewById(R.id.textLuna);
        textViewMonth.setText(dateFormatMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));

        try {
            getEventFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        compactCalendar.addEvents(events);

        for(int i=0;i<events.size();i++)
        {
            Log.e("DataInEvent","Event "+i+":");
            Log.e("DataInEvent" , "timeInMillis: "+events.get(i).getTimeInMillis() + "   eventData: "+events.get(i).getData());
        }



        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getContext();

                if (compactCalendar.getEvents(dateClicked).isEmpty()){
                    Toast.makeText(context, "No Events Planned for that day", Toast.LENGTH_SHORT).show();
                }else {
                    List<Event> events = compactCalendar.getEvents(dateClicked);
                    Toast.makeText(context, events.get(0).getData().toString(), Toast.LENGTH_SHORT).show();

                }
            }



            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                textViewMonth.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });




        return rootView;
    }


    private void getEventFromDB() throws SQLException {
        Statement sql;
        sql = Profil.getInstance().conectionclass().createStatement();

        Log.e("Activity","getEventFromDB");
        ResultSet rs;

        rs = sql.executeQuery("select * from [dbo].[UserEvents]");

        events.clear();

        while(rs.next())
        {
            if(String.valueOf(rs.getInt("id")).equals(Profil.getInstance().getID()))
            {
                int id = rs.getInt("id");
                long timeInMillis = rs.getLong("timeInMillis");
                String eventData = rs.getString("eventData");
                Log.e("ReciveData" , "id: "+id +"   timeInMillis: "+timeInMillis + "   eventData: "+eventData);

                Event ev = new Event(Color.GREEN, timeInMillis,eventData);
                events.add(ev);
            }
        }
    }




}
