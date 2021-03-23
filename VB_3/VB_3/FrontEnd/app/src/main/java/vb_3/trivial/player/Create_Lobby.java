package vb_3.trivial.player;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import vb_3.trivial.ControllerClasses.app.AppController;
import vb_3.trivial.Global_Varable;
import vb_3.trivial.LoginActivity;
import vb_3.trivial.R;
import vb_3.trivial.admin.Admin_Activity;
import vb_3.trivial.newJsonArrayRequest;
import vb_3.trivial.spectator.Spectator_Activity;

public class Create_Lobby extends AppCompatActivity {

    private EditText Lobby_name_edit_text;
    private TextView Lobby_name_text_viw;
    private Spinner Category_spinner;
    private Switch Lobby_switch;
    private Switch Chat_switch;
    private Button Create_lobby_button;

    //Non-implemented settings
    private boolean chat_mode = false;
    private boolean lobby_mode = false;

    //Implemented settings
    private String URL = "http://coms-309-vb-3.misc.iastate.edu:8080/lobby_create";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__lobby);

        //Lobby_name_edit_text = findViewById(R.id.lobby_edit);
        Lobby_name_text_viw = findViewById(R.id.lobby_text);
        Category_spinner = findViewById(R.id.cat_spinner);
        Lobby_switch = findViewById(R.id.lobby_switch);
        Chat_switch = findViewById(R.id.chat_switch);
        Create_lobby_button = findViewById(R.id.create_lobby_button);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category_spinner.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();

        generateGameID();

        Lobby_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lobby_mode){
                    lobby_mode = false;
                }
                else
                {
                    lobby_mode = true;
                }
            }
        });

        Chat_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chat_mode){
                    chat_mode = false;
                }
                else
                {
                    chat_mode = true;
                }
            }
        });

        Create_lobby_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lobbyName = Lobby_name_text_viw.getText().toString();

                String category = Category_spinner.getSelectedItem().toString();
                if(category == "Random")
                {
                    Random rand = new Random();
                    int r_cat = rand.nextInt(2);

                    if(r_cat == 0)
                    {
                        category = "Geography";
                    }
                    else if(r_cat == 1)
                    {
                        category = "Languages";
                    }
                    else
                    {
                        category = "Math";
                    }
                }

                generateGameID();

                if(lobbyName.isEmpty())
                {
                    //TODO Set lobbyName to just the GameID
                    lobbyName = "Generic Name";
                }

                //Pass Game Elements to the lobby
                Intent intent = new Intent(Create_Lobby.this, Lobby.class);
                //intent.putExtra("LobbyName", lobbyName);
                startActivity(intent);
            }
        });
    }

    /**
     * Calls for a Game ID to be generated for this new lobby.
     */
    private void generateGameID(){
        final StringRequest strRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    Global_Varable.Game_ID = response;
                    Lobby_name_text_viw.setText(response);
                    Log.d("Response for Generate", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Create_Lobby.this, "Check your internet connection!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        AppController.getInstance().addToRequestQueue(strRequest);
    }


}
