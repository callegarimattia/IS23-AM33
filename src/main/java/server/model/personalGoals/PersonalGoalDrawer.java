package server.model.personalGoals;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;

public class PersonalGoalDrawer {
    private final LinkedList<PersonalGoal> pile;

    public PersonalGoalDrawer() throws IOException {
        this.pile = new LinkedList<>();
        InputStream json = PersonalGoalDrawer.class.getResourceAsStream("/JSONs/PersonalGoals4Drawer.json");
        assert json != null;
        String data = CharStreams.toString(new InputStreamReader(json, StandardCharsets.UTF_8));
        PersonalGoal[] all = new Gson().fromJson(data, PersonalGoal[].class);
        Collections.addAll(pile, all);
        Collections.shuffle(pile);
    }

    public PersonalGoal draw() {
        return pile.pop();
    }

}
