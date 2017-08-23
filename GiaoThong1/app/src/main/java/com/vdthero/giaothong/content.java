package com.vdthero.giaothong;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Thanh-PC on 7/12/2017.
 */

public class content extends AppCompatActivity {
    TextView txt1,txt2;
    LinearLayout lo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar =  getSupportActionBar();
        // hiển thị nút Up ở Home icon
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.content);
        lo1= (LinearLayout) findViewById(R.id.lo1);
        txt1 = (TextView) findViewById(R.id.txttieude);
        txt2= (TextView) findViewById(R.id.txtnd);

        Intent intent=getIntent();
        String tieude=intent.getStringExtra("tieude");
        String noidung=intent.getStringExtra("noidung");

       txt1.setText(tieude);
        txt2.setText(noidung);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return super.onCreateOptionsMenu(menu);
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

        if (id == R.id.action_zoom1) {

            txt2.setTextSize(23);
            return true;
        }

        if (id == R.id.action_zoom2) {

            txt2.setTextSize(15);
            return true;
        }

        if (id == R.id.dem) {
            lo1.setBackgroundColor(Color.BLACK);
            txt2.setTextColor(getResources().getColor(R.color.nightText));
            return true;
        }

        if (id == R.id.demoff) {
            lo1.setBackgroundColor(Color.WHITE);
            txt2.setTextColor(getResources().getColor(R.color.nightTextoff));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}