package com.example.pradipkokate.e_license;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddRC extends AppCompatActivity {

    Button show;
    EditText mobile_no;
    public static String smobile_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rc);

        show=(Button)findViewById(R.id.show);
        mobile_no=(EditText)findViewById(R.id.mob);


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                smobile_no=mobile_no.getText().toString();
                Intent intent= new Intent(getApplicationContext(),RC1.class);
                startActivity(intent);
            }
        });
    }
}
