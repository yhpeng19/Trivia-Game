package com.example.alex1.experiment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void CounterApp(View view) {

    }


    public void fibcalc(View view) {

    }

    public void hello(View view) {
        //showToast();
        Toast.makeText(this, "Hello World!", Toast.LENGTH_SHORT).show();
    }
}
