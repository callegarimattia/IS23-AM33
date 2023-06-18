package server.model.commonGoals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonGoalDrawerTest {
    private final CommonGoalDrawer commonGoalDrawer = new CommonGoalDrawer();
    @Test
    void DrawerTest(){
        int test = 0;
        for(int i=0;i<100;i++){
            int x = commonGoalDrawer.drawer();
            if(x > 12 || x < 1 )
                    test = 1;
        }
        assertEquals(test, 0);
    }
    @Test
    void commonGoalInitializerTest(){
        CommonGoal commonGoal;
        commonGoal = commonGoalDrawer.commonGoalInitializer(1,-1);
        CommonGoal1 commonGoal1 = new CommonGoal1();
        if(commonGoal.equals(commonGoal1)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(2,-1);
        CommonGoal2 commonGoal2 = new CommonGoal2();
        if(commonGoal.equals(commonGoal2)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(3,-1);
        CommonGoal3 commonGoal3 = new CommonGoal3();
        if(commonGoal.equals(commonGoal3)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(4,-1);
        CommonGoal4 commonGoal4 = new CommonGoal4();
        if(commonGoal.equals(commonGoal4)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(5,-1);
        CommonGoal5 commonGoal5 = new CommonGoal5();
        if(commonGoal.equals(commonGoal5)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(6,-1);
        CommonGoal6 commonGoal6 = new CommonGoal6();
        if(commonGoal.equals(commonGoal6)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(7,-1);
        CommonGoal7 commonGoal7 = new CommonGoal7();
        if(commonGoal.equals(commonGoal7)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(8,-1);
        CommonGoal8 commonGoal8 = new CommonGoal8();
        if(commonGoal.equals(commonGoal8)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(9,-1);
        CommonGoal9 commonGoal9 = new CommonGoal9();
        if(commonGoal.equals(commonGoal9)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(1,-1);
        CommonGoal10 commonGoal10 = new CommonGoal10();
        if(commonGoal.equals(commonGoal10)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(11,-1);
        CommonGoal11 commonGoal11 = new CommonGoal11();
        if(commonGoal.equals(commonGoal11)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(12,-1);
        CommonGoal12 commonGoal12 = new CommonGoal12();
        if(commonGoal.equals(commonGoal12)) assertEquals(1,1);

        commonGoal = commonGoalDrawer.commonGoalInitializer(1,1);
        if(!commonGoal.equals(commonGoal1)) assertEquals(1,1);
    }
}
