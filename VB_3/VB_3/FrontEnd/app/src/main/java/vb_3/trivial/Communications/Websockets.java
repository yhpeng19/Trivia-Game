package vb_3.trivial.Communications;

import android.util.Log;

import org.java_websocket.drafts.Draft_6455;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;


public class Websockets {

    private WebSocketClient wsc;
    private String URL;
    private String score;

    /**
     * Creates a new websocket with the given URL. This websocket will be used to manage score updating a
     * @param url A given URL to connect with the correct instance of a websocket.
     */
    public void game_socket(String url) {

        Draft[] drafts = {new Draft_6455()};
        String w = "ws://coms-309-vb-3.misc.iastate.edu:8080/chat/" + url;

        try {
            Log.d("Socket:", "Trying socket");
            wsc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);

                    //Action to be completed when a score is updated
                    //t1.setText(s + " Server:" + message);
                    score = message;
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }


            };
        } catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        wsc.connect();
    }


    /**
     * Recieves the score from the websocket
     * @return
     */
    public String getScore()
    {
        return score;
    }

}