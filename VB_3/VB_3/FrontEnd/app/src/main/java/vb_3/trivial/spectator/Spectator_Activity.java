package vb_3.trivial.spectator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vb_3.trivial.Global_Varable;
import vb_3.trivial.LoginActivity;
import vb_3.trivial.R;
import vb_3.trivial.admin.Admin_Activity;

/**
 *  Class Spectator_Activity of spectator, which is as a main page.
 * */
public class Spectator_Activity extends AppCompatActivity {
    private String logoutURL = "http://coms-309-vb-3.misc.iastate.edu:8080/users/logout";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectator_);

        Button logout_Button;
        logout_Button = findViewById(R.id.admin_logout_button);
        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
                Intent intent = new Intent(Spectator_Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
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
