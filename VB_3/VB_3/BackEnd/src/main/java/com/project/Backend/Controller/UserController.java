package com.project.Backend.Controller;

import com.project.Backend.Entities.FriendRequest;
import com.project.Backend.Entities.LoginSession;
import com.project.Backend.Entities.User;
import com.project.Backend.Repositories.FriendRequestRepository;
import com.project.Backend.Repositories.SessionRepository;
import com.project.Backend.Repositories.UserRepository;
import com.project.Backend.VOs.LoginVO;
import com.project.Backend.VOs.ProposedQVO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
//import java.util.logging.Logger;

/**
 * Class for managing users, promote, demote, view, login, register
 */
@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private LoginVO response;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    /**
     * Method to display users with the highest score overall
     * @param body token of a current user to check for authorization
     * @return a list of all users sorted from highest to lowest by their overall score
     */
    @PostMapping("/leaderboard")
    public List<User> leaderboard(@RequestBody Map<String, String> body){
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "player");
        if (result.getStatus()) {
            List<User> users = userRepository.findLeaders();
            for (User u : users) {
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
     * Method to view a list of friends of a current user by their overall score
     * @param body token to check the session and retrieve current user info
     * @return a list of users that are friends with a current user sorted from highest to lowest by their overall score
     */
    @PostMapping("/leaderboardFriends")
    public List<User> leaderboardFrieds(@RequestBody Map<String, String> body){
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "player");
        if (result.getStatus()) {
            LoginSession session = sessionRepository.findByToken(token);
            User user = session.getUser();
            List<User> friends = user.getFriends();
            List<User> temp = userRepository.findLeaders();
            List<User> users = new ArrayList<>();
            for (User u : temp){
                if (friends.contains(u) || user.getUsername().equals(u.getUsername())){
                    users.add(u);
                }
            }
            for (User u : users) {
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
     * Method for registering a new user. Role automatically set to player. Checks for uniqueness of an email and username before registering.
     * @param body username, email and password of a user to be registered
     * @return status true if registered successfully, false otherwise
     */
    @PostMapping("/users/register")
    public LoginVO addUser(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String pass = body.get("password");
        User temp = userRepository.findByEmail(email);
        if (temp == null) {
            temp = userRepository.findByUsernameUnique(username);
            if (temp == null){
            User newUser = new User(username, email, pass, null, null,0);
            userRepository.save(newUser);
            LoginSession loginSession = new LoginSession(newUser);
            sessionRepository.save(loginSession);
            return new LoginVO(true, loginSession.getToken(), "NA", newUser.getRole(), newUser.getUsername());
            }
            else {return new LoginVO(false, "NA", "username", "NA", "NA");}
        } else {
            return new LoginVO(false, "NA", "email", "NA", "NA");
        }
    }

    /**
     * Method for users to login, checks for email and password to be correct
     * @param body email and password for login
     * @return status true and a session token if login success, false and a reason otherwise
     */
    @PostMapping("/users/login")
    public LoginVO userLogin(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String pass = body.get("password");
        User user = userRepository.findByEmail(email);
        logger.info("Received " + user + " user info");
        if (user == null) {
            return new LoginVO(false, "NA", "email", "NA", "NA");
        } else if (!pass.equals(user.getPassword())) {
            return new LoginVO(false, "NA", "password", "NA", "NA");
        } else {
            LoginSession loginSession = sessionRepository.findByUserId(user);

            if (loginSession == null) {
                loginSession = new LoginSession(user);
                sessionRepository.save(loginSession);
            } else {
                if (loginSession.getEndDate().before(new Date())) {
                    sessionRepository.delete(loginSession);
                    loginSession = new LoginSession(user);
                    sessionRepository.save(loginSession);
                }
            }
            return new LoginVO(true, loginSession.getToken(), "NA", user.getRole(), user.getUsername());
        }
    }


    /**
     * Method to promote spectators to players, and players to admins
     * @param body token to check for request authorization, username of a user to be promoted
     * @return status true if promoted successfully, false and a reason otherwise
     */
    @PostMapping("/users/promote")
    public ProposedQVO promoteUser(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "admin");
        if (result.getStatus()) {
            String username = body.get("username");
            User user = userRepository.findByUsernameUnique(username);
            if (user == null) {
                return new ProposedQVO(false, "username");
            } else {
                if (user.getRole().equals("player")){
                    user.setRole("admin");
                }
                else if (user.getRole().equals("spectator")) {user.setRole("player");
                }
                userRepository.save(user);
                return new ProposedQVO(true, "NA");
            }
        } else return result;
    }

    /**
     * Method to promote any user directly to an admin. Used by superadmins. Method should be deleted in the final version of a project.
     * @param body username of user to be promoted
     * @return status true if promoted successfully, false otherwise
     */
    @PostMapping("/users/promoteAdmin")
    public ProposedQVO promoteAdmin(@RequestBody Map<String, String> body) {
            String username = body.get("username");
            User user = userRepository.findByUsernameUnique(username);
                    user.setRole("admin");
                    userRepository.save(user);
                    return new ProposedQVO(true, "NA");
    }

    /**
     * Method to demote admins to players, and players to spectators
     * @param body token to check for request authorization, username of a user to be demoted
     * @return status true if demoted successfully, false and a reason otherwise
     */
    @PostMapping("/users/demote")
    public ProposedQVO demoteUser(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "admin");
        if (result.getStatus()) {
            String username = body.get("username");
            User user = userRepository.findByUsernameUnique(username);
            if (user == null) {
                return new ProposedQVO(false, "username");
            } else {
                if (user.getRole().equals("admin")){
                    user.setRole("player");
                }
                else if (user.getRole().equals("player")){
                    user.setRole("spectator");
                }
                userRepository.save(user);
                return new ProposedQVO(true, "NA");
            }
        } else return result;
    }


    /**
     * Method to logout a user and delete the session token
     * @param body token of a current session
     * @return status true if logout successful, false and a reason otherwise
     */
    @PostMapping("/users/logout")
    public LoginVO delete(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        LoginSession loginSession = sessionRepository.findByToken(token);
        if (loginSession == null) {
            return new LoginVO(false, "NA", "token", "NA", "NA");
        } else {
            sessionRepository.delete(loginSession);
            return new LoginVO(true, "NA", "NA", "NA", "NA");
        }
    }

    /**
     * Method returns the number of users currently in the database
     * @param body token to check for authorization
     * @return number of users in the database, -1 if authorization failed
     */
    @PostMapping("/users/numUsers")
    public int numUsers(@RequestBody Map<String,String> body){
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "admin");
        if (!result.getStatus()) return -1;
        else {
            return userRepository.numUsers();
        }
    }

    /**
     * Method to view all the users, some information hidden
     * @param body token to check for request authorization
     * @return a list of all the users in the database
     */
    @PostMapping("/users/view")
    public List<User> viewUsers(@RequestBody Map<String,String> body){
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "admin");
        if (result.getStatus()){
            List<User> users = userRepository.findAll();
            for (User u:users){
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
        else return null;
    }

    /**
     * Method to delete a user record from the database by user id
     * @param body token to check for authorization
     * @param id of a user to be deleted
     */
    @DeleteMapping ("/users/delete/{id}")
    public void deleteUser(@RequestBody Map<String,String> body, @PathVariable ("id") int id) {
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "admin");
        if (result.getStatus()) {
            userRepository.delete(userRepository.findById(id));
        }
    }


    /**
     * Method to logout all the users
     * @return true
     */
    @DeleteMapping("/users/logoutAll")
    public ProposedQVO logoutAll(){
        sessionRepository.deleteAll();
        return new ProposedQVO(true, "NA");
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

