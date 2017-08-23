package com.vdthero.giaothong;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AlteredCharSequence;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class welcom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        ckeckInternet();


    }

    void showdialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Không có kết nối mạng");
        builder.setMessage("Kiểm tra đường truyền và khỏi động lại app");
        builder.setCancelable(false);
        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    private void ckeckInternet() {

        ConnectivityManager cn = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nif = cn.getActiveNetworkInfo();
        if (nif != null && nif.isConnected()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(welcom.this, MainActivity.class);
                    welcom.this.startActivity(i);
                    welcom.this.finish();
                }

            }, 1000);

        }
        else {
            showdialog();

        }
    }



}
