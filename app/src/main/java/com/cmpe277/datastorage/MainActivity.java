package com.cmpe277.datastorage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSearchProduct(View view){
      String searchText=((EditText)findViewById(R.id.edt_search)).getText().toString();
        StringBuilder searchResult=new StringBuilder(50);

      DbController db = new DbController(getBaseContext());
      db.open();
      try(Cursor cu= db.searchByName( searchText)){//this try (with resrouce) available from API 19+
        while(cu.moveToNext()){
          //TODO display result on main activity list;
            searchResult.append(cu.getString(0)+"\n");//name
            searchResult.append(cu.getString(1)+"\n");//description
            searchResult.append(cu.getString(2)+"\n");//price
            searchResult.append(cu.getString(3)+"\n");//review
        }
      }
      db.close();
        ((TextView)findViewById(R.id.txt_result)).setText(searchResult.toString());
    }

    public void onNewProduct(View view){
        Intent newProduct = new Intent(this,NewProductActivity.class);
        startActivity(newProduct);
    }
  }
