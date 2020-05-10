package com.studioapp.mobileapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;


public class Profil{

    private String ID;
    private String numeUtilizator;
    private String prenumeUtilizator;
    private String CNP;
    private String adresa;
    private String dataNasterii;
    private String adresaEmail;
    private String nrTelefon;
    private String idMedic;
    private String locDeMunca;
    private Bitmap imagineProfil;

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

        ImageView imageView;
        imageView = ProfilFragment.getRootView().findViewById(R.id.imagineProfil);
        if(imagineProfil!=null)
            imageView.setImageBitmap(getImagineProfil());

    }

    public void updateProfilDataBase() throws SQLException {

        String SQL = "UPDATE [dbo].[UserProfil]" +
                " SET numeUtilizator=?, prenumeUtilizator=?, adresa=?, adresaEmail=?, nrTelefon=?, locDeMunca=?" +
                " WHERE id=?";

        Log.e("updateUserProfil","updateUserProfil");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if(imagineProfil!=null) {
            imagineProfil.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            Log.e("bitmap","bitmap size: "+ imagineProfil.getByteCount()/1000000 + " MB");
        }
        byte[] byteArrayImage = byteArrayOutputStream.toByteArray();

        Log.e("byteArray","byteArray size: "+ byteArrayImage.length + " Bytes");


        PreparedStatement statement = conectionclass().prepareStatement(SQL);
        statement.setString(1,numeUtilizator);
        Log.e("UPDATE","PASS Nume");

        statement.setString(2,prenumeUtilizator);
        Log.e("UPDATE","PASS Prenume");

        statement.setString(3,adresa);
        Log.e("UPDATE","PASS Adresa");

        statement.setString(4,adresaEmail);
        Log.e("UPDATE","PASS Email");

        statement.setString(5,nrTelefon);
        Log.e("UPDATE","PASS Telefon");

        statement.setString(6,locDeMunca);
        Log.e("UPDATE","PASS Munca");

//        statement.setBytes(7,byteArrayImage);
//        Log.e("UPDATE","PASS IMAGINE");

        statement.setString(7,ID);
        Log.e("UPDATE","PASS ID");


        int rowUpdated = statement.executeUpdate();
        if(rowUpdated>0)
        {
            Log.e("UPDATE","UPDATE EFECTUAT CU SUCCES");
        }
        conectionclass().close();


    }

    public void extractProfilData(String email) throws SQLException {
        Statement sql;
        sql = (Statement) conectionclass().createStatement();
        Log.e("extractProfilData","extractProfilData");
        ResultSet rs;
        rs = sql.executeQuery("select * from [dbo].[UserProfil]");

        while (rs.next())
        {
            if(rs.getString("adresaEmail").equals(email))
            {

                ID = String.valueOf(rs.getInt("id"));
                Log.e("DOWNLOAD","PASS id");

                numeUtilizator = rs.getString("numeUtilizator");
                Log.e("DOWNLOAD","PASS numeUtilizator");

                prenumeUtilizator=rs.getString("prenumeUtilizator");
                Log.e("DOWNLOAD","PASS prenumeUtilizator");

                CNP = rs.getString("CNP");
                Log.e("DOWNLOAD","PASS CNP");

                adresa = rs.getString("adresa");
                Log.e("DOWNLOAD","PASS adresa");

                dataNasterii = rs.getDate("dataNasterii").toString();
                Log.e("DOWNLOAD","PASS dataNasterii");

                adresaEmail = rs.getString("adresaEmail");
                Log.e("DOWNLOAD","PASS adresaEmail");

                nrTelefon = rs.getString("nrTelefon");
                Log.e("DOWNLOAD","PASS nrTelefon");

                idMedic = rs.getString("idMedic");
                Log.e("DOWNLOAD","PASS idMedic");

                locDeMunca = rs.getString("locDeMunca");
                Log.e("DOWNLOAD","PASS locDeMunca");

                byte[] byteArrayImage = rs.getBytes("imagineProfil");

                if(byteArrayImage!=null) {
                    Log.e("DOWNLOAD", "PASS imagineProfil");
                    imagineProfil = BitmapFactory.decodeByteArray(byteArrayImage, 0, byteArrayImage.length);
                    Log.e("byteArray", "byteArray: " + Arrays.toString(byteArrayImage));
                }
            }
        }
        conectionclass().close();

    }


    public Connection conectionclass()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        String USERNAME = "dinu@mobileapp-testserver";
        String PASSWORD = "Descarcare1";
        String URL = "jdbc:jtds:sqlserver://mobileapp-testserver.database.windows.net:1433;DatabaseName=ProfilData;user="+USERNAME+";password="+PASSWORD+";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        // String URL = "jdbc:jtds:sqlserver://lilycod-db-dev.database.windows.net:1433;database=lilcord-db-dev;user=lilycod-db-dev@lilycod-db-dev;password=London10;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;\n";

        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Log.e("CONNECT","PASS 1");

            ConnectionURL=URL;
            connection = DriverManager.getConnection(ConnectionURL);
            Log.e("CONNECT","PASS 2");

        }
        catch (SQLException e)
        {
            Log.e("error here 1 : ", e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2: ", e.getLocalizedMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3: ", e.getLocalizedMessage());
        }

        return connection;
    }


    public String getID()
    {
        return ID;
    }

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

    public Bitmap getImagineProfil()
    {
        return imagineProfil;
    }
    public void setImagineProfil(Bitmap bitmap)
    {
        imagineProfil = bitmap;
    }

}
