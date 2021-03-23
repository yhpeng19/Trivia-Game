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
import vb_3.trivial.admin.Question_Approve;
import vb_3.trivial.admin.Question_Pack_item_Activity;
import vb_3.trivial.admin.item;
import vb_3.trivial.newJsonArrayRequest;

public class LeaderBoard extends AppCompatActivity {

    private ListView lst;

    private Button global;
    private Button friend;

    private String global_URL = "http://coms-309-vb-3.misc.iastate.edu:8080/leaderboard";
    private String friend_URL = "http://coms-309-vb-3.misc.iastate.edu:8080/leaderboardFriends";

    private int item_number;
    public static String[][] learder_board_Package;
    private ArrayList<leader_item> itemList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        LoadGlobalPack();

        global = findViewById(R.id.L_Global_button);
        friend = findViewById(R.id.L_Friend_button);

        global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                learder_board_Package=null;
                itemList.clear();
                item_number=0;

                int p1 = 0;
                long delay1= 100;
                while(p1<1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadGlobalPack();
                        }
                    },delay1);
                    p1++;
                }

                int p2 = 0;
                long delay2= 500;
                while(p2<1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < item_number; i++) {
                                leader_item temp = new leader_item(i+1+"th",learder_board_Package[i][0],learder_board_Package[i][1]);
                                itemList.add(temp);
                            }
                            LeaderBoard.Adapter adapter = new LeaderBoard.Adapter(LeaderBoard.this, R.layout.leader_board_item,itemList);
                            lst = (ListView) findViewById(R.id.leader_board_listView);
                            lst.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    },delay2);
                    p2++;
                }

            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemList.clear();
                learder_board_Package=null;
                item_number=0;

                int p1 = 0;
                long delay1= 100;
                while(p1<1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadfriendPack();
                        }
                    },delay1);
                    p1++;
                }

                int p2 = 0;
                long delay2= 500;
                while(p2<1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < item_number; i++) {
                                leader_item temp = new leader_item(i+1+"th",learder_board_Package[i][0],learder_board_Package[i][1]);
                                itemList.add(temp);
                            }
                            LeaderBoard.Adapter adapter = new LeaderBoard.Adapter(LeaderBoard.this, R.layout.leader_board_item,itemList);
                            lst = (ListView) findViewById(R.id.leader_board_listView);
                            lst.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    },delay2);
                    p2++;
                }

            }
        });

        int p = 0;
        long delay= 500;
        while(p<1){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < item_number; i++) {
                        leader_item temp = new leader_item(i+1+"th",learder_board_Package[i][0],learder_board_Package[i][1]);
                        itemList.add(temp);
                    }
                    LeaderBoard.Adapter adapter = new LeaderBoard.Adapter(LeaderBoard.this, R.layout.leader_board_item,itemList);
                    lst = (ListView) findViewById(R.id.leader_board_listView);
                    lst.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            },delay);
            p++;
        }

    }

    public class Adapter extends ArrayAdapter<leader_item> {
        private int Id;

        /**
         *  Constructor for adapter.
         * */
        public Adapter(Context context, int textViewResourceId, ArrayList<leader_item> object) {
            super(context,textViewResourceId,object);
            Id=textViewResourceId;
        }

        /**
         * @return view which is fixed single item for ViewList.
         * */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            leader_item temp = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(Id, parent, false);
            TextView rank = (TextView) view.findViewById(R.id.L_rank);
            TextView username = (TextView) view.findViewById(R.id.L_name);
            TextView score = (TextView) view.findViewById(R.id.L_score);
            rank.setText(temp.getRank()+"");
            score.setText(temp.getScore()+"");
            username.setText(temp.getUsername()+"");
            return view;
        }
    }

    /**
     *   void method LoadGlobalPack full filled the 2D array by receiving the data from server(the leader board for top 100 players).
     * */
    public void LoadGlobalPack() {
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        JSONObject parameters = new JSONObject(params);
        newJsonArrayRequest req = new newJsonArrayRequest(Request.Method.POST,global_URL, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    item_number=response.length();
                    learder_board_Package = new String[item_number][2];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject info = (JSONObject) response.get(i);
                        String username = info.getString("username");
                        String score = info.getString("userScore");
                        learder_board_Package[i][0] = username;
                        learder_board_Package[i][1] = score;

                    }
                } catch (JSONException e) {
                    Toast errorToast = Toast.makeText(LeaderBoard.this, "0", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(LeaderBoard.this, "1", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }

    /**
     *   void method LoadfriendPack full filled the 2D array by receiving the data from server(the leader board for users and friends).
     * */
    public void LoadfriendPack() {
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        JSONObject parameters = new JSONObject(params);
        newJsonArrayRequest req = new newJsonArrayRequest(Request.Method.POST,friend_URL, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    item_number=response.length();
                    learder_board_Package = new String[item_number][2];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject info = (JSONObject) response.get(i);
                        String username = info.getString("username");
                        String score = info.getString("userScore");
                        learder_board_Package[i][0] = username;
                        learder_board_Package[i][1] = score;

                    }
                } catch (JSONException e) {
                    Toast errorToast = Toast.makeText(LeaderBoard.this, "0", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(LeaderBoard.this, "1", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }
}
