package mockito.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class TestQuestionService {


   /* @InjectMocks
    QuestionService ques;

    @Mock
    QuestionRepository repo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }




    @Test
    public void getQuestionByQuestionTest() {
        when(repo.findByQuestion("What color is the sky?")).thenReturn( new Question( "What color is the sky?",
                                                        "Blue",
                                                        "Green", "Purple", "Orange",
                                                            "Science"));

        Question q = ques.findByQuestion("What color is the sky?");

        assertEquals("What color is the sky?", q.getQuestion());
        assertEquals("Science", q.getCategory());
        assertEquals(1, q.getId());

    }

    @Test
    public void getQuestionsFromListTest() {

        List<Question> list = new ArrayList<Question>();
        Question q1 = new Question( "What color is the sky?", "Blue", "Green", "Purple", "Orange", "Science");
        Question q2 = new Question ( "What are trees made of?", "Wood", "Water", "Air", "Clouds", "Science");
        Question q3 = new Question( "Who was the first president of the US?", "George Washington", "Abraham Lincoln", "Theodore Roosevelt", "Thomas Jefferson", "History");

        list.add(q1);
        list.add(q2);
        list.add(q3);

        *//*when(repo.printQuestionList()).thenReturn(list);*//*
        assertEquals(q1, list.get(0));
        assertEquals(q2, list.get(1));
        assertEquals(q3, list.get(2));

    }

    @Test
    public void saveTest() {

        Question test_q = new Question( "What color is the sky?", "Blue", "Green", "Purple", "Orange", "Science");
        when(repo.save(any(Question.class))).thenReturn(test_q);
    }

    @Test
    public void findByIdTest() {
        when(repo.findById(1)).thenReturn( new Question( "What color is the sky?",
                "Blue",
                "Green", "Purple", "Orange",
                "Science"));

        Question q = ques.findById(1);

        assertEquals("What color is the sky?", q.getQuestion());
        assertEquals("Science", q.getCategory());
        assertEquals(1, q.getId());

    }*/

}
