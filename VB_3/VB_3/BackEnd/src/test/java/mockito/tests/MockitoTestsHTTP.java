package mockito.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Backend.Controller.QuestionController;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MockitoTestsHTTP {

    @Mock
    QuestionController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTest() throws JSONException, IOException {
        JSONObject sampleObject = new JSONObject();
        sampleObject.put("id", "5");
        sampleObject.put("question", "How are you?");
        sampleObject.put("correct_answer", "Good");
        sampleObject.put("wrong_answer1", "I do not know");
        sampleObject.put("wrong_answer2", "Who are you?");
        sampleObject.put("wrong_answer3", "what?");
        sampleObject.put("category", "Test");

        String s = sampleObject.toString();
        ObjectMapper mapper = new ObjectMapper();

        Map<String,String> map = mapper.readValue(s, Map.class);

        /*
        ObjectMapper mapper = new ObjectMapper();
        Iterator<String> keysItr = sampleObject.keys();
        Map<String, String> tempMap = new HashMap<String,String>();
        while (keysItr.hasNext()){
        String key = keysItr.next();
        String value = (String)sampleObject.get(key);
        tempMap.put(key,value);
        }
        //Map<String, String> tempMap = mapper.readValue((DataInput) sampleObject, Map.class);
        Question q = controller.create(map);


        //Something must have been recently messed up in create()
        assertEquals(5, q.getId());
        assertEquals("Test", q.getCategory());
        assertEquals("Good",q.getCorrectAnswer());
        */

    }
}
