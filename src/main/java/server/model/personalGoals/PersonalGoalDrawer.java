package server.model.personalGoals;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;

public class PersonalGoalDrawer {
    private final LinkedList<PersonalGoal> pile;

    public PersonalGoalDrawer() throws IOException {
        this.pile = new LinkedList<>();
        String data = new String(Files.readAllBytes(Paths.get("src/main/resources/PersonalGoalsJson/data.json")));
        Gson g = new Gson();
        PersonalGoal[] gjg = g.fromJson(data, PersonalGoal[].class);
        for(PersonalGoal p : gjg)
            pile.add(p);
        Collections.shuffle(pile);
    }

    public PersonalGoal draw() {
        return pile.pop();
    }

}
