package com.example.pradipkokate.e_license;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {
    EditText uname,pwd;
    Button login,signup;

    public static String unameString,pwdString;

    SQLiteDatabase db;
    private ProgressDialog progress;
    final Context context=this;
    public static String receivedValue="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        uname=(EditText)findViewById(R.id.mob);
        pwd=(EditText)findViewById(R.id.osno);
        login=(Button) findViewById(R.id.b1);
        signup=(Button)findViewById(R.id.b2);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                unameString=uname.getText().toString();
                pwdString=pwd.getText().toString();

                if (unameString.equals("")){
                    uname.setError("Enter Username");
                }else if(pwdString.equals("")){
                    pwd.setError("Enter Password");
                }else if(unameString.equals("9999999999") && pwdString.equals("admin"))
                {
                    Intent intent=new Intent(getApplicationContext(),Admin.class);
                    startActivity(intent);
                }
                else{

                    progress=new ProgressDialog(context);
                    progress.setMessage("Wait...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(false);
                    progress.setProgress(0);
                    progress.setCancelable(false);
                    progress.show();
                    new login().execute();
                }


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),OTP.class);
                startActivity(i);
            }
        });
    }


    private class login extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {

            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost(url.url+"/login.php");
            //temp=params[0];
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("e1",unameString));
            pairs.add(new BasicNameValuePair("e2",pwdString));
            try
            {
                post.setEntity(new UrlEncodedFormEntity(pairs));
            }
            catch (Exception ex)
            {
                // e1=ex.toString();
                //  Toast.makeText(getApplicationContext(), "Error 1="+ex.toString(), Toast.LENGTH_SHORT).show();
            }
            //Perform HTTP Request
            try
            {
                ResponseHandler<String> responseHandler=new BasicResponseHandler();
                receivedValue =client.execute(post,responseHandler);
                // Toast.makeText(getApplicationContext(), receivedValue, Toast.LENGTH_SHORT).show();
                //name.setText(receivedValue);
            }
            catch (Exception ex)
            {
                // e2=ex.toString();
                // Toast.makeText(getApplicationContext(), "Error 2="+ex.toString(), Toast.LENGTH_SHORT).show();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progress.dismiss();

            // Toast.makeText(context, "E1="+e1, Toast.LENGTH_SHORT).show();
            //  Toast.makeText(context, "E2="+e2, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, receivedValue, Toast.LENGTH_SHORT).show();
            if(receivedValue.contains("wro"))
            {
                Toast.makeText(context, "Invalid Authentication", Toast.LENGTH_SHORT).show();
            }
            else if(receivedValue.contains("success"))
            {
                Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
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
