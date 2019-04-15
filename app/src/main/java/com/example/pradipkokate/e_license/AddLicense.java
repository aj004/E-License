package com.example.pradipkokate.e_license;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddLicense extends AppCompatActivity {
    EditText mobile;
    Button show,submit;
    public static String smob="";
    int cnt=0;

    EditText rto,licno,doi,validity,atd,address;
    TextView phone,name,dob,swd;
    String srto,slicno,sdoi,svalidity,satd,sadd;

    public static String sname,sswd,sdob,svillage,staluka,sdist,spin;
    public static int pos=0;

    String myJSON;
    JSONArray peoples = null;
    private static final String TAG_RESULTS="result";
    ArrayList<HashMap<String, String>> personList;
    private ProgressDialog progress;
    final Context context=this;
    public static String receivedValue="";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_license);

        show=(Button)findViewById(R.id.show);
        submit=(Button)findViewById(R.id.submit);
        mobile=(EditText)findViewById(R.id.mobile);
        rto=(EditText)findViewById(R.id.rto);
        licno=(EditText)findViewById(R.id.licno);
        doi=(EditText)findViewById(R.id.doi);
        validity=(EditText)findViewById(R.id.val);
        atd=(EditText)findViewById(R.id.atd);
        address = (EditText)findViewById(R.id.add);

        phone = (TextView)findViewById(R.id.phone);
        name = (TextView)findViewById(R.id.name);
        dob = (TextView)findViewById(R.id.dob);
        swd = (TextView)findViewById(R.id.swd);


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                smob=mobile.getText().toString();
                phone.setText(smob);
                getData();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                srto = rto.getText().toString();
                slicno = licno.getText().toString();
                sdoi = doi.getText().toString();
                satd = atd.getText().toString();
                svalidity = validity.getText().toString();

                try {
                    progress=new ProgressDialog(context);
                    progress.setMessage("Wait...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(false);
                    progress.setProgress(0);
                    progress.setCancelable(false);
                    progress.show();
                    new AddLicense.addUser().execute();

                }
                catch (Exception ex)
                {
                    Toast.makeText(context, "Error="+ex.toString(), Toast.LENGTH_SHORT).show();
                }




            }
        });
    }





    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost(url.url+"/getData.php?m="+smob);

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
                sname=( c.getString("v1"));
                sswd=( c.getString("v2"));
                sdob=( c.getString("v3"));
                svillage=( c.getString("v4"));
                staluka=( c.getString("v5"));
                sdist=( c.getString("v6"));
                spin=( c.getString("v7"));

                name.setText(sname);
                dob.setText(sdob);
                swd.setText(sswd);

                sadd = svillage+" "+staluka+" "+sdist;
                address.setText(sadd);
            }

            Toast.makeText(context, "Address :"+svillage+""+staluka+""+sdist, Toast.LENGTH_SHORT).show();
        } catch (Exception  e) {
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private class addUser extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost(url.url+"/addLicense.php");
            //temp=params[0];

            //uid,nm,mob,add,mail,pass,cpass;
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("e1",srto));
            pairs.add(new BasicNameValuePair("e2",smob));
            pairs.add(new BasicNameValuePair("e3",slicno));
            pairs.add(new BasicNameValuePair("e4",sdoi));
            pairs.add(new BasicNameValuePair("e5",svalidity));
            pairs.add(new BasicNameValuePair("e6",satd));
            pairs.add(new BasicNameValuePair("e7",sname));
            pairs.add(new BasicNameValuePair("e8",sdob));
            pairs.add(new BasicNameValuePair("e9",sswd));
            pairs.add(new BasicNameValuePair("e10",sadd));
            try
            {
                post.setEntity(new UrlEncodedFormEntity(pairs));
            }
            catch (Exception ex)
            {
                //e1=ex.toString();
                Toast.makeText(getApplicationContext(), "Error 1="+ex.toString(), Toast.LENGTH_SHORT).show();
            }
            //Perform HTTP Request
            try
            {
                ResponseHandler<String> responseHandler=new BasicResponseHandler();
                receivedValue =client.execute(post,responseHandler);
                //Toast.makeText(getApplicationContext(), receivedValue, Toast.LENGTH_SHORT).show();

            }
            catch (Exception ex)
            {
                // e2=ex.toString();
                //Toast.makeText(getApplicationContext(), "Error 2="+ex.toString(), Toast.LENGTH_SHORT).show();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progress.dismiss();
            Toast.makeText(context, receivedValue, Toast.LENGTH_SHORT).show();
            if(receivedValue.contains("exists"))
            {
                Toast.makeText(context, "UserId Already Exists", Toast.LENGTH_SHORT).show();
            }
            else if(receivedValue.contains("success"))
            {
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
                finish();
            }
        }

        @Override
        protected void onPreExecute()
        {


            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }
    }



}


