package model.gameLogic;

import model.gameLogic.personalGoals.PersonalGoal;
import model.gameLogic.personalGoals.PersonalGoal2;

public class PlayerTest {

    Player Niko = new Player("Niko");
    PersonalGoal myGoal = new PersonalGoal2();

    //  dovranno poi essere importati da json
    Tile[][] MyShelf = { { Tile.EMPTY, Tile.EMPTY, Tile.TROPHY, Tile.EMPTY, Tile.CAT },
            { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
            { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.BOOK, Tile.EMPTY },
            { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
            { Tile.EMPTY, Tile.GAME, Tile.EMPTY, Tile.FRAME, Tile.EMPTY },
            { Tile.PLANT, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
    };

    void tester1(){
        Niko.setPersonalGoal(myGoal);
        // come gli setto la shelf senza creare un nuovo metodo setter ?

    }


}
