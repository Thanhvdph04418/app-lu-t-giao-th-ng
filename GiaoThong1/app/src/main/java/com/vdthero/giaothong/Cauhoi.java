package com.vdthero.giaothong;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Adapter.AdapCauhoi;
import model.ItemCauhoi;

public class Cauhoi extends AppCompatActivity {
    ArrayList<String> li;
    ListView lv1, lv2;
    ArrayList<ItemCauhoi> list;
    AdapCauhoi ad;
     TextView txtload;
     TabHost tabHost;
    AdapCauhoi ad2;
    ArrayList<ItemCauhoi> arr;
    View viewitem;
    TextView txtdapan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cauhoi);

        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        // hiển thị nút Up ở Home icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        addControl();
        addEvent();


    }

       private void addEvent() {

    }


    public String getJson(String url) {
        try {
            URL mUrl = new URL(url);
            InputStreamReader inputStreamReader = new InputStreamReader(mUrl.openStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            return builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    class myTask10 extends AsyncTask<Void, Void, String> {
        String url = "https://chayandroid.000webhostapp.com/indexlt.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();


        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                JSONArray array = new JSONArray(getJson(url));

                for (int i = 0; i < array.length(); i++) {
                    String socau = array.getJSONObject(i).getString("socau");
                    String anh = array.getJSONObject(i).getString("anh");
                    String dapan = array.getJSONObject(i).getString("dapan");

                    ItemCauhoi itch = new ItemCauhoi(socau, anh, dapan);
                    list.add(itch);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtload.setText("");
            ad = new AdapCauhoi(Cauhoi.this, R.layout.item_cauhoi, list);
            lv1.setAdapter(ad);
            ad.notifyDataSetChanged();
        }
    }


    ///////////////////////
    class myTask9 extends AsyncTask<Void, Void, String> {
        String url = "https://chayandroid.000webhostapp.com/indexsh.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arr=new ArrayList<>();



        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                JSONArray array = new JSONArray(getJson(url));

                for (int i = 0; i < array.length(); i++) {
                    String socau = array.getJSONObject(i).getString("socau");
                    String anh = array.getJSONObject(i).getString("anh");
                    String dapan = array.getJSONObject(i).getString("dapan");

                    ItemCauhoi itch = new ItemCauhoi(socau, anh, dapan);
                    arr.add(itch);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ad2=new AdapCauhoi(Cauhoi.this,R.layout.item_cauhoi,arr);
            lv2.setAdapter(ad2);
            txtload.setText("");
            ad2.notifyDataSetChanged();
        }
    }
    ///////////////////


    private void addControl() {
        txtload = (TextView) findViewById(R.id.txtLoad);
        lv1 = (ListView) findViewById(R.id.lvlithuyet);
        lv2 = (ListView) findViewById(R.id.lvsahinh);



        myTask10 mt = new myTask10();
        mt.execute();




        myTask9 mt1 = new myTask9();
        mt1.execute();



        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");

        tab1.setIndicator("Lý thuyết");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");

        tab2.setIndicator("Sa hình");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem mSearch = menu.findItem(R.id.mSearch);
        SearchView searchView = (SearchView) mSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    ad.filter(newText);
                }catch (Exception ex){
                    ad2.filter(newText);
                }

                try {
                    ad2.filter(newText);
                }catch (Exception ex){
                    ad.filter(newText);
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
