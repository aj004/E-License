package com.example.pradipkokate.e_license;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

public class ViewRC extends AppCompatActivity {
    String myJSON;
    JSONArray peoples = null;
    final Context context=this;
    public static String receivedValue="";
    private static final String TAG_RESULTS = "result";
    static public int cnt = 0;
    TextView chno, osno, esno, regd, mfr, vhe, name, swd, address, model, cucap, noc, wheel, mfg, unladen, fuel, seating, color;
    String Schno, Sosno, Sesno, Sregd, Smfr, Svhe, Sname, Sswd, Saddress, Smodel, Scucap, Snoc, Swheel, Smfg, Sunladen, Sfuel, Sseating, Scolor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rc);

        chno = (TextView) findViewById(R.id.mob);
        osno = (TextView) findViewById(R.id.osno);
        esno = (TextView) findViewById(R.id.esno);
        regd = (TextView) findViewById(R.id.regd);
        mfr = (TextView) findViewById(R.id.mfr);
        vhe = (TextView) findViewById(R.id.vhecl);
        name = (TextView) findViewById(R.id.name);
        swd = (TextView) findViewById(R.id.swd);
        address = (TextView) findViewById(R.id.address);
        model = (TextView) findViewById(R.id.model);
        cucap = (TextView) findViewById(R.id.cucap);
        noc = (TextView) findViewById(R.id.noc);
        wheel = (TextView) findViewById(R.id.wheel);
        mfg = (TextView) findViewById(R.id.mfg);
        unladen = (TextView) findViewById(R.id.unladen);
        fuel = (TextView) findViewById(R.id.fuel);
        seating = (TextView) findViewById(R.id.seating);
        color = (TextView) findViewById(R.id.color);

        getData();

    }
    public void getData() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost(url.url + "/getRC.php?m=" + ViewRC1.sch_no);

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
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                } finally {
                    try {
                        if (inputStream != null) inputStream.close();
                    } catch (Exception squish) {
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                // Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            for (int i = 0; i < peoples.length(); i++) {
                cnt++;
            }
            for (int i = 0; i < peoples.length(); i++) {
                cnt++;
                JSONObject c = peoples.getJSONObject(i);
                Sname = (c.getString("v1"));
                Sswd = (c.getString("v2"));
                Saddress = (c.getString("v3"));
                Schno = (c.getString("v4"));
                Sesno = (c.getString("v5"));
                Smfr = (c.getString("v6"));
                Sosno = (c.getString("v7"));
                Sregd = (c.getString("v8"));
                Svhe = (c.getString("v9"));
                Smodel = (c.getString("v10"));
                Snoc = (c.getString("v11"));
                Smfg = (c.getString("v12"));
                Sfuel = (c.getString("v13"));
                Scolor = (c.getString("v14"));
                Scucap = (c.getString("v15"));
                Swheel = (c.getString("v16"));
                Sunladen = (c.getString("v17"));
                Sseating = (c.getString("v18"));


                name.setText(Sname);
                swd.setText(Sswd);
                address.setText(Saddress);
                chno.setText(Schno);
                esno.setText(Sesno);
                mfr.setText(Smfr);
                osno.setText(Sosno);
                regd.setText(Sregd);
                vhe.setText(Svhe);
                model.setText(Smodel);
                noc.setText(Snoc);
                mfg.setText(Smfg);
                fuel.setText(Sfuel);
                color.setText(Scolor);
                cucap.setText(Scucap);
                wheel.setText(Swheel);
                unladen.setText(Sunladen);
                seating.setText(Sseating);

            }

            Toast.makeText(context, "Address :"+Schno+""+Sunladen+""+Swheel, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


}


