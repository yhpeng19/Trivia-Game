package vb_3.trivial.player;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import vb_3.trivial.Global_Varable;
import vb_3.trivial.Help_Activity;
import vb_3.trivial.LoginActivity;
import vb_3.trivial.R;

/**
 * The home page that pushes into all the other subsequent pages. It can only be accessed by standard users and spectators.
 */

public class Main_Activity extends AppCompatActivity {
    private final String URL = "http://coms-309-vb-3.misc.iastate.edu:8080/users/searchByUsername";
    private final String friend_url = "http://coms-309-vb-3.misc.iastate.edu:8080//users/viewFriends";
    private String logoutURL = "http://coms-309-vb-3.misc.iastate.edu:8080/users/logout";
    private NavigationView navigationView;
    private DrawerLayout drawerlayout;
    private TextView username;
    //private Toolbar toolbar;
    //Dialog dialog;

    @Override
    /**
     * On creation of the main activity, it will pull up the activity_main xml. This will show the main menu for the application.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        /*
        toolbar= (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Home Page");
        toolbar.setNavigationIcon(R.drawable.);
        */

        Button SinglePlayer_Button;
        SinglePlayer_Button = findViewById(R.id.singlePlayer_button);
        SinglePlayer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Category.mode = 0;
                    Intent intent = new Intent(Main_Activity.this, Category.class);
                    startActivity(intent);
            }
        });

        Button MultiPlayer_Button;
        MultiPlayer_Button = findViewById(R.id.multiplayer_button);
        MultiPlayer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category.mode = 1;
                Intent intent = new Intent(Main_Activity.this, Lobbies_List.class);
                startActivity(intent);
            }
        });

        navigationView = findViewById(R.id.navigationView);
        drawerlayout = (DrawerLayout)findViewById(R.id.drawerlayout);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setCheckable(true);
                        menuItem.setChecked(true);
                        drawerlayout.closeDrawers();
                        switch (menuItem.getItemId())
                        {
                            case R.id.Question_propose_button:
                                Intent intent1 = new Intent(Main_Activity.this, Question_propose.class);
                                startActivity(intent1);
                                break;
                            case R.id.settings_button:
                                //Intent intent2 = new Intent(Main_Activity.this, Settings.class);
                                //startActivity(intent2);
                                break;
                            case R.id.user_logout_button:
                                Logout();
                                Intent intent3 = new Intent(Main_Activity.this, LoginActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.help_button:
                                Intent intent4 = new Intent(Main_Activity.this, Help_Activity.class);
                                //intent.putExtra("USERNAME", username);
                                startActivity(intent4);
                                break;
                            case R.id.friend_button:
                                Intent intent5 = new Intent(Main_Activity.this, Friend_Activity.class);
                                startActivity(intent5);
                                break;
                            case R.id.friend_request:
                                Intent intent6 = new Intent(Main_Activity.this, Friend_request_Activity.class);
                                startActivity(intent6);
                                break;
                            case R.id.learder_board:
                                Intent intent7 = new Intent(Main_Activity.this, LeaderBoard.class);
                                startActivity(intent7);
                                break;
                        }
                        return true;
                    }
                });

        View headview= navigationView.getHeaderView(0);
        username = headview.findViewById(R.id.user_name_show);
        username.setText("Hello, Player "+Global_Varable.username+"!");
/*
        Button Question_propose_button;
        Question_propose_button= findViewById(R.id.Question_propose_button);
        Question_propose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Activity.this, Question_propose.class);
                startActivity(intent);
            }
        });

        Button Help_Button;
        Help_Button = findViewById(R.id.help_button);
        Help_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Activity.this, Help_Activity.class);
                //intent.putExtra("USERNAME", username);
                startActivity(intent);
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


        Button logout_Button;
        logout_Button = findViewById(R.id.user_logout_button);
        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
                Intent intent = new Intent(Main_Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        */
    }

    /**
     *  Help method to logout Button, which sends a JSON request to server for log out.
     */
    private void Logout(){
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        Global_Varable.token="";
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, logoutURL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }

}
