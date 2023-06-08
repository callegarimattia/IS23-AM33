package server.model.commonGoals;

import java.util.Random;

public class CommonGoalDrawer {
    Random random = new Random();
    public CommonGoalDrawer() {

    }

    public int drawer(){
        int x = random.nextInt(12)+1;
        return x;
    }

    public CommonGoal commonGoalInitializer(int x, int same){
        while(x == same){
            x = drawer();
        }
        switch(x){
            case 1:
                return new CommonGoal1();
            case 2:
                return new CommonGoal2();
            case 3:
                return new CommonGoal3();
            case 4:
                return new CommonGoal4();
            case 5:
                return new CommonGoal5();
            case 6:
                return new CommonGoal6();
            case 7:
                return new CommonGoal7();
            case 8:
                return new CommonGoal8();
            case 9:
                return new CommonGoal9();
            case 10:
                return new CommonGoal10();
            case 11:
                return new CommonGoal11();
            case 12:
                return new CommonGoal12();
        }
        return null;
    }
}
