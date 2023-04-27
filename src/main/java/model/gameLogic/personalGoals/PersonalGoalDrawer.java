package model.gameLogic.personalGoals;

import java.util.Collections;
import java.util.LinkedList;

public class PersonalGoalDrawer {
    private final LinkedList<PersonalGoal> pile;

    public PersonalGoalDrawer() {
        this.pile = new LinkedList<>();
        pile.add(new PersonalGoal1());
        pile.add(new PersonalGoal2());
        pile.add(new PersonalGoal3());
        pile.add(new PersonalGoal4());
        pile.add(new PersonalGoal5());
        pile.add(new PersonalGoal6());
        pile.add(new PersonalGoal7());
        pile.add(new PersonalGoal8());
        pile.add(new PersonalGoal9());
        pile.add(new PersonalGoal10());
        pile.add(new PersonalGoal11());
        pile.add(new PersonalGoal12());
        Collections.shuffle(pile);
    }

    public PersonalGoal draw() {
        return pile.pop();
    }

}
