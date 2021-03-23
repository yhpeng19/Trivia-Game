package com.project.Backend.Controller;

import com.project.Backend.Entities.ProposedQuestion;
import com.project.Backend.Entities.Question;
import com.project.Backend.Entities.LoginSession;
import com.project.Backend.Entities.User;
import com.project.Backend.Repositories.ProposedQuestionRepository;
import com.project.Backend.Repositories.QuestionRepository;
import com.project.Backend.Repositories.SessionRepository;
import com.project.Backend.VOs.ProposedQVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Class for managing questions proposed by player users
 */
@RestController
public class  ProposedQuestionController {
    @Autowired
    private ProposedQuestionRepository proposedQuestionRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private QuestionRepository questionRepository;


    /**
     * Method to record questions proposed by players
     * @param body consists of all the necessary parts of a question
     * @return status true if proposed successfully, false and a reason otherwise
     */
    @PostMapping("/questions/propose")
    public ProposedQVO addUser(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        ProposedQVO response = checkToken(token, "player");
        if (response.getStatus()) {
            String question = body.get("question");
            String correct = body.get("correct");
            String wrong1 = body.get("wrong1");
            String wrong2 = body.get("wrong2");
            String wrong3 = body.get("wrong3");
            String category = body.get("category");
            LoginSession loginSession = sessionRepository.findByToken(token);
            proposedQuestionRepository.save(new ProposedQuestion(question, correct, wrong1, wrong2, wrong3, category, loginSession.getUser()));
            return new ProposedQVO(true, "NA");
        }
        return response;
    }

    /**
     * Method returns the number of questions pending for approval
     * @param body token to check the user authorization
     * @return number of pending questions
     */
    @PostMapping ("/questions/numPending")
    public ProposedQVO numPending(@RequestBody Map<String, String> body){
        String token = body.get("token");
        ProposedQVO response = checkToken(token, "admin");
        if (response.getStatus()){
        return new ProposedQVO(true, ""+proposedQuestionRepository.numPending());}
        return response;
    }

    /**
     * Method returns the list of pending questions that are waiting for approval
     * @param body token to check user authorization
     * @return a list of pending questions
     */
    @PostMapping ("/questions/pending")
    public List<ProposedQuestion> pendingQuestions(@RequestBody Map<String, String> body){
        String token = body.get("token");
        ProposedQVO response = checkToken(token, "admin");
        if (response.getStatus()){
            List<ProposedQuestion> list = proposedQuestionRepository.findAll();
            for (ProposedQuestion q: list){
                q.setUser(null);
            }
            return list;}
        return null;
    }

    /**
     * Method to approve a certain questions. The question is then deleted from the Pending table and added to the Question table.
     * @param body token to check user authorization
     * @return status true if approved successfully, false and a reason otherwise
     */
    @PostMapping ("/questions/approve")
    public ProposedQVO addQuestion(@RequestBody Map<String, String> body){
        String token = body.get("token");
        ProposedQVO response = checkToken(token, "admin");
        if (response.getStatus()){
            String id = body.get("id");
            int idInt = Integer.parseInt(id);
            ProposedQuestion temp = proposedQuestionRepository.findById(idInt);
            if (temp != null){
                proposedQuestionRepository.delete(temp);
                String question = body.get("question");
                String correct_answer = body.get("correct");
                String wrong_answer1 = body.get("wrong1");
                String wrong_answer2 = body.get("wrong2");
                String wrong_answer3 = body.get("wrong3");
                String category = body.get("category");
                questionRepository.save(new Question(question, correct_answer, wrong_answer1, wrong_answer2, wrong_answer3, category));
                return new ProposedQVO(true, "NA");
            }
            else {
                return new ProposedQVO(false, "id");
            }
        }
       else return response;
    }

    /**
     * Method deletes a pending question if not approved
     * @param body token to the user authorization
     * @param id id of a pending question to be deleted
     * @return status true if deleted successfully, false and a reason otherwise
     */
    @DeleteMapping ("/questions/decline/{id}")
    public ProposedQVO delete(@RequestBody Map<String, String> body, @PathVariable("id") int id){
        String token = body.get("token");
        ProposedQVO response = checkToken(token, "admin");
        if (response.getStatus()){
            ProposedQuestion question = proposedQuestionRepository.findById(id);
            if (question == null) return new ProposedQVO(false, "id");
            proposedQuestionRepository.delete(question);
            return new ProposedQVO(true, "NA");}
        return response;
    }

    /**
     * Helper method to check for token existence, expiration date, and role authorization
     * @param token token of a current session
     * @param role role to check for authorization
     * @return status true if authorized, false and a reason otherwise
     */
    private ProposedQVO checkToken(String token, String role){
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
        }}}
    return new ProposedQVO(true, "");
}

}
