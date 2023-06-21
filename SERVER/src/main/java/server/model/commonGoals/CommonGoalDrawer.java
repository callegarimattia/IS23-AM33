package server.model.commonGoals;
import java.util.Collections;
import java.util.LinkedList;

public class CommonGoalDrawer {
    private final LinkedList<CommonGoal> pile;
    public CommonGoalDrawer() {
        pile = new LinkedList<>();
        pile.add(new CommonGoal1());
        pile.add(new CommonGoal2());
        pile.add(new CommonGoal3());
        pile.add(new CommonGoal4());
        pile.add(new CommonGoal5());
        pile.add(new CommonGoal6());
        pile.add(new CommonGoal7());
        pile.add(new CommonGoal8());
        pile.add(new CommonGoal9());
        pile.add(new CommonGoal10());
        pile.add(new CommonGoal11());
        pile.add(new CommonGoal12());
        Collections.shuffle(pile);
    }

    public CommonGoal draw(){
        return pile.pop();
    }

}
