package com.cmpe277.datastorage;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void onSaveProduct(View view){
      String name=((EditText)findViewById(R.id.edt_name)).getText().toString();
      String desc=((EditText)findViewById(R.id.edt_desc)).getText().toString();
      String price=((EditText)findViewById(R.id.edt_price)).getText().toString();
      String review=((EditText)findViewById(R.id.edt_review)).getText().toString();

      DbController db = new DbController(getBaseContext());
      db.open();
        try {
            db.insert(new Product(name,desc,review,Double.parseDouble(price)));
            ((TextView)findViewById(R.id.txt_log)).setText("New Item added!");
        }
        catch (SQLiteException ex)
        {
            ex.printStackTrace();
            ((TextView)findViewById(R.id.txt_log)).setText(ex.toString());
        }

      db.close();
    }

    public void onCancel(View view){
        this.finish();
    }
}
