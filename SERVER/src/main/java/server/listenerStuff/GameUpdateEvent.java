package server.listenerStuff;

import common.MainBoardCoordinates;

import java.util.EventObject;
import java.util.List;

public class GameUpdateEvent extends EventObject {
    private final List<MainBoardCoordinates> coordinates; // tiles that have been picked
    private final String updater;   //  they guy that did the command
    private final int column;  // where have been placed
    private final String newCurrPlayer;
    private List<Integer> types;

    public GameUpdateEvent(Object source, List<MainBoardCoordinates> coordinates, String updater, int column, String newCurrPlayer, List<Integer> types) {
        super(source);
        this.coordinates = coordinates;
        this.updater = updater;
        this.column = column;
        this.newCurrPlayer = newCurrPlayer;
        this.types = types;
    }

    public List<MainBoardCoordinates> getCoordinates() {
        return coordinates;
    }

    public String getUpdater() {
        return updater;
    }

    public int getColumn() {
        return column;
    }

    public String getNewCurrPlayer() {
        return newCurrPlayer;
    }

    public List<Integer> getTypes() {
        return types;
    }
}
