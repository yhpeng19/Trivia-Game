package vb_3.trivial.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import vb_3.trivial.R;

public class User_item_Activity extends AppCompatActivity {

    private TextView tx;
    private String promoteURL="http://coms-309-vb-3.misc.iastate.edu:8080/users/promote";
    private String demoteURL = "http://coms-309-vb-3.misc.iastate.edu:8080/users/demote";
    private Button promote;
    private Button demote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_item_);
        tx = findViewById(R.id.user_item_display);
        String print = Account_Manage.user_Package[Global_Varable.Approving_index1][0]
                +"\n"+Account_Manage.user_Package[Global_Varable.Approving_index1][1]
                +"\n"+Account_Manage.user_Package[Global_Varable.Approving_index1][2]
                +"\n"+Account_Manage.user_Package[Global_Varable.Approving_index1][3]
                +"\n"+Account_Manage.user_Package[Global_Varable.Approving_index1][4];
               // +"\n"+Account_Manage.user_Package[Global_Varable.Approving_index1][5];
        tx.setText(print);

        promote = findViewById(R.id.user_promote);
       promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promote();
            }
        });

        demote = findViewById(R.id.user_demote);
        demote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demote();
            }
        });
    }

    /**
     * Make a JSON object request, and send a JSON object to server to promote a user.
     * */
    private void promote(){
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        params.put("username", Account_Manage.user_Package[Global_Varable.Approving_index1][1]);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, promoteURL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("true")) {
                        Intent intent = new Intent(User_item_Activity.this, Account_Manage.class);
                        startActivity(intent);
                    } else {
                        if (response.getString("error").equals("username")) {
                            Toast errorToast = Toast.makeText(User_item_Activity.this, " username wasn’t found!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        } else if (response.getString("error").equals("token")) {
                            Toast errorToast = Toast.makeText(User_item_Activity.this, " Token is not find!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        } else if (response.getString("error").equals("date")) {
                            Toast errorToast = Toast.makeText(User_item_Activity.this, "Token expired!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        } else if (response.getString("error").equals("role")) {
                            Toast errorToast = Toast.makeText(User_item_Activity.this, "User is not a an admin!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }
                    }
                } catch(JSONException e){
                    Toast errorToast = Toast.makeText(User_item_Activity.this, "Sorry, there is an error happened!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(User_item_Activity.this, "Check your internet connection!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }

    /**
     * Make a JSON object request, and send a JSON object to server to demote a user.
     * */
    private void demote(){
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        params.put("username", Account_Manage.user_Package[Global_Varable.Approving_index1][1]);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, demoteURL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("true")) {
                        Intent intent = new Intent(User_item_Activity.this, Account_Manage.class);
                        startActivity(intent);
                    } else {
                        if (response.getString("error").equals("username")) {
                            Toast errorToast = Toast.makeText(User_item_Activity.this, " username wasn’t found!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        } else if (response.getString("error").equals("token")) {
                            Toast errorToast = Toast.makeText(User_item_Activity.this, " Token is not find!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        } else if (response.getString("error").equals("date")) {
                            Toast errorToast = Toast.makeText(User_item_Activity.this, "Token expired!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        } else if (response.getString("error").equals("role")) {
                            Toast errorToast = Toast.makeText(User_item_Activity.this, "User is not a an admin!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }
                    }
                } catch(JSONException e){
                    Toast errorToast = Toast.makeText(User_item_Activity.this, "Sorry, there is an error happened!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(User_item_Activity.this, "Check your internet connection!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }
}
