package com.project.Backend.Controller;


//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
import com.project.Backend.Entities.LoginSession;
import com.project.Backend.Entities.Question;
import com.project.Backend.Entities.User;
import com.project.Backend.Repositories.QuestionRepository;
import com.project.Backend.Repositories.SessionRepository;
import com.project.Backend.VOs.ProposedQVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Class for managing questions available for the game
 */
@RestController
public class QuestionController {
    private int[] arrRand;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private QuestionRepository questionRepository;


    /**
     * Method to access the full list of questions in the database
     * @return a list of all questions in the database
     */
    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    /**
     * Method to access the list of all the questions of a certain category
     * @param category of questions
     * @return a list of questions of {category}
     */
    @GetMapping("/questions/{category}")
    public List<Question> getQuestionByCategory(@PathVariable("category") String category) {
        return questionRepository.findByCategory(category);
    }

    /**
     * Method returns a requested number:num of random questions of a requested category
     * @param category category of a question
     * @param num number of questions
     * @return a list of {num} questions of {category}
     */
    @GetMapping("/questions/{category}/{num}")
    public List<Question> getNumOfQuestionsByCategory(@PathVariable("category") String category, @PathVariable ("num") int num) {

        List<Question> list = getQuestionByCategory(category);
        Random rand = new Random();
        List<Question> temp = new ArrayList<Question>();
        if (num > list.size()) return list;
        arrRand = new int[list.size()-1];

        int i = 0;
        while (i < num){
            int index = rand.nextInt(list.size());
            if (unique(index, i)) {temp.add(list.get(index)); i++;}
        }
        return temp;
    }


    /**
     * Method to search for a question by id
     * @param id of a question
     * @return question by id
     */
    @GetMapping("/questions/search/{id}")
    public Question searchById(@PathVariable("id") int id){
        return questionRepository.findById(id);
    }


    /**
     * Deletes a question by question id
     * @param body token to check user authorization
     * @param id of a question to be deleted
     * @return status true if deleted successfully, false and a reason otherwise
     */
    @DeleteMapping("/questions/{id}")
    public ProposedQVO delete(@RequestBody Map<String, String> body, @PathVariable("id") int id){
        String token = body.get("token");
        ProposedQVO result = checkToken(token, "admin");
        if (result.getStatus()){
            Question q = questionRepository.findById(id);
            if (q == null) return new ProposedQVO(false, "id");
            questionRepository.delete(q);
            return new ProposedQVO(true, "NA");
        }
        return result;
    }

    /**
     * Helper method to randomize a list of questions returned by getNumOfQuestionsByCategory() method
     * @param index of a question to be added to the list
     * @param size number of questions requested
     * @return false if the question is already in the list, true otherwise
     */
    private boolean unique(int index, int size){
        for (int i=0;i<size; i++){
            if (arrRand[i] == index) return false;
        }
        return true;
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
        return new ProposedQVO(true, "");
    }

}

