package com.project.Backend.Controller;

import com.project.Backend.Entities.*;
import com.project.Backend.Repositories.*;
import com.project.Backend.VOs.GameHistoryVO;
import com.project.Backend.VOs.ProposedQVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Class for managing games, recording result
 */
@RestController
public class GameController {
    private int[] arrRand;
    @Autowired
    private FullGameRepository fullGameRepository;
    @Autowired
    private SessionRepository sesRepo;
    @Autowired
    private GamePlayersRepository gpRepo;
    @Autowired
    private GameQuestionsRepository gameQuestionsRepository;
    @Autowired
    private SessionRepository loginSessionRepository;
    @Autowired
    private QuestionRepository questionRepository;


    /**
     * Method returns a user by a token
     * @param body consists of a current user token sent from a client
     * @return user
     */
    @PostMapping("/username_finder")
    public User find_user(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        LoginSession s = sesRepo.findByToken(token);
        User u = s.getUser();
        u.getUsername();
        return u;
    }


    /**
     * Creates a game record on lobby creation, assigns a list of questions to the game
     * @return gameID
     */
    @PostMapping("/lobby_create")
    public String start_game() {
        Game game;
        game = new Game();
        fullGameRepository.save(game);
        int num = 10;
        List<Question> list = questionRepository.findAll();
        Random rand = new Random();
        List<Question> temp = new ArrayList<>();
        if (num > list.size()) temp = list;
        else {
            arrRand = new int[num - 1];
            int i = 0;
            while (i < num) {
                int index = rand.nextInt(list.size());
                if (unique(index, i)) {
                    temp.add(list.get(index));
                    i++;
                }
            }
        }
        for (Question q:temp){
            gameQuestionsRepository.save(new GameQuestions(game,q));
        }
        return game.getIdString();
    }

    /**
     * Sets the game status to active on game start
     * @param gameId game/lobby ID
     * @return a list of questions assigned to this game
     */
    @GetMapping ("/game/startGame/{gameId}")
    public List<Question> sendQuestions(@PathVariable("gameId") int gameId){
        Game game = fullGameRepository.findById(gameId);

        if (game == null) return new ArrayList<Question>();
        game.setStatus("active");
        fullGameRepository.save(game);
        List<GameQuestions> list = game.getQuestionList();
        List<Question> qlist = new ArrayList<Question>();
        for(GameQuestions q : list){
            qlist.add(q.getQuestion());
        }
        return qlist;
    }

    /**
     * Saves user scores after the game is finished
     * @param body user token and their score in that game
     * @param gameId the game the score was earned in
     * @return status whether score save was successful or not
     */
    @PostMapping("/game_end/{gameId}")
    public ProposedQVO end_game_data(@RequestBody Map<String, String> body, @PathVariable("gameId") int gameId) {
        //Request body contains: token, GameId, score
        String TokenUserId = body.get("token");
        ProposedQVO result = checkToken(TokenUserId, "player");
        if (result.getStatus()) {
            Game game = fullGameRepository.findById(gameId);
            game.setStatus("closed");
            if (game == null) return new ProposedQVO(false, "GameId");
            String SCORE = body.get("score");
            LoginSession session = sesRepo.findByToken(TokenUserId);
            User user = session.getUser();
            user.setUserScore(user.getUserScore() + Integer.parseInt(SCORE));
            GamePlayers gamePlayers;
            gamePlayers = new GamePlayers(Integer.parseInt(SCORE), user, game);
            gpRepo.save(gamePlayers);

            return new ProposedQVO(true, "NA");
        }
        return result;
    }

    /**
     * Deletes a game record by gameID
     * @param gameId id of the game to be deleted
     * @return status true if deleted successfully, false otherwise
     */
    @DeleteMapping("/game/delete/{gameId}")
    private ProposedQVO deleteGame(@PathVariable("gameId") int gameId)
    {
        Game g = fullGameRepository.findById(gameId);
        if (g == null) {
            return new ProposedQVO(false, "gameId");
        }
        fullGameRepository.delete(g);
        return new ProposedQVO(true, "NA");
    }

    /**
     * Returns a game by the gameId
     * @param gameId Id of the game to be retrieved
     * @return game
     */
    @GetMapping("/game/retrieve/{gameId}")
    private Game getGameById(@PathVariable("gameId") int gameId){
        Game g = fullGameRepository.findById(gameId);
        return g;
    }

    /**
     * Returns a list of all games with a status "open" - games that haven't been played, for displaying a list of lobbies
     * @return a list of games
     */
    @GetMapping("/selectLobby")
    public List<Game> retrieveLobbies(){
        List<Game> g = fullGameRepository.findAllOpen("open");
        for(Game i : g){
            i.setGamesList(null);
            i.setqList(null);
        }
        return g;
    }

    /**
     * Returns a list of games played by current user, consists of all users and their scores for each game
     * @param body consists of a token of a current user
     * @return a list of objects that consists of usernames and their scores
     */
    @PostMapping ("/games/history")
    public List<GameHistoryVO> history(@RequestBody Map<String, String> body){
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "player");
        if (result.getStatus()) {
            LoginSession loginSession = loginSessionRepository.findByToken(token);
            User currentUser = loginSession.getUser();
            List<Game> games = fullGameRepository.findGamebyUserDesc(currentUser);
            List <GameHistoryVO> history = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Game g = games.get(i);
                GamePlayers gp = gpRepo.findScoreByUserAndGame(currentUser, g);
                if (gp != null){
                    GameHistoryVO oneGame = new GameHistoryVO();
                    int numPlayers = 1;
                    oneGame.setCurrentUser(currentUser.getUsername());
                    oneGame.setCurrentUserScore(gp.getTotalScore());
                    List<GamePlayers> enemiesGp = gpRepo.findEnemyScoresByGame(currentUser, g);
                    if (enemiesGp.size() > 0){
                        oneGame.setPlayer1(enemiesGp.get(0).getPlayer().getUsername());
                        oneGame.setScore1(enemiesGp.get(0).getTotalScore());
                        numPlayers++;
                    }
                    if (enemiesGp.size() > 1){
                        oneGame.setPlayer2(enemiesGp.get(1).getPlayer().getUsername());
                        oneGame.setScore2(enemiesGp.get(1).getTotalScore());
                        numPlayers++;
                    }
                    if (enemiesGp.size() > 2){
                        oneGame.setPlayer3(enemiesGp.get(2).getPlayer().getUsername());
                        oneGame.setScore3(enemiesGp.get(2).getTotalScore());
                        numPlayers++;
                    }
                    oneGame.setNumPlayers(numPlayers);
                    history.add(oneGame);
                }
            }
            return history;
        }
        return null;
    }

    /**
     * Helper method to check whether current user is authorized to make a certain request by his role type
     * @param token current user token received from a client
     * @param role a role of a user authorized to send a certain request
     * @return status true if token was found and not expired, and user is authorized; false otherwise
     */
    private ProposedQVO checkToken(String token, String role) {
        LoginSession session = sesRepo.findByToken(token);
        if (session == null) {
            return new ProposedQVO(false, "token");
        } else {
            Date current = new Date();
            Date endDate = session.getEndDate();
            if (current.after(endDate)) {
                sesRepo.delete(session);
                return new ProposedQVO(false, "date");
            } else {
                User user = session.getUser();
                if (!user.getRole().equals(role)) {
                    return new ProposedQVO(false, "role");
                }
            }
        }
        return new ProposedQVO(true, "");
    }


    /**
     * Helper method to create a list of random questions for a game
     * @param index index if a question to be added
     * @param size size of a list of questions
     * @return true if the question has not been added to that list, false otherwise
     */
    private boolean unique(int index, int size){
        for (int i=0;i<size; i++){
            if (arrRand[i] == index) return false;
        }
        arrRand[size] = index;
        return true;
    }

}