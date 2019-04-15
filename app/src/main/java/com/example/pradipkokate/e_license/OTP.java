package com.example.pradipkokate.e_license;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OTP extends AppCompatActivity {

    Button b1,b2;
    Integer integer1;
    EditText mobile,verify;
    String o;
    public static  String no="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        try {
            b1 = (Button) findViewById(R.id.continue1);
            b2 = (Button) findViewById(R.id.verify);
            mobile = (EditText) findViewById(R.id.mob);
            verify = (EditText) findViewById(R.id.OTP);

            b1.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    try {
                        no = mobile.getText().toString();
                        Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_WEEK);
                        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
                        int day2 = calendar.get(Calendar.SECOND);
                        o = "" + ((day * 100 + day1 * day2) * day2 * day2);
                        try {

                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(mobile.getText().toString(), null, "Your OTP for RTO APP : " + o, null, null);
                            Toast.makeText(getApplicationContext(), "You will receive OTP", Toast.LENGTH_SHORT).show();

                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Please Enable SEND SMS Permission", Toast.LENGTH_SHORT).show();
                        }
                        verify.setText(o);
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Error=" + ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (verify.getText().toString().equals(o)) {

                        Intent i = new Intent(getApplicationContext(), Signup.class);
                        startActivity(i);
                        finish();
                    } else {
                        verify.setError("Incorrect OTP");
                        Toast.makeText(getApplicationContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Error="+ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}