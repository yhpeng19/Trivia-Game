package vb_3.trivial.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;

import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

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

/**
 *   Class Account_Manage of admin, which is an activity that get a list of all users' information like email, username... and display them on a ListView.
 *   Admin could operate these information by click manage Button after each list item.
 * */
public class Account_Manage extends AppCompatActivity {

    private ListView lst;
    private String user_listURL = "http://coms-309-vb-3.misc.iastate.edu:8080/users/view";
    private int user_length;
    public static String[][] user_Package;
    private ArrayList<user_item> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        //receiveUserNumber();
        LoadUserPack();
        int p = 0;
        long delay= 500;
        while(p<1){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < user_length; i++) {
                        user_item temp = new user_item(user_Package[i][0],user_Package[i][1],user_Package[i][2],user_Package[i][3],user_Package[i][4],user_Package[i][5]);
                        userList.add(temp);
                    }
                    userAdapter adapter = new userAdapter(Account_Manage.this, R.layout.single_item,userList);
                    lst = (ListView) findViewById(R.id.account_manage_listView);
            lst.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            },delay);
            p++;
        }
    }

    public class userAdapter extends ArrayAdapter<user_item> {
        private int Id;

        /**
         *  Constructor for adapter.
         * */
        public userAdapter(Context context, int textViewResourceId, ArrayList<user_item> object) {
            super(context,textViewResourceId,object);
            Id=textViewResourceId;
        }

        /**
         * @return view which is fixed single item for ViewList.
         * */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            user_item temp = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(Id, parent, false);
            TextView email_ = (TextView) view.findViewById(R.id.user_email);
            TextView username_ = (TextView) view.findViewById(R.id.user_username);
            TextView rank_ = (TextView) view.findViewById(R.id.user_rank);
            Button check = (Button) view.findViewById(R.id.user_list_check);
            email_.setText(temp.getEmail()+"");
            username_.setText(temp.getUsername()+"");
            rank_.setText(temp.getGames()+"");
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global_Varable.Approving_index1=position;
                    Intent intent = new Intent(Account_Manage.this,User_item_Activity.class);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    /**
     * Make a JSON array request to get the array of JSON which includes all users' account informations.
     * */
    public void LoadUserPack() {
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        JSONObject parameters = new JSONObject(params);
        newJsonArrayRequest req = new newJsonArrayRequest(Request.Method.POST,user_listURL, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    user_length=response.length();
                    user_Package = new String[user_length][6];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject Question_Answers = (JSONObject) response.get(i);
                        String id = Question_Answers.getString("id");
                        String username = Question_Answers.getString("username");
                        String email = Question_Answers.getString("email");
                        String password = Question_Answers.getString("password");
                        String role = Question_Answers.getString("role");
                        String games = Question_Answers.getString("games");
                        user_Package[i][0] = id;
                        user_Package[i][1] = username;
                        user_Package[i][2] = email;
                        user_Package[i][3] = password;
                        user_Package[i][4] = role;
                        user_Package[i][5] = games;
                    }
                } catch (JSONException e) {
                    Toast errorToast = Toast.makeText(Account_Manage.this, "0", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Account_Manage.this, "1", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }

}
