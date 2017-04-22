package com.cmpe277.datastorage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        if(searchText.isEmpty())
            return;
      ListView listView = ((ListView)findViewById(R.id.listview));
      StringBuilder searchResult=new StringBuilder(50);

        final ArrayList<Product> productArrayList = new ArrayList<Product>();
      DbController db = new DbController(getBaseContext());
      db.open();
      try(Cursor cu= db.searchByName( searchText)){//this try (with resrouce) available from API 19+
        while(cu.moveToNext()){
          //TODO display result on main activity list;
            int rowid=cu.getColumnIndex("ProductId");
            Product product = new Product(cu.getInt(rowid), cu.getString(1)
                    ,cu.getString(2),cu.getString(4),cu.getDouble(3));
            productArrayList.add(product);
        }
      }
      db.close();
        ListProductArrayAdapter adapter = new ListProductArrayAdapter(this
                ,R.layout.list_product_item,productArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Product product = (Product)parent.getItemAtPosition(position);
                view.animate().setDuration(1000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getBaseContext(),NewProductActivity.class);
                        intent.putExtra("Id",product.id);
                        intent.putExtra("Name", product.name);
                        intent.putExtra("Description", product.description);
                        intent.putExtra("Price", product.price);
                        intent.putExtra("Review", product.review);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void onNewProduct(View view){
        Intent newProduct = new Intent(this,NewProductActivity.class);
        startActivity(newProduct);
    }

    public void onClear(View view){
        //((TextView)findViewById(R.id.txt_result)).setText("");
        ((TextView)findViewById(R.id.edt_search)).setText("");
    }

    private class ListProductArrayAdapter extends ArrayAdapter<Product>{
        HashMap<Product,Integer> integerProductHashMap = new HashMap<>();
        int layoutId;
        public ListProductArrayAdapter(@NonNull Context context, @LayoutRes int resource
                , @NonNull List<Product> objects) {
            super(context, resource, objects);
            layoutId=resource;
            for(Product product:objects){
                integerProductHashMap.put(product,product.id);
            }
        }

        @Override
        public long getItemId(int position) {
            Product product = getItem(position);
            return integerProductHashMap.get(product);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View rowView = inflater.inflate(layoutId,parent,false);

            TextView title = (TextView)rowView.findViewById(R.id.firstLine);
            TextView desc = (TextView)rowView.findViewById(R.id.secondLine);
            title.setText(getItem(position).name);
            desc.setText(getItem(position).description);
            /*change row background color by odd and even*/
            return rowView;
        }
    }
  }
