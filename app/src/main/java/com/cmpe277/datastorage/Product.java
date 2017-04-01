package com.cmpe277.datastorage;

/**
 * Created by tranpham on 3/26/17.
 */

public class Product {
    String name="";
    String description="";
    String review="";
    Double price=0.0;

    Product(String _name, String _desc, String _review, Double _price){
      name=_name;
      description=_desc;
      review=_review;
      price=_price;
    }
}
