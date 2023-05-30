package server.model;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import common.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.model.personalGoals.PersonalGoal;
import server.model.personalGoals.PersonalGoalDrawer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonalGoalTest {

    PersonalGoal[] list = null;
    Tile[][][] goalMatrices = null;

    @BeforeEach
    void init() throws IOException {
        InputStream json = PersonalGoalDrawer.class.getResourceAsStream("/JSONs/PersonalGoals4Drawer.json");
        assert json != null;
        String data = CharStreams.toString(new InputStreamReader(json, StandardCharsets.UTF_8));
        list = new Gson().fromJson(data, PersonalGoal[].class);


        json = Tile.class.getResourceAsStream("/JSONs/Shelfs4PersonalGoalTests.json");
        assert json != null;
        data = CharStreams.toString(new InputStreamReader(json, StandardCharsets.UTF_8));
        goalMatrices = new Gson().fromJson(data, Tile[][][].class);

    }

    // goal matrices:
    // 1: empty matrix
    // 2: 12 punti per personal goal 6
    // 3: 4 punti per personal goal 1
    // 4: 1 punto per personal goal 10
    // 5: 9 punti per personal goal 4


    @Test
    @DisplayName("Personal Goal Points Test1 for PersonalGoal 6")
    void CountRightsTester1() {
        PersonalGoal myGoal = list[5];
        assertEquals(0, myGoal.calcPoints(goalMatrices[0]));
    }

    @Test
    @DisplayName("Personal Goal Points Test2 for PersonalGoal 6")
    void CountRightsTester2() {
        PersonalGoal myGoal = list[5];
        assertEquals(12, myGoal.calcPoints(goalMatrices[1]));
    }

    @Test
    @DisplayName("Personal Goal Points Test1 for PersonalGoal 1")
    void CountRightsTester3() {
        PersonalGoal myGoal = list[0];
        assertEquals(4, myGoal.calcPoints(goalMatrices[2]));
    }

    @Test
    @DisplayName("Personal Goal Points Test1 for PersonalGoal 10")
    void CountRightsTester4() {
        PersonalGoal myGoal = list[9];
        assertEquals(1, myGoal.calcPoints(goalMatrices[3]));
    }

    @Test
    @DisplayName("Personal Goal Points Test2 for PersonalGoal 10")
    void CountRightsTester5() {
        PersonalGoal myGoal = list[9];
        assertEquals(0, myGoal.calcPoints(goalMatrices[2]));
    }

    @Test
    @DisplayName("Personal Goal Points Test1 for PersonalGoal 4")
    void CountRightsTester6() {
        PersonalGoal myGoal = list[3];
        assertEquals(9, myGoal.calcPoints(goalMatrices[4]));
    }


}