package vb_3.trivial.player;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vb_3.trivial.ControllerClasses.app.AppController;
import vb_3.trivial.Global_Varable;
import vb_3.trivial.R;
import vb_3.trivial.newJsonArrayRequest;

public class Friend_request_Activity extends AppCompatActivity {

    private final String requestlistlist_url = "http://coms-309-vb-3.misc.iastate.edu:8080/users/pendingRequests";
    private final String accept_url = "http://coms-309-vb-3.misc.iastate.edu:8080/users/addFriend";
    private final String deline_url = "http://coms-309-vb-3.misc.iastate.edu:8080/users/declineRequest";

    private String[] request_save;
    private int request_num=0;
    private ArrayList<request_item> requestList = new ArrayList<>();
    private ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request_);

        lst = (ListView) findViewById(R.id.friend_request_listView);
        LoadrequestPack();

        int p = 0;
        long delay= 500;
        while(p<1){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < request_num; i++) {
                        request_item temp = new request_item(request_save[i]);
                        requestList.add(temp);
                    }

                    Friend_request_Activity.requestAdapter adapter = new Friend_request_Activity.requestAdapter(Friend_request_Activity.this, R.layout.request_item,requestList);

                    lst.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            },delay);
            p++;
        }
    }

    public void LoadrequestPack() {
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        JSONObject parameters = new JSONObject(params);
        newJsonArrayRequest req = new newJsonArrayRequest(Request.Method.POST,requestlistlist_url, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    request_num = response.length();
                    request_save = new String[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject request = (JSONObject) response.get(i);
                        String username = request.getString("username");
                        request_save[i] = username;
                    }
                } catch (JSONException e) {
                    Toast errorToast = Toast.makeText(Friend_request_Activity.this, "Sorry, an exception happened!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Friend_request_Activity.this, "Unexpected data type1!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }

    public class requestAdapter extends ArrayAdapter<request_item> {
        private int Id;

        /**
         *  Constructor for adapter.
         * */
        public requestAdapter(Context context, int textViewResourceId, ArrayList<request_item> object) {
            super(context,textViewResourceId,object);
            Id=textViewResourceId;
        }

        /**
         * @return view which is fixed single item for ViewList.
         * */

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            request_item temp = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(Id, parent, false);
            TextView username_ = (TextView) view.findViewById(R.id.request_name);
            Button accept = (Button) view.findViewById(R.id.request_accept);
            Button decline = (Button) view.findViewById(R.id.request_decline);
            username_.setText(temp.getUsername()+"");

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global_Varable.request_index=position;
                    accept();
                    Intent intent = new Intent(Friend_request_Activity.this, Main_Activity.class);
                    startActivity(intent);
                }
            });

            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global_Varable.request_index=position;
                    decline();
                    Intent intent = new Intent(Friend_request_Activity.this, Main_Activity.class);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    /**
     * Make a JSON object request, and send a JSON object to server to accept a friend request.
     * */
    public void accept(){
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        params.put("username", request_save[Global_Varable.request_index]);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, accept_url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.getString("status").equals("true")){
                        Toast errorToast = Toast.makeText(Friend_request_Activity.this, "Adding friend successfully!", Toast.LENGTH_SHORT);
                        errorToast.show();
                    }else{
                        if(response.getString("error").equals("token")){
                            Toast errorToast = Toast.makeText(Friend_request_Activity.this, "The token is invalid!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("username")){
                            Toast errorToast = Toast.makeText(Friend_request_Activity.this, "User name is invalid!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("role")){
                            Toast errorToast = Toast.makeText(Friend_request_Activity.this, "You are not a player!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("friend")){
                            Toast errorToast = Toast.makeText(Friend_request_Activity.this, "This person is your friend already!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }
                    }
                }catch(JSONException e){
                    Toast errorToast = Toast.makeText(Friend_request_Activity.this, "Sorry, there is an error happened!", Toast.LENGTH_SHORT);
                    errorToast.show();
                    // Recovery
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Friend_request_Activity.this, "Check your internet connection!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }

    /**
     * Make a JSON object request, and send a JSON object to server to decline a friend request.
     * */
    public void decline(){
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        params.put("username", request_save[Global_Varable.request_index]);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, deline_url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.getString("status").equals("true")){
                        Toast errorToast = Toast.makeText(Friend_request_Activity.this, "Reject the friend request successfully!", Toast.LENGTH_SHORT);
                        errorToast.show();
                    }else{
                        if(response.getString("error").equals("token")){
                            Toast errorToast = Toast.makeText(Friend_request_Activity.this, "The token is invalid!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("username")){
                            Toast errorToast = Toast.makeText(Friend_request_Activity.this, "User name is invalid!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("role")){
                            Toast errorToast = Toast.makeText(Friend_request_Activity.this, "You are not a player!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("friend")){
                            Toast errorToast = Toast.makeText(Friend_request_Activity.this, "This person is your friend already!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }
                    }
                }catch(JSONException e){
                    Toast errorToast = Toast.makeText(Friend_request_Activity.this, "Sorry, there is an error happened!", Toast.LENGTH_SHORT);
                    errorToast.show();
                    // Recovery
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Friend_request_Activity.this, "Check your internet connection!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }
}
