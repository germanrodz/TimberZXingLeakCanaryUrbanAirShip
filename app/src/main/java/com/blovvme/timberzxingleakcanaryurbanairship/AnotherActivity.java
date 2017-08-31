package com.blovvme.timberzxingleakcanaryurbanairship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnotherActivity extends AppCompatActivity {


    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);


        btn = (Button)findViewById(R.id.btn);
    }

    public void click(View view) {

        Intent intent = new Intent(AnotherActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
