package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOnClick(View v){
        // Get the text view.
        TextView showCountTextView = (TextView)
                findViewById(R.id.HelloWorld);

        // Display the new value in the text view.
        showCountTextView.setText("How are you");

        // Get the text view.
        Button showButton1 = (Button)
                findViewById(R.id.button);

        // Display the new value in the text view.
        showButton1.setText("Cool");


    }
    /**
     * Show a toast
     * @param view -- the view that is clicked
     */
    public void toastMe(View view){
        // Toast myToast = Toast.makeText(this, message, duration);
        Toast myToast = Toast.makeText(this, "Hello Toast!",
                Toast.LENGTH_SHORT);
        myToast.show();
    }
}
