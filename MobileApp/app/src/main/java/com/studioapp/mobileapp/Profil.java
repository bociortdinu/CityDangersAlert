package com.studioapp.mobileapp;

import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Profil{


    private String numeUtilizator;
    private String prenumeUtilizator;
    private String CNP;
    private String adresa;
    private String dataNasterii;
    private String adresaEmail;
    private String nrTelefon;
    private String idMedic;
    private String locDeMunca;
    private ImageView imagineProfil;

    private static Profil profil;

    public Profil()
    {
        numeUtilizator= "Nume";
        prenumeUtilizator = "Prenume";
        CNP = "0000000000000";
        adresa = "Jud, Oras, Strada, Numar";
        dataNasterii = "zi/luna/an";
        adresaEmail = "exemplu@exemplu.com";
        nrTelefon = "0700000000";
        idMedic = "112341244";
        locDeMunca = "Firma la care lucrezi";


    }

    public static synchronized Profil getInstance()
    {
        if(profil==null)
        {
            profil = new Profil();
        }
        return profil;
    }

    public void updateProfil()
    {
        TextView text;
        text = ProfilFragment.getRootView().findViewById(R.id.numeUtilizator);
        text.setText(numeUtilizator + " " + prenumeUtilizator);

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


//    public Connection conectionclass()
//    {
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        Connection connection = null;
//        String ConnectionURL = null;
//
//        String USERNAME = "your_mysql_username";
//        String PASSWORD = "your_mysql_password";
//        String DRIVER = "com.mysql.jdbc.Driver";
//        String URL = "jdbc:jtds:mysql://localhost:3306/mobileapp";
//        try
//        {
//            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            //your database connection string goes below
//            ConnectionURL=URL;
//            connection = DriverManager.getConnection(ConnectionURL,"root","descarcare");
//        }
//        catch (SQLException e)
//        {
//            Log.e("error here 1 : ", e.getMessage());
//        }
//        catch (ClassNotFoundException e)
//        {
//            Log.e("error here 2: ", e.getLocalizedMessage());
//        }
//        catch (Exception e)
//        {
//            Log.e("error here 3: ", e.getLocalizedMessage());
//        }
//
//        return connection;
//    }




    public String getNumeUtilizator()
    {
        return numeUtilizator;
    }
    public void setNumeUtilizator(String nume)
    {
        numeUtilizator=nume;
    }

    public String getPrenumeUtilizator()
    {
        return prenumeUtilizator;
    }
    public void setPrenumeUtilizator(String prenume)
    {
        prenumeUtilizator=prenume;
    }

    public String getCNP()
    {
        return CNP;
    }
    public void setCNP(String cnp)
    {
        CNP=cnp;
    }

    public String getAdresa()
    {
        return adresa;
    }
    public void setAdresa(String adr)
    {
        adresa=adr;
    }

    public String getDataNasterii()
    {
        return dataNasterii;
    }
    public void setDataNasterii(String data)
    {
        dataNasterii=data;
    }

    public String getAdresaEmail()
    {
        return adresaEmail;
    }
    public void setAdresaEmail(String email)
    {
        adresaEmail=email;
    }

    public String getNrTelefon()
    {
        return nrTelefon;
    }
    public void setNrTelefon(String telefon)
    {
        nrTelefon=telefon;
    }

    public String getIdMedic()
    {
        return idMedic;
    }
    public void setIdMedic(String medic)
    {
        idMedic=medic;
    }

    public String getLocDeMunca()
    {
        return locDeMunca;
    }
    public void setLocDeMunca(String munca)
    {
        locDeMunca=munca;
    }

}
