package com.cmpe277.datastorage;

/**
 * Created by tranpham on 3/26/17.
 */

public class Product {
    Integer id;
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

    Product(Integer _id, String _name, String _desc, String _review, Double _price){
        id=_id;
        name=_name;
        description=_desc;
        review=_review;
        price=_price;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(
                String.format("Item name:%s'\nItem Description:%s'\nPrice'\n",name,description,price));

        if(!review.isEmpty()){
            builder.append("'\nReview:"+review);
        }

        return builder.toString();
    }
}
