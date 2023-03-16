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
    List<Object> adresList= new ArrayList<>();
    List<Object> sellerProducts=new ArrayList<>();

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
    public void database_baglantisi_kurulur()
    {
        createConnection();
    }
    @Given("Staff tablosundaki {string} leri listelenir.")
    public void staff_tablosundaki_leri_listelenir(String id) {
        staffID = getColumnData("SELECT * FROM u480337000_tlb_training.staff",id);
        System.out.println(staffID);
        //getColumnData methodu list dondurur,
        //dondurulen list, list olan staffID ye atanir
    }
    @Given("Verilen {string} dogrulanir.")
    public void verilen_dogrulanir(String verilenId) {

        assertTrue(staffID.toString().contains(verilenId));
        //staffID list oldugu icin toString() ile string e cevrilir, sonra contains diye arayabiliriz.
    }
    @Given("Database baglantisi kapatilir.")
    public void database_baglantisi_kapatilir()
    {
        closeConnection();
    }


    //*********************************************************************************************************


    @Given("{string} degeri verilen customerin {string} guncellenir.")
    public void degeri_verilen_customerin_guncellenir(String id, String adres) throws SQLException {
        String query= "UPDATE u480337000_tlb_training.customer_addresses SET address= '"+adres+"' WHERE id="+id;

        //'"+adres+"'  varchar ve dinamiktir bu haliyle
        // (SET address= \"Helsinki\" \n") once boyleydi, yine icini bosaltip " tan sonra, ++ yapip icine adres yaziyoruz
        //aslinda("+adres+")   yerinde "Helsinki" var ama o hali varchar yapmaliyiz. o nedenle en son ('"+adres+"') bu sekilde yapiyoruz
        // cunku javada "" string, ''  olursa varchar olur


        //+id ile int ve dinamiktir
        // (where id=1") once boyleydi 1'i siliyoruz hiclik oluyor o hiclige concat yapiyoruz burada


        System.out.println(query);
              /*
              UPDATE u480337000_tlb_training.customer_addresses
                SET address= 'kadiköy' WHERE id=1
               */

        update(query);

    }
    @Given("customer address tablosundaki {string} bilgileri listelenir.") // "  "  ile adres kismi string olarak kullaniliyor
    public void customer_address_tablosundaki_bilgileri_listelenir(String columnName) {

        String query= "SELECT * FROM u480337000_tlb_training.customer_addresses;";

        adresList = getColumnData(query, columnName);
        //getColumnData() list donduruyor, o yuzden adresList'e atadik
        System.out.println(adresList);

    }
    @Given("customerin {string} guncellendigi dogrulanir.")
    public void customerin_guncellendigi_dogrulanir(String guncelleneneAdres) {

        assertTrue(adresList.toString().contains(guncelleneneAdres));
        //adresList list idi toString() ile string yapip contains yapiyoruz
    }


    //*******************************************************************************************************


    @Given("Verilen datalar ile query hazirlanip sorgu gerceklestirilir.")
    public void verilen_datalar_ile_query_hazirlanip_sorgu_gerceklestirilir() throws SQLException {

        String query = "SELECT email FROM u480337000_tlb_training.users WHERE first_name='admin' and last_name='user';";

        resultset = getStatement().executeQuery(query);
    }
    @Given("Donen Result set datasi dogrulanir")
    public void donen_result_set_datasi_dogrulanir() throws SQLException {

        resultset.first();
        String actualEmailData= resultset.getString("email");
        String expectedEmailData= "admin@gmail.com";
        assertEquals(expectedEmailData,actualEmailData);

        System.out.println(resultset.getString("email"));


    }

    //*******************************************************************************************************
    @Then("seller_products tablosundaki  discount_type degeri {string} olan tum product'lari listeler")

    public void seller_products_tablosundaki_discount_type_degeri_olan_tum_product_lari_listeler(String product_name) {
        String query= "SELECT * FROM seller_products WHERE discount_type = 1";
        sellerProducts=getColumnData(query,product_name);

        System.out.println(sellerProducts);


    }

    //******************************************************************************************************

    @Then("languages tablosundan verilen {string} numarasinin native degerinin istenen datayi dondurdugu dogrulanmali.")
    public void languages_tablosundan_verilen_numarasinin_native_degerinin_istenen_datayi_dondurdugu_dogrulanmali(String id ) throws SQLException {
        String query="SELECT native FROM u480337000_tlb_training.languages where id=10";
        String nativeC=getCellValue(query).toString();
        System.out.println(nativeC);

    }













}

