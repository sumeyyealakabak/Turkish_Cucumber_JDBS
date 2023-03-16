package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static utilities.DBUtils.*;

public class Stepdefinition {
    List<Object> staffID= new ArrayList<>();

    // JDBC (DB) testi yapilmaya baslamadan önce Sistem Yöneticisi ile görüsülüp Database
    // bilgileri alinir.
    /*
    Database baglantisi icin gerekli bilgiler.
    type: jdbc:mysql   //https gibi
    host/ip: 45.84.206.41
    port:3306
    database: u480337000_tlb_training
    username: u480337000_tbl_training_u
    password: pO9#4bmxU
     */



    String url="jdbc:mysql://45.84.206.41:3306/u480337000_tlb_training";
    String username="u480337000_tbl_training_u";
    String password="pO9#4bmxU";

    // Database Sistem Yöneticisinden alinan bilgiler ile bir Url olusturuldu
    // Username ve password String data type'inda bir variable atandi.



    Connection connection;
    Statement statement;
    ResultSet resultset;


    @Given("Database ile iletisimi baslat")
    public void database_ile_iletisimi_baslat() throws SQLException {

        connection= DriverManager.getConnection(url,username,password);
        statement= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        // connection objemizi olusturduk ve (url,username ve password datalarini connection objesinin icine koyduk.)
        // olusturdugumuz connection objesini kullanarak typelari belli bir satatement create ettik.
    }


    @Then("Query statement araciligi ile database gonderilir")
    public void query_statement_araciligi_ile_database_gonderilir() throws SQLException {
        String query= "SELECT * FROM u480337000_tlb_training.users;";

        resultset= statement.executeQuery(query);
        // statement araciligi ile Database'e gönderdigimiz query sonucunu (datayi) bir resultset icinde store ettik.
    }


    @Then("Databaseden donen resulset verisi test edilir")
    public void databaseden_donen_resulset_verisi_test_edilir() throws SQLException {

        resultset.first();
        System.out.println(resultset.getString("first_name"));//Super

        String actualName= resultset.getString("first_name");
        String expectedName="Super";

       assertEquals(expectedName,actualName);


       /*
        resultset.next();
        System.out.println(resultset.getString("first_name"));

        resultset.next();
        System.out.println(resultset.getString("first_name"));

        resultset.absolute(11);
        System.out.println(resultset.getString("first_name"));

        System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°");

        resultset.absolute(0);  // resultset.first() yaparsak dongude de next() dedigi icin 1. yi gecmis olur

        int sira=1;
        while(resultset.next()){
            System.out.println(sira+"--"+resultset.getString("first_name"));
            sira++;
        }

        resultset.absolute(11);
        System.out.println(resultset.getString("email"));

        */


    }
    @Then("Database kapatilir")
    public void database_kapatilir() throws SQLException {
        connection.close();
    }


    //*********************************************************************************************************


    @Given("Database baglantisi kurulur.")
    public void database_baglantisi_kurulur() {
        createConnection();
    }
    @Given("Staff tablosundaki {string} leri listelenir.")
    public void staff_tablosundaki_leri_listelenir(String id) {
        staffID = getColumnData("SELECT * FROM u480337000_tlb_training.staff",id);
        System.out.println(staffID);
    }
    @Given("Verilen {string} dogrulanir.")
    public void verilen_dogrulanir(String verilenId) {

        assertTrue(staffID.toString().contains(verilenId));
    }
    @Given("Database baglantisi kapatilir.")
    public void database_baglantisi_kapatilir() {
        closeConnection();
    }












}

