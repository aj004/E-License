package com.example.pradipkokate.e_license;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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



import static android.R.id.progress;

public class Signup extends AppCompatActivity {

    Button reg;
    EditText username,name,father,dob,village,taluka,dist,pincode,pwd,cpwd;
    String Susername,Sname,Sfather,Sdob,Svillage,Staluka,Sdist,Spincode,Spwd,Scpwd,snum;

    private ProgressDialog progress;
    final Context context=this;
    String receivedValue="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        reg = (Button)findViewById(R.id.register);

        username = (EditText)findViewById(R.id.username);
        name = (EditText)findViewById(R.id.name);
        father = (EditText)findViewById(R.id.father);
        dob = (EditText)findViewById(R.id.dob);
        village = (EditText)findViewById(R.id.village);
        taluka = (EditText)findViewById(R.id.taluka);
        dist = (EditText)findViewById(R.id.dist);
        pincode = (EditText)findViewById(R.id.pincode);
        pwd = (EditText)findViewById(R.id.pwd);
        cpwd = (EditText)findViewById(R.id.cpwd);



       username.setText(OTP.no);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Susername = username.getText().toString();
                Sname = name.getText().toString();
                Sfather = father.getText().toString();
                Sdob =dob.getText().toString();
                Svillage = "A/P :"+village.getText().toString();
                Staluka = "Tal :"+taluka.getText().toString();
                Sdist = "Dist :"+dist.getText().toString();
                Spincode = pincode.getText().toString();
                Spwd = pwd.getText().toString();
                Scpwd = cpwd.getText().toString();


                if (Susername.equals("")){
                    username.setError("Enter Name");
                    Snackbar.make(view, "Enter Name", Snackbar.LENGTH_LONG)
                            .show();
                }else if(Sname.equals("")){
                    name.setError("Check name");
                    Snackbar.make(view, "Check Mobile no", Snackbar.LENGTH_LONG)
                            .show();
                }else if (Sfather.equals("")){
                    father.setError("Enter Father/Husband name");
                    Snackbar.make(view, "Enter Address", Snackbar.LENGTH_LONG)
                            .show();
                }else if(Sdob.equals("")){
                    dob.setError("Check Date of birth");
                    Snackbar.make(view, "Check Email Id", Snackbar.LENGTH_LONG)
                            .show();
                }
                else if(Svillage.equals("")){
                    village.setError("Check Village name");
                    Snackbar.make(view, "Password Should not be less than 4 char", Snackbar.LENGTH_LONG)
                            .show();
                } else if(Staluka.equals("")){
                    taluka.setError("Check Taluka name");
                    Snackbar.make(view, "Password Should not be less than 4 char", Snackbar.LENGTH_LONG)
                            .show();
                }
                else if(Sdist.equals("")){
                    dist.setError("Check District name");
                    Snackbar.make(view, "Password Should not be less than 4 char", Snackbar.LENGTH_LONG)
                            .show();
                }
                else if(Spincode.equals("")){
                    pincode.setError("Check Pincode");
                    Snackbar.make(view, "Password Should not be less than 4 char", Snackbar.LENGTH_LONG)
                            .show();
                }
                else if (!Spwd.equals(Scpwd)){
                    cpwd.setError("Enter correct password");
                    Snackbar.make(view, "Enter correct password", Snackbar.LENGTH_LONG)
                            .show();
                }else{



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



            }
        });

    }

    private class addUser extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost(url.url+"/addUser.php");
            //temp=params[0];

            //uid,nm,mob,add,mail,pass,cpass;
            List<NameValuePair> pairs=new ArrayList<NameValuePair>(1);
            pairs.add(new BasicNameValuePair("username",Susername));
            pairs.add(new BasicNameValuePair("name",Sname));
            pairs.add(new BasicNameValuePair("father",Sfather));
            pairs.add(new BasicNameValuePair("dob",Sdob));
            pairs.add(new BasicNameValuePair("village",Svillage));
            pairs.add(new BasicNameValuePair("taluka",Staluka));
            pairs.add(new BasicNameValuePair("dist",Sdist));
            pairs.add(new BasicNameValuePair("pincode",Spincode));
            pairs.add(new BasicNameValuePair("pwd",Spwd));
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
