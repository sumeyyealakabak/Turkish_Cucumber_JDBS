Feature: tum productları listeleme

  # # SELECT * FROM u480337000_tlb_training.staff; SELECT * FROM seller_products WHERE discount_type = 1;
  # Database üzerinden seller_products tablosundaki discount_type degeri (1) olan tüm product'lari listeleyin.


  @soru
  Scenario:
    * Database baglantisi kurulur.
    * seller_products tablosundaki  discount_type degeri "product_name" olan tum product'lari listeler
    * Database baglantisi kapatilir.