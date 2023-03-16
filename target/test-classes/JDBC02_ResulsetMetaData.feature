Feature: ResultsetMetaData methodlari ile JDBC sorgusu calistirma

  # Database baglantisi kurarak staff tablosundaki Id'lerin icinde "5" id numarasina sahip staff var mi kontrol ediniz?
  # SELECT * FROM u480337000_tlb_training.staff;
  @staffID
  Scenario Outline: Staff tablosundan "ID" sorgulama

    * Database baglantisi kurulur.
    * Staff tablosundaki "id" leri listelenir.
    * Verilen "<id>" dogrulanir.
    * Database baglantisi kapatilir.


    Examples:
      |id|
      |5|
      |2|
      |3|
      |4|
      |1|
      |100|
      |10|