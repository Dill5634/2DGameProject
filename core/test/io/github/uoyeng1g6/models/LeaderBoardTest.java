package io.github.uoyeng1g6.models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class LeaderBoardTest {

    LeaderBoard myLeaderBoard;
    @BeforeEach
    void setUp(){
        File temp = new File("leaderboard.txt");
        temp.delete();
        myLeaderBoard = new LeaderBoard();
    }

    @Test
    void testEntry(){
        // Entry(float) contructor class
        // test creating a new entry and that score is stored correctly
        LeaderBoard.Entry e = myLeaderBoard.new Entry(0f);
        assertEquals(e.getScore(), 0f);

        e.setNickname("Test");
        assertEquals(e.getNickname(), "Test");

        // Entry(string, float) constructor class
        LeaderBoard.Entry e2 = myLeaderBoard.new Entry("Test2", 0f);
        assertEquals("Test2", e2.getNickname());
        assertEquals(0f, e2.getScore());
    }

    @Test
    void insert() {

        //check inserts new entries and orders correctly

        for(int i = 0; i < 10; i++) {
            String name = "Testing:" + i;
            myLeaderBoard.insert(name, (100f-10*i));
        }

        for(int i = 0; i < 10; i++){
            String correct = "Testing:" + i;
            assertEquals(correct, myLeaderBoard.show(i)[0]);
            assertEquals((100f-10*i), Float.valueOf(myLeaderBoard.show(i)[1]));
        }

        //test that entry with too low of a score is not appended
        assertFalse(myLeaderBoard.insert("Too Low", 0f));
        assertNotEquals("Too Low", myLeaderBoard.show(9)[0]);

        //test that new entry with higher score is added to the top
        assertTrue(myLeaderBoard.insert("New", 200f));
        assertEquals("New", myLeaderBoard.show(0)[0]);

        //test that previous bottom entry has been removed
        assertNotEquals("Testing:9", myLeaderBoard.show(9)[0]);
    }
}