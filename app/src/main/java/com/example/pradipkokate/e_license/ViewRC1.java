package com.example.pradipkokate.e_license;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewRC1 extends AppCompatActivity {

    Button show;
    EditText chno;
    public static String sch_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rc);

        show=(Button)findViewById(R.id.show);
        chno=(EditText)findViewById(R.id.mob);
        Toast.makeText(this, "in viewrc1", Toast.LENGTH_SHORT).show();


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sch_no=chno.getText().toString();
                Intent intent= new Intent(getApplicationContext(),ViewRC.class);
                startActivity(intent);
            }
        });
    }
}

