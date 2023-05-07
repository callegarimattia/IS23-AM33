package model.lobbies;

import model.gameLogic.*;

import java.util.List;


public class GameHandler implements GameHandlerInterface {

    private Game myGame;

    @Override
    public boolean pickAndInsert(User turnUser, List<MainBoardCoordinates> coordinates, int column) throws InputException, LastRoundException {
        String turnPlayer = turnUser.getUserName();
        for (Player ply : myGame.getPlayers()) {
            if (turnPlayer.equals(ply.getUserName())) {
                //  forse sarebbe meglio non esporre game e avere il metodo picktiles su Lobby che lo chiama
                //  a sua volta sua Game, non so
                return myGame.pickAndInsert(ply.getUserName(), coordinates, column);
            }
        }
        return false;
    }

    public GameHandler(Game myGame) {
        this.myGame = myGame;
    }
}
