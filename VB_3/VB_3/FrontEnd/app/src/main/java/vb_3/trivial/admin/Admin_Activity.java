package vb_3.trivial.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vb_3.trivial.Global_Varable;
import vb_3.trivial.LoginActivity;
import vb_3.trivial.R;
import vb_3.trivial.player.Category;
import vb_3.trivial.player.Main_Activity;
import vb_3.trivial.spectator.Spectator_Activity;

/**
 *   Main Class for admin user.
 * */
public class Admin_Activity extends AppCompatActivity {

    private String logoutURL = "http://coms-309-vb-3.misc.iastate.edu:8080/users/logout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);

        Button Quwstion_Approve_Button;
        Quwstion_Approve_Button = findViewById(R.id.question_approve_button);
        Quwstion_Approve_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Activity.this, Question_Approve.class);
                startActivity(intent);
            }
        });

        Button logout_Button;
        logout_Button = findViewById(R.id.admin_logout_button);
        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
                Intent intent = new Intent(Admin_Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button Account_Manage_Button;
        Account_Manage_Button = findViewById(R.id.account_manage_button);
        Account_Manage_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Activity.this, Account_Manage.class);
                startActivity(intent);
            }
        });

        Button empty_Button;
    }

    /**
     *  Help method to logout Button, which sends a JSON request to server for log out.
     */
    private void Logout(){
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.DELETE, logoutURL, parameters, new Response.Listener<JSONObject>() {
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
