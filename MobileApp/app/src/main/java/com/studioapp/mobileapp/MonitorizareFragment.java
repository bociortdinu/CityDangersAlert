package com.studioapp.mobileapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MonitorizareFragment extends Fragment {

    private static View rootView;

    TextView umiditate;
    TextView temperatura;
    TextView puls;
    TextView EKG;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_monitorizare,container,false);

        umiditate = rootView.findViewById(R.id.text_umiditate);
        temperatura = rootView.findViewById(R.id.text_temperatura);
        puls = rootView.findViewById(R.id.text_puls);
        EKG = rootView.findViewById(R.id.text_EKG);


        return rootView;
    }

    private void uploadMonitorDB() throws SQLException {
        String SQL = "UPDATE [dbo].[UserMonitor]" +
                " SET umiditate=?, temperatura=?, puls=?, EKG=?, mesajAlarma=?" +
                " WHERE id=?";

        Log.e("uploadMonitorDB","uploadMonitorDB");

        PreparedStatement statement = Profil.getInstance().conectionclass().prepareStatement(SQL);
        statement.setFloat(0, Float.parseFloat(umiditate.getText().toString()));
        statement.setFloat(1, Float.parseFloat(temperatura.getText().toString()));
        statement.setFloat(2, Float.parseFloat(puls.getText().toString()));
        statement.setFloat(3, Float.parseFloat(EKG.getText().toString()));
        statement.setString(4, "mesajul de alarma");

        int rowUpdated = statement.executeUpdate();
        if(rowUpdated>0)
        {
            Log.e("UPDATE","UPDATE EFECTUAT CU SUCCES");
        }
        Profil.getInstance().conectionclass().close();

    }

}


