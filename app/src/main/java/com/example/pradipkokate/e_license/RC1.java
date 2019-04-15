package com.example.pradipkokate.e_license;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RC1 extends AppCompatActivity {

    EditText name_r,swd_r,addr_r,ch_no,e_sno,mfr,o_sno,regd_upto,vhe_cl,model,no_of_cyl,mfg_dt,fuel,color,cu_cap,wheel_base,unladen_wt,seating_c;
    public static String sname_r,sswd_r,saddr_r,sch_no,se_sno,smfr,so_sno,sregd_upto,svhe_cl,smodel,sno_of_cyl,smfg_dt,sfuel,scolor,scu_cap,swheel_base,sunladen_wt,sseating_c;
    public static String svillage_r,staluka_r,sdist_r,spin_r;
    Button Register_r;

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
        setContentView(R.layout.activity_rc1);

        name_r=(EditText)findViewById(R.id.name_r);
        swd_r=(EditText)findViewById(R.id.swd_r);
        addr_r=(EditText)findViewById(R.id.addr_r);
        ch_no=(EditText)findViewById(R.id.ch_no);
        e_sno=(EditText)findViewById(R.id.e_sno);
        mfr=(EditText)findViewById(R.id.mfr);
        o_sno=(EditText)findViewById(R.id.o_sno);
        regd_upto=(EditText)findViewById(R.id.regd_upto);
        vhe_cl=(EditText)findViewById(R.id.vhe_cl);
        model=(EditText)findViewById(R.id.model);
        no_of_cyl=(EditText)findViewById(R.id.no_of_cyl);
        mfg_dt=(EditText)findViewById(R.id.mfg_dt);
        fuel=(EditText)findViewById(R.id.fuel);
        color=(EditText)findViewById(R.id.color);
        cu_cap=(EditText)findViewById(R.id.cu_cap);
        wheel_base=(EditText)findViewById(R.id.wheel_base);
        unladen_wt=(EditText)findViewById(R.id.unladen_wt);
        seating_c=(EditText)findViewById(R.id.seating_c);
        Register_r=(Button)findViewById(R.id.Register_r) ;

        getData();

        Register_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                sname_r=name_r.getText().toString();
                sswd_r=swd_r.getText().toString();
                saddr_r=addr_r.getText().toString();
                smodel=model.getText().toString();
                sno_of_cyl=no_of_cyl.getText().toString();
                smodel=model.getText().toString();
                smfg_dt=mfg_dt.getText().toString();
                sfuel=fuel.getText().toString();
                scolor=color.getText().toString();
                scu_cap=cu_cap.getText().toString();
                swheel_base=wheel_base.getText().toString();
                sunladen_wt=unladen_wt.getText().toString();
                sseating_c=seating_c.getText().toString();
                so_sno=o_sno.getText().toString();
                sregd_upto=regd_upto.getText().toString();
                svhe_cl=vhe_cl.getText().toString();
                sch_no=ch_no.getText().toString();
                se_sno=e_sno.getText().toString();
                smfr=mfr.getText().toString();



                try {
                    progress=new ProgressDialog(context);
                    progress.setMessage("Wait...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(false);
                    progress.setProgress(0);
                    progress.setCancelable(false);
                    progress.show();
                    new addUser().execute();

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
                HttpPost httppost = new HttpPost(url.url+"/getData.php?m="+AddRC.smobile_no);

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
                sname_r=( c.getString("v1"));
                sswd_r=( c.getString("v2"));

                svillage_r=( c.getString("v4"));
                staluka_r=( c.getString("v5"));
                sdist_r=( c.getString("v6"));
                spin_r=( c.getString("v7")); 

                name_r.setText(sname_r);
                swd_r.setText(sswd_r);

                saddr_r = svillage_r+" "+staluka_r+" "+sdist_r+" "+ spin_r;
                addr_r.setText(saddr_r);//the address the name and the swd is set now once the mobile number is entered
            }

            Toast.makeText(context, "Address :"+svillage_r+""+staluka_r+""+sdist_r, Toast.LENGTH_SHORT).show();
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
            HttpPost post=new HttpPost(url.url+"/addRC.php");//change the php name
            //temp=params[0];

            //uid,nm,mob,add,mail,pass,cpass;
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("e1",sname_r));
            pairs.add(new BasicNameValuePair("e2",sswd_r));
            pairs.add(new BasicNameValuePair("e3",saddr_r));
            pairs.add(new BasicNameValuePair("e4",sch_no));
            pairs.add(new BasicNameValuePair("e5",se_sno));
            pairs.add(new BasicNameValuePair("e6",smfr));
            pairs.add(new BasicNameValuePair("e7",so_sno));
            pairs.add(new BasicNameValuePair("e8",sregd_upto));
            pairs.add(new BasicNameValuePair("e9",svhe_cl));
            pairs.add(new BasicNameValuePair("e10",smodel));
            pairs.add(new BasicNameValuePair("e11",sno_of_cyl));
            pairs.add(new BasicNameValuePair("e12",smfg_dt));
            pairs.add(new BasicNameValuePair("e13",sfuel));
            pairs.add(new BasicNameValuePair("e14",scolor));
            pairs.add(new BasicNameValuePair("e15",scu_cap));
            pairs.add(new BasicNameValuePair("e16",swheel_base));
            pairs.add(new BasicNameValuePair("e17",sunladen_wt));
            pairs.add(new BasicNameValuePair("e18",sseating_c));



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
                Intent i=new Intent(getApplicationContext(),Login.class);
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
