package com.example.pradipkokate.e_license;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pradipkokate.e_license.ViewLicense1;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewLicense extends AppCompatActivity {

    TextView t2,t4,t6,t8,t10,t12,t14,t16;
    public static String st2,st4,st6,st8,st10,st12,st14,st16;




    String myJSON;
    JSONArray peoples = null;
    private static final String TAG_RESULTS="result";
    ArrayList<HashMap<String, String>> personList;
    private ProgressDialog progress;
    final Context context=this;
    public static String receivedValue="";
    public static int pos=0;
    public static String smob="";
    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_license);

        t2=(TextView)findViewById(R.id.t2);
        t4=(TextView)findViewById(R.id.t4);
        t6=(TextView)findViewById(R.id.t6);
        t8=(TextView)findViewById(R.id.t8);
        t10=(TextView)findViewById(R.id.t10);
        t12=(TextView)findViewById(R.id.t12);
        t14=(TextView)findViewById(R.id.t14);
        t16=(TextView)findViewById(R.id.t16);

        getData();
    }


    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost(url.url+"/getDataforViewLic.php?m="+ ViewLicense1.smob);

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                }
                catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                // Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }


    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            for(int i=0;i<peoples.length();i++){
                cnt++;
            }
            for(int i=0;i<peoples.length();i++){
                cnt++;
                JSONObject c = peoples.getJSONObject(i);
                st2=( c.getString("v1"));
                st4=( c.getString("v2"));
                st6=( c.getString("v3"));
                st8=( c.getString("v4"));
                st10=( c.getString("v5"));
                st12=( c.getString("v6"));
                st14=( c.getString("v7"));
                st16=( c.getString("v8"));

                t2.setText(st2);
                t4.setText(st4);
                t6.setText(st6);
                t8.setText(st8);
                t10.setText(st10);
                t12.setText(st12);
                t14.setText(st14);
                t16.setText(st16);



            }


        } catch (Exception  e) {
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
