package com.studioapp.mobileapp;

import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeoutException;


public class ProfilFragment extends Fragment {

    private static View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profil, container, false);

        Profil p = new Profil();
        p.updateProfil();

        return rootView;
    }

    public static View getRootView()
    {
        return rootView;
    }
}


class Profil {


    private String numeUtilizator;
    private String CNP;
    private String adresa;
    private String dataNasterii;
    private String adresaEmail;
    private String nrTelefon;
    private String idMedic;
    private String locDeMunca;
    private ImageView imagineProfil;


    public Profil()
    {
        numeUtilizator= "Bociort Dinu";
        CNP = "1983458621451";
        adresa = "Arad, Arad, Str.Sever Bocu, Nr. 6";
        dataNasterii = "18/12/1998";
        adresaEmail = "bociortdinu@gmail.com";
        nrTelefon = "0746339033";
        idMedic = "112341244";
        locDeMunca = "Continental Automotive";
    }

    public void updateProfil()
    {
        TextView text;
        text = ProfilFragment.getRootView().findViewById(R.id.numeUtilizator);
        text.setText(numeUtilizator);

        text = ProfilFragment.getRootView().findViewById(R.id.cnpUtilizator);
        text.setText(CNP);

        text = ProfilFragment.getRootView().findViewById(R.id.adresaUtilizator);
        text.setText(adresa);

        text = ProfilFragment.getRootView().findViewById(R.id.dataNasteriiUtilizator);
        text.setText(dataNasterii);

        text = ProfilFragment.getRootView().findViewById(R.id.adresaEmailUtilizator);
        text.setText(adresaEmail);

        text = ProfilFragment.getRootView().findViewById(R.id.telefonUtilizator);
        text.setText(nrTelefon);

        text = ProfilFragment.getRootView().findViewById(R.id.idMedicUtilizator);
        text.setText(idMedic);

        text = ProfilFragment.getRootView().findViewById(R.id.locDeMuncaUtilizator);
        text.setText(locDeMunca);

    }

}