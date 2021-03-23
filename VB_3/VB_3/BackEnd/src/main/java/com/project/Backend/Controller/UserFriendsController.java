package com.project.Backend.Controller;

import com.project.Backend.Entities.FriendRequest;
import com.project.Backend.Entities.LoginSession;
import com.project.Backend.Entities.User;
import com.project.Backend.Repositories.FriendRequestRepository;
import com.project.Backend.Repositories.SessionRepository;
import com.project.Backend.Repositories.UserRepository;
import com.project.Backend.VOs.LoginVO;
import com.project.Backend.VOs.ProposedQVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Class for managing friends feature for users
 */
@RestController
public class UserFriendsController {
    private LoginVO response;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    /**
     * Method for searching for players to send a friend request
     * @param body token to determine a current user
     * @return a list of users with a username similar to the one being searched
     */
    @PostMapping("/users/searchByUsername")
    private List<User> searchByUsername(@RequestBody Map<String,String> body){
        String token = body.get("token");
        List <User> users = new ArrayList<>();
        ProposedQVO result =  checkToken(token, "player");
        if (result.getStatus() || result.getError().equals("role")){
            String username = body.get("username");
            users = userRepository.findByUsername(username);
            if (users!=null){
                users.remove(userRepository.findByUsernameUnique(username));
                for (User u : users)
                {
                    u.setPassword(null);
                    u.setLoginSessions(null);
                    u.setGames(null);
                    u.setProposedQuestions(null);
                    u.setFriends(null);
                    u.setSent(null);
                    u.setReceived(null);
                }
            }

        }
        return users;
    }


    /**
     * Method for sending friend requests, adds the user to friends if an opposite request is already pending
     * @param body token of a current user, username of user to send a request to
     * @return status true if request sent successfully, false otherwise
     */
    @PostMapping ("/users/addFriend")
    private ProposedQVO addFriend(@RequestBody Map<String,String> body){
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "player");
        if (result.getStatus()){
            String username = body.get("username");
            User receiver = userRepository.findByUsernameUnique(username);
            if (receiver == null) return new ProposedQVO(false, "username");
            LoginSession loginSession = sessionRepository.findByToken(token);
            User sender = loginSession.getUser();
            FriendRequest requestRev = friendRequestRepository.findRequest(receiver, sender);
            FriendRequest request = friendRequestRepository.findRequest(sender, receiver);
            if (requestRev != null) {
                friendRequestRepository.delete(requestRev);
                receiver.addFriend(sender);
                sender.addFriend(receiver);
                userRepository.save(receiver);
                userRepository.save(sender);
                if (request != null) friendRequestRepository.delete(request);
                return new ProposedQVO(true, "NA");
            }
            else{
                if (request == null) {friendRequestRepository.save(new FriendRequest(sender, receiver));
                    return new ProposedQVO(true, "NA");
                }
                else return new ProposedQVO(false, "friends");
            }
        }
        return result;
    }

    /**
     * Method to view all the pending friend requests
     * @param body token to check for authorization and retrieve current user info
     * @return a list of users who has a pending friend request for a current user
     */
    @PostMapping ("/users/pendingRequests")
    private List<User> viewFriendRequests(@RequestBody Map<String,String> body) {
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "player");
        if (result.getStatus()){
            LoginSession loginSession = sessionRepository.findByToken(token);
            User user = loginSession.getUser();
            List<User> users = friendRequestRepository.findByReceiver(user);
            List<String> list = new ArrayList<String>();
            for (User u : users){
                u.setPassword(null);
                u.setLoginSessions(null);
                u.setGames(null);
                u.setProposedQuestions(null);
                u.setFriends(null);
                u.setSent(null);
                u.setReceived(null);
            }
            return users;
        }
        return null;
    }

    /**
     * Method for deleting a user from the fiends list by username
     * @param body token to check for authorization and retrieve current user info, username of a friend to be deleted
     * @return status true if deleted successfully, false and a reason otherwise
     */
    @PostMapping ("/users/deleteFriend")
    private ProposedQVO deleteFriend(@RequestBody Map<String, String> body){
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "player");
        if (result.getStatus()){
            String username = body.get("username");
            User user = userRepository.findByUsernameUnique(username);
            if (user == null) return new ProposedQVO(false, "username");
            LoginSession loginSession = sessionRepository.findByToken(token);
            User user1 = loginSession.getUser();
            user1.deleteFriend(user);
            user.deleteFriend(user1);
            userRepository.save(user);
            userRepository.save(user1);
            return new ProposedQVO(true, "NA");
        }
        return result;
    }

    /**
     * Method to retrieve all the users that are friends with a current user
     * @param body token to check for authorization and retrieve current user info
     * @return a list of friends of a current user
     */
    @PostMapping ("/users/viewFriends")
    private List<User> viewFriends(@RequestBody Map<String,String> body) {
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "player");
        if (result.getStatus()){
            LoginSession loginSession = sessionRepository.findByToken(token);
            User user = loginSession.getUser();
            List<User> users = userRepository.findFriendsById(user.getId());
            List<String> list = new ArrayList<String>();
            for (User u : users){
                u.setPassword(null);
                u.setLoginSessions(null);
                u.setGames(null);
                u.setProposedQuestions(null);
                u.setFriends(null);
                u.setSent(null);
                u.setReceived(null);
            }
            return users;
        }
        return null;
    }

    /**
     * Method to decline a friend request pending
     * @param body token to check for authorization and retrieve current user info, username of user who the friend request comes from
     * @return status true if declined successfully, false and a reason otherwise
     */
    @PostMapping ("/users/declineRequest")
    private ProposedQVO declineRequest(@RequestBody Map<String,String> body){
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "player");
        if (result.getStatus()){
            String senderUsername = body.get("username");
            User sender = userRepository.findByUsernameUnique(senderUsername);
            if (sender != null){
                LoginSession session =sessionRepository.findByToken(token);
                User receiver = session.getUser();
                FriendRequest friendRequest = friendRequestRepository.findRequest(sender,receiver);
                if (friendRequest != null) {
                    friendRequestRepository.delete(friendRequest);
                    return new ProposedQVO(true, "NA");
                }
                else return new ProposedQVO(false, "request");
            }
            else return new ProposedQVO(false, "username");
        }
        return result;
    }

    /**
     * Helper method to check for token existence, expiration date, and request authorization
     * @param token of a current session
     * @param role of a user that is authorized for a certain request
     * @return true if authorized, false and a reason otherwise
     */
    private ProposedQVO checkToken(String token, String role) {
        LoginSession loginSession = sessionRepository.findByToken(token);
        if (loginSession == null) {
            return new ProposedQVO(false, "token");
        } else {
            Date current = new Date();
            Date endDate = loginSession.getEndDate();
            if (current.after(endDate)) {
                sessionRepository.delete(loginSession);
                return new ProposedQVO(false, "date");
            } else {
                User user = loginSession.getUser();
                if (!user.getRole().equals(role)) {
                    return new ProposedQVO(false, "role");
                }
            }
        }
        return new ProposedQVO(true, "NA");
    }
}
