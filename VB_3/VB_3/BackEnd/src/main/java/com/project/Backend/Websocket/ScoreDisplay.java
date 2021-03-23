package com.project.Backend.Websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint("/ScoreDisplay/{username}/{id}")
@Component
public class ScoreDisplay {

    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    private static Map<String, Integer> usernameScoreMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private static Map<String, List<String>> idUsernamesMap = new HashMap<>();
    private static Map<Session, String> sessionIdMap = new HashMap<>();


    /**
     * Starts game with each user at a score of 0
     * @param session Current websocket session
     * @param username username of the client connecting
     * @param id GameId
     * @throws IOException
     */
    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username,
            @PathParam("id") String id) throws IOException
    {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        usernameScoreMap.put(username, 0);
        sessionIdMap.put(session, id);

        String endMessage = "";

        /*
        ArrayList<String> gameId;
        gameId = (ArrayList<String>) idUsernamesMap.get(id);
        gameId.add(username);
        idUsernamesMap.replace(id, idUsernamesMap.get(id), gameId);

        for(String key: idUsernamesMap.get(id)) {
            endMessage += key + ": " + usernameScoreMap.get(key);
            //broadcast(key + ": " + usernameScoreMap.get(key));
        }
        for(String k : idUsernamesMap.get(id)){
            sendMessageToPArticularUser(k, endMessage);
        }
        */

        for(String key: usernameScoreMap.keySet()){
            endMessage += key + ": " + usernameScoreMap.get(key) + "\n";
        }

        broadcast(endMessage);
    }

    /**
     *  When an updated score is sent to the websocket, the websocket shows updated score
     * @param session
     * @param message A number that is the calculated score
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        // Handle new messages
        logger.info("Entered into Message: Got Message:"+message);
        String username = sessionUsernameMap.get(session);
        String id = sessionIdMap.get(session);

        int i = Integer.parseInt(message);

        Integer temp = usernameScoreMap.get(username);
        usernameScoreMap.replace(username, temp, i);
        String endMessage = "";
        for(String key: usernameScoreMap.keySet()){
            endMessage += key + ": " + usernameScoreMap.get(key) + "\n";
        }
        /*
        for(String key: idUsernamesMap.get(id)) {
            endMessage += key + ": "+ usernameScoreMap.get(key) + "\n";
        }

        for(String key: idUsernamesMap.get(id)) {
            sendMessageToPArticularUser(key, endMessage);
        }*/
        broadcast(endMessage);
    }

    /**
     * The method to use when a game is finished
     * @param session
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException
    {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        usernameScoreMap.remove(username);

        //String message= username + " disconnected";
        //broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // Do error handling here
        logger.info("Entered into Error");
    }

    private static void broadcast(String message)
            throws IOException
    {
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
