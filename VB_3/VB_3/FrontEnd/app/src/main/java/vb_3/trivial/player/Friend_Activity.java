package vb_3.trivial.player;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
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
import vb_3.trivial.LoginActivity;
import vb_3.trivial.R;
import vb_3.trivial.admin.Admin_Activity;
import vb_3.trivial.newJsonArrayRequest;
import vb_3.trivial.spectator.Spectator_Activity;

public class Friend_Activity extends AppCompatActivity {
    private String[] userlist;
    private String[] friendlist;
    private int user_length;
    private int friend_length;
    private ListView friend_lst;
    private final String URL = "http://coms-309-vb-3.misc.iastate.edu:8080/users/searchByUsername";
    private final String friend_url = "http://coms-309-vb-3.misc.iastate.edu:8080//users/viewFriends";
    private final String add_friend_url = "http://coms-309-vb-3.misc.iastate.edu:8080/users/addFriend";
    private ArrayList<friend_item> friendList = new ArrayList<>();
    private MultiAutoCompleteTextView multiAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_);

        LoadUserPack();

        int p1 = 0;
        long delay1= 500;
        while(p1<1){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadFriendPack();
                }
            },delay1);
            p1++;
        }

        int p = 0;
        long delay= 1000;
        while(p<1){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < friend_length; i++) {
                        friend_item temp = new friend_item(friendlist[i]);
                        friendList.add(temp);
                    }

                    friend_lst = (ListView) findViewById(R.id.friend_listView);
                    final AutoCompleteTextView AutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.MultiAutoCompleteTextView);
                    Friend_Activity.friendAdapter adapter = new Friend_Activity.friendAdapter(Friend_Activity.this, R.layout.friend_item,friendList);

                    friend_lst.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    ArrayAdapter<String> aa = new ArrayAdapter<String>(Friend_Activity.this, android.R.layout.simple_list_item_1, userlist);
                    AutoCompleteTextView.setAdapter(aa);
                    aa.notifyDataSetChanged();
                    //multiAutoCompleteTextView

                    AutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,View view,int position, long id) {
                            Global_Varable.username_add = AutoCompleteTextView.getText().toString();
                            //Intent intent = new Intent(Friend_Activity.this, Add_Friend_Activity.class);
                            //intent.putExtra()
                            //startActivity(intent);
                            AlertDialog.Builder builder  = new AlertDialog.Builder(Friend_Activity.this);
                            builder.setTitle("Confirm" ) ;
                            builder.setMessage("Do you want to add " + Global_Varable.username_add + " to friend list?") ;
                            builder.setPositiveButton("Yes", null);
                            builder.setNegativeButton("No", null);
                            builder.create();
                            final AlertDialog dialog = builder.create();
                            dialog.show();
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    addFriend();
                                }
                            });
                            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                }
            },delay);
            p++;
        }

    }

    public void LoadUserPack() {
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        params.put("username", "");
        JSONObject parameters = new JSONObject(params);
        newJsonArrayRequest req = new newJsonArrayRequest(Request.Method.POST,URL, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    user_length = response.length();
                    userlist = new String[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject Question_Answers = (JSONObject) response.get(i);
                        String username = Question_Answers.getString("username");
                        userlist[i] = username;
                    }
                } catch (JSONException e) {
                    Toast errorToast = Toast.makeText(Friend_Activity.this, "Sorry, an exception happened!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Friend_Activity.this, "Unexpected data type1!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }

    public void LoadFriendPack() {
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        //params.put("Username", Global_Varable.token);
        JSONObject parameters = new JSONObject(params);
        newJsonArrayRequest req = new newJsonArrayRequest(Request.Method.POST,friend_url, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    friend_length = response.length();
                    friendlist = new String[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject Question_Answers = (JSONObject) response.get(i);
                        String username = Question_Answers.getString("username");
                        friendlist[i]=username;
                    }
                } catch (JSONException e) {
                    Toast errorToast = Toast.makeText(Friend_Activity.this, "Sorry, an exception happened!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Friend_Activity.this, "Unexpected data type2!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }

    public void addFriend() {
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        params.put("username", Global_Varable.username_add);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, add_friend_url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.getString("status").equals("true")){
                        Toast errorToast = Toast.makeText(Friend_Activity.this, "The request has been sent successfully!", Toast.LENGTH_SHORT);
                        errorToast.show();
                    }else{
                        if(response.getString("error").equals("token")){
                            Toast errorToast = Toast.makeText(Friend_Activity.this, "The token is invalid!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("username")){
                            Toast errorToast = Toast.makeText(Friend_Activity.this, "User name is invalid!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("role")){
                            Toast errorToast = Toast.makeText(Friend_Activity.this, "You are not a player!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("friend")){
                            Toast errorToast = Toast.makeText(Friend_Activity.this, "This person is your friend already!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }
                    }
                }catch(JSONException e){
                    Toast errorToast = Toast.makeText(Friend_Activity.this, "Sorry, there is an error happened!", Toast.LENGTH_SHORT);
                    errorToast.show();
                    // Recovery
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Friend_Activity.this, "Check your internet connection!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }

    public class friendAdapter extends ArrayAdapter<friend_item> {
        private int Id;

        /**
         *  Constructor for adapter.
         * */
        public friendAdapter(Context context, int textViewResourceId, ArrayList<friend_item> object) {
            super(context,textViewResourceId,object);
            Id=textViewResourceId;
        }

        /**
         * @return view which is fixed single item for ViewList.
         * */

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            friend_item temp = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(Id, parent, false);
            TextView username_ = (TextView) view.findViewById(R.id.friend_name);
            Button check = (Button) view.findViewById(R.id.friend_list_check);
            username_.setText(temp.getUsername()+"");
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global_Varable.Approving_index1=position;
                    Intent intent = new Intent(Friend_Activity.this, Friend_item_Activity.class);
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}
