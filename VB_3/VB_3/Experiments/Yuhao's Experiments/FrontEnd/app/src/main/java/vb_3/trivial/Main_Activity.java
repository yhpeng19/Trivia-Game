package vb_3.trivial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;



public class Main_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        Button SinglePlayer_Button;
        SinglePlayer_Button = findViewById(R.id.singlePlayer_button);
        SinglePlayer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Main_Activity.this, SinglePlayer.class);
                    startActivity(intent);
            }
        });

        Button MultiPlayer_Button;
        MultiPlayer_Button = findViewById(R.id.multiplayer_button);
        MultiPlayer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Main_Activity.this, MultiPlayer.class);
                //startActivity(intent);
            }
        });

        Button Help_Button;
        Help_Button = findViewById(R.id.help_button);
        Help_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Main_Activity.this, Help.class);
                //startActivity(intent);
            }
        });

        Button Settings_Button;
        Settings_Button = findViewById(R.id.settings_button);
        Settings_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Main_Activity.this, Settings.class);
                //startActivity(intent);
            }
        });

    }

}
