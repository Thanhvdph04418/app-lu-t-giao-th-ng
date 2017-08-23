package com.vdthero.giaothong;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import Adapter.Adap1;
import Adapter.Adap2;
import Adapter.AdapVideo;
import model.CardVideo;
import model.bienbao;
import model.coban;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ListView lv1;
    ArrayList<coban> list;
    Adap1 ad;
    ProgressDialog dialog;
    ArrayList<bienbao> listbb;
    Adap2 ad2;
    AdapVideo ad3;
    ArrayList<CardVideo> arrVideo;
    ViewFlipper viewFlipper;

    Animation in,out;
    TextView txtThua;
    ProfilePictureView profilePictureView;
    LoginButton loginButton;
    TextView textfb;
    CallbackManager callbackManager;
    String name,idanh;
    View header;
    ShareDialog shareDialog;
    ShareLinkContent shareLinkContent;
    String luutru="LUU";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager=CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        shareDialog=new ShareDialog(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        addcontrol2();
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        addControl();
        addEvent();
    }

    private void addcontrol2() {
        profilePictureView= (ProfilePictureView)header. findViewById(R.id.friendProfilePicture);
        loginButton= (LoginButton) header.findViewById(R.id.login_button);
        textfb= (TextView)header. findViewById(R.id.txtfb);
        setLogin();
    }

    private void setLogin() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                    result();
                    loginButton.setVisibility(View.GONE);
                    textfb.setVisibility(View.VISIBLE);



            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void result() {
        GraphRequest graphRequest=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON",response.getJSONObject().toString());

                try {

                    idanh=object.getString("id");
                    name=object.getString("name");
                    SharedPreferences preferences=getSharedPreferences(luutru,MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("anh",idanh);
                    editor.putString("ten",name);
                    editor.commit();

                    profilePictureView.setProfileId(idanh);
                    textfb.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void addEvent() {
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MainActivity.this, content.class);
                it.putExtra("tieude", list.get(position).getTieude());
                it.putExtra("noidung", list.get(position).getNoidung());
                startActivity(it);
            }
        });

        profilePictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginButton.getVisibility()== View.VISIBLE){
                    loginButton.setVisibility(View.GONE);
                }else {
                    loginButton.setVisibility(View.VISIBLE);
                }


            }


        });

    }



    private void addControl() {
        SharedPreferences preferences=getSharedPreferences(luutru,MODE_PRIVATE);
        String anhdaluu=preferences.getString("anh","");
        String ten=preferences.getString("ten","");

        if(anhdaluu.length()>0 && ten.length() >0){
            profilePictureView.setProfileId(anhdaluu);
            textfb.setText(ten);
        }

        loginButton.setVisibility(View.GONE);


        txtThua= (TextView) findViewById(R.id.textthua);
        viewFlipper = (ViewFlipper) findViewById(R.id.view1);
        in = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
        out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        txtThua.setVisibility(View.GONE);



        dialog = new ProgressDialog(this);
        dialog.setTitle("Xin Chờ");
        dialog.setMessage("Đang kết nối....");
        lv1 = (ListView) findViewById(R.id.lv1);

        myTask mt = new myTask();
        mt.execute();








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

    class myTask extends AsyncTask<Void, Void, String> {
        String url = "https://chayandroid.000webhostapp.com/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
            list = new ArrayList<>();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                JSONArray array = new JSONArray(getJson(url));

                for (int i = 0; i < array.length(); i++) {
                    String tieude = array.getJSONObject(i).getString("tieude");
                    String noidung = array.getJSONObject(i).getString("noidung");

                    coban ct = new coban(tieude, noidung);
                    list.add(ct);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            ad = new Adap1(MainActivity.this, R.layout.item1, list);
            lv1.setAdapter(ad);
            ad.notifyDataSetChanged();
        }
    }

    /////////////////////////////////
    class myTask3 extends AsyncTask<Void, Void, String> {
        String url = "https://thanhvdph04418.000webhostapp.com/index3.php";

        @Override
        protected void onPreExecute() {
            listbb = new ArrayList<>();
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                JSONArray array = new JSONArray(getJson(url));

                for (int i = 0; i < array.length(); i++) {
                    String anh = array.getJSONObject(i).getString("anh");
                    String noidung = array.getJSONObject(i).getString("noidung");

                    bienbao bb = new bienbao(anh, noidung);
                    listbb.add(bb);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ad2 = new Adap2(MainActivity.this, R.layout.item2, listbb);
            lv1.setAdapter(ad2);
            dialog.dismiss();
            ad2.notifyDataSetChanged();
        }
    }

    ///////////////////////////////////
    class myTask4 extends AsyncTask<Void, Void, String> {
        String url = "https://chayandroid.000webhostapp.com/index4.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrVideo = new ArrayList<>();
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                JSONArray array = new JSONArray(getJson(url));

                for (int i = 0; i < array.length(); i++) {
                    String anh = array.getJSONObject(i).getString("anh");
                    String tieude = array.getJSONObject(i).getString("tieude");
                    String mota = array.getJSONObject(i).getString("mota");
                    String idvideo = array.getJSONObject(i).getString("idvideo");
                    CardVideo cv = new CardVideo(anh, tieude, mota, idvideo);
                    arrVideo.add(cv);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            ad3=new AdapVideo(MainActivity.this,R.layout.itemvideo,arrVideo);
            lv1.setAdapter(ad3);
            ad3.notifyDataSetChanged();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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


//                try {
//                    ad3.filter(newText);
//                }catch (Exception ex){
////                    ad2.filter(newText);
//                }
//                try {
////                    ad2.filter(newText);
//                }catch (Exception ex){
//                    ad3.filter(newText);
//                }

                try {
                    ad3.filter(newText);
                }catch (Exception ex){
                    ad.filter(newText);
                }

                try {
                    ad.filter(newText);
                }catch (Exception ex){
                    ad3.filter(newText);
                }

                return false;
            }
        });
        return true;
    }




    public boolean onCreateOptionsMenu2(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
                ad2.filter(newText);

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
        if (id == R.id.action_thoat) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thoát");
            builder.setMessage("Bạn có thật sự muốn thoát");
            builder.setCancelable(false);
            builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.show();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navtt) {
            viewFlipper.setVisibility(View.VISIBLE);
            txtThua.setVisibility(View.GONE);

            class myTask2 extends AsyncTask<Void, Void, String> {
                String url = "https://chayandroid.000webhostapp.com/index2.php";

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    list=new ArrayList<>();
                    dialog.show();
                    ad.clear();
                }

                @Override
                protected String doInBackground(Void... params) {
                    try {
                        JSONArray array = new JSONArray(getJson(url));

                        for (int i = 0; i < array.length(); i++) {
                            String tieude = array.getJSONObject(i).getString("tieude");
                            String noidung = array.getJSONObject(i).getString("noidung");

                            coban ct = new coban(tieude, noidung);
                            list.add(ct);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    dialog.dismiss();
                    ad=new Adap1(MainActivity.this,R.layout.item1,list);
                    lv1.setAdapter(ad);
                    ad.notifyDataSetChanged();
                }
            }
            myTask2 mt = new myTask2();
            mt.execute();

            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent(MainActivity.this, content.class);
                    it.putExtra("tieude", list.get(position).getTieude());
                    it.putExtra("noidung", list.get(position).getNoidung());
                    startActivity(it);
                }
            });


        } else if (id == R.id.nav_home) {
            viewFlipper.setVisibility(View.VISIBLE);
            txtThua.setVisibility(View.GONE);
            ad.clear();
            myTask m = new myTask();
            m.execute();
            lv1.setAdapter(ad);
            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent(MainActivity.this, content.class);
                    it.putExtra("tieude", list.get(position).getTieude());
                    it.putExtra("noidung", list.get(position).getNoidung());
                    startActivity(it);
                }
            });


        } else if (id == R.id.nav_a1) {
            viewFlipper.setVisibility(View.GONE);
            txtThua.setVisibility(View.VISIBLE);
            myTask3 mt3 = new myTask3();
            mt3.execute();
            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                }
            });

        } else if (id == R.id.nav_a2) {
            viewFlipper.setVisibility(View.GONE);
            txtThua.setVisibility(View.VISIBLE);
            myTask4 mt4 = new myTask4();
            mt4.execute();

            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent iten = new Intent(MainActivity.this, VideoView.class);
                    iten.putExtra("idvideo", arrVideo.get(position).getIdvideo());
                    startActivity(iten);
                }
            });

        } else if (id == R.id.nav_b1) {


            Intent iten1 = new Intent(MainActivity.this, Cauhoi.class);
            startActivity(iten1);

        } else if (id == R.id.nav_share) {
              if(ShareDialog.canShow(ShareLinkContent.class)){
                  shareLinkContent=new ShareLinkContent.Builder().setContentTitle("ung dung luat giao thong")
                          .setContentDescription("ung dung hoc luat giao thong")
                          .setContentUrl(Uri.parse("https://sharecode.vn/source-code/app-luat-giao-thong-listview-nang-cao-va-co-banjson-13455.htm")).build();
              }
              shareDialog.show(shareLinkContent);


        } else if (id == R.id.nav_About) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông tin app");

            builder.setMessage("Phát triển bởi : Vũ Duy Thanh \n Phiên bản : 1.0.12 \n Ngày phát hành:16-07-2017 \n Giấy phép số: 1421 \n Phiên bản mới nhất cập nhật ngày :16-07-2017");
            builder.setCancelable(true);


            builder.show();



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
