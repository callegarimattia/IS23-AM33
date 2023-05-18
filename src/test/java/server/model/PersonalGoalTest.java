package server.model;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.model.personalGoals.PersonalGoal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonalGoalTest {

    PersonalGoal[] list = null;
    Tile[][][] goalMatrices = null;

    @BeforeEach
    void init() throws IOException {
        String data = new String(Files.readAllBytes(Paths.get("src/main/resources/JSONs/various_shelfs.json")));
        Gson g = new Gson();
        goalMatrices = g.fromJson(data, Tile[][][].class);

        String data2 = new String(Files.readAllBytes(Paths.get("src/main/resources/JSONs/data.json")));
        list = g.fromJson(data2, PersonalGoal[].class);
    }

    @Test
    @DisplayName("Personal Goal Points Test for PersonalGoal 2")
    void CountRightsTester12() throws IOException {
        init();
        PersonalGoal myGoal = list[1];
        assertEquals(12, myGoal.calcPoints(goalMatrices[0]));
    }

    @Test
    @DisplayName("Personal Goal Points Test for PersonalGoal 2")
    void CountRightsTester0() throws IOException {
        init();
        PersonalGoal myGoal = list[1];
        assertEquals(0, myGoal.calcPoints(goalMatrices[1]));
    }
}