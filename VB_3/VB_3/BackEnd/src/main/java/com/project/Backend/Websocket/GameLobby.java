package com.project.Backend.Websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.now;

/*
ALL COMMENTED OUT CODE WAS AN ATTEMPT AT SEPERATING WEBSOCKETS INTO LOBBIES
 */

@ServerEndpoint("/game_lobby/{username}/{id}")
@Component
public class GameLobby {

    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    private static Map<String, String> usernameReadyMap = new HashMap<>();
    private static Map<String, String> usernameIdMap = new HashMap<>();
    private static Map<String, Session> idSessionMap = new HashMap<>();
    private static Map<Session, String> sessionIdMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    //Store a list of usernames to each Id
    private static Map<String, List<String>> idUsernamesMap = new HashMap<>();

    //Stores the number of people who have clicked ready to the id
    private static Map<String, Integer> idNumReady = new HashMap<>();

    //Stores the number of people total to the id
    private static Map<String, Integer> idTotal = new HashMap<>();


    private boolean ready = false;
    private int ready_up = 0;
    private int num_connected_to = 0;


    /**
     * The OnOpen method will default to show everyone connected and with the default ready status of "unready"
     * @param session Session of the current websocket
     * @param username Username of the joiner of the websocket
     * @param id Game id to differentiate between games
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("username") String username,
                       @PathParam("id") String id) throws IOException {
        logger.info("Entered into Open");

        String readyStatus = "unready";

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        usernameReadyMap.put(username, readyStatus);
        usernameIdMap.put(username, id);
        idSessionMap.put(id,session);
        sessionIdMap.put(session,id);

        String endMessage = "";


        String identity = sessionIdMap.get(session);
        for (String key : usernameSessionMap.keySet()) {
            endMessage += key + ": " + usernameReadyMap.get(key) + "\n";
        }
        broadcast(endMessage);

        /*//updates the ArrayList of a particular gameId
        ArrayList<String> gameId;
        gameId = (ArrayList<String>) idUsernamesMap.get(id);
        gameId.add(username);
        idUsernamesMap.replace(id, idUsernamesMap.get(id), gameId);

        //Increments the total of people connected to a gameId by 1
        idTotal.replace(id, idTotal.get(id), (idTotal.get(id)+1));

        //For each Username associated with a specific id, concatenate them to the message to send
        for(String i : idUsernamesMap.get(id)){
            endMessage += i + ": " + usernameReadyMap.get(i) + "\n";
        }

        //Send the message to each username associated with an id
        for(String s : idUsernamesMap.get(id)){
            sendMessageToPArticularUser(s, endMessage);
        }

        /*
        broadcast(endMessage);
        */

    }

    /**
     * Simulates a ready up button that is to be pressed
     * @param session Current session for a websocket client
     * @param message Either "ready" or "unready" to update
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        logger.info("Ready up updated");

        String identity = sessionIdMap.get(session);
        String user = sessionUsernameMap.get(session);


        for (String key : usernameSessionMap.keySet()) {
            num_connected_to++;
        }


        /*
        for (String k : usernameReadyMap.keySet()) {
            if (usernameReadyMap.get(k).equals("ready")) {
                ready_up++;
            } else if (usernameReadyMap.get(k).equals("unready")) {
                ready_up--;
            }
        }
        */

        if (message.equals("ready")) {
            String username = sessionUsernameMap.get(session);
            String ready = usernameReadyMap.get(username);
            usernameReadyMap.replace(username, ready, "ready");
        } else if (message.equals("unready")) {
            String username = sessionUsernameMap.get(session);
            String ready = usernameReadyMap.get(username);
            usernameReadyMap.replace(username, ready, "unready");
        }

        /*
        for (String k : idUsernamesMap.get(identity)) {
            if (usernameReadyMap.get(k).equals("ready")) {
                idNumReady.replace(identity, idNumReady.get(identity), idNumReady.get(identity)+1);
            } else if (usernameReadyMap.get(k).equals("unready")) {
                idNumReady.replace(identity, idNumReady.get(identity), idNumReady.get(identity)-1);
            }
        }
        */

        for (String k : usernameReadyMap.keySet()) {
            //broadcast(k);
            if (usernameReadyMap.get(k).equals("ready")) {
                ready_up++;
            } else if (usernameReadyMap.get(k).equals("unready")) {
                ready_up--;
            }
        }

        if (ready_up == num_connected_to) {
            ready = true;
            broadcast("ALL READY");
            //sendMessageToParticularUser(identity, "ALL READY");
        } else {
            String larry = "";

            for (String key : usernameSessionMap.keySet()) {
                larry += key + ": " + usernameReadyMap.get(key) + "\n";
                //sendMessageToParticularUser(identity, key + ": " + usernameReadyMap.get(key));
            }
            broadcast(larry);
            //sendMessageToParticularUser(identity, identity + ": " + larry);
        }
        num_connected_to = 0;


        /*
        if (idNumReady.get(identity).equals(idTotal.get(identity))) {
            for(String tmp : idUsernamesMap.get(identity)){
                sendMessageToPArticularUser(tmp, "ALL READY");
            }
        } else {
            String endMessage = "";
            for (String key : idUsernamesMap.get(identity)) {
                endMessage += key + ": " + usernameReadyMap.get(key) + "\n";
            }
            for(String u : idUsernamesMap.get(identity)) {
                sendMessageToPArticularUser(u, endMessage);
            }
        }
         */
    }

    /**
     * When the websocket is closed, or someone leaves the websocket
     * @param session Session of the websocket
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        String identity = sessionIdMap.get(session);

        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        usernameReadyMap.remove(username);

        //If the player is readied up when they exit, decrement the ready
        if (usernameReadyMap.get(username) == "ready") {
            //idNumReady.replace(identity, idNumReady.get(identity), idNumReady.get(identity)-1);
            ready_up--;
        }
        //idTotal.replace(identity, idTotal.get(identity), idTotal.get(identity)-1);
        num_connected_to--;

        String message = username + " disconnected";
        /*
        for(String i : idUsernamesMap.get(identity)){
            sendMessageToPArticularUser(i, message);
        }
         */
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
    }

    private static void broadcast(String message)
            throws IOException {
        sessionUsernameMap.forEach((session, username) -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendMessageToPArticularUser(String username, String message)
    {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

}
