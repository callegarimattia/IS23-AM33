package server.model;

public enum Tile {

    /**
     * EMPTY will be used for the locations that still could be occupied in a player's
     * matrix or for the locations from which a tile was taken in the dashboard
     * UNAVAILABLE will be used for the positions in the dashboard that are not
     * valid due to the rules about number of players in the game
     */
    EMPTY,
    UNAVAILABLE,
    CAT,
    BOOK,
    GAME,
    FRAME,
    TROPHY,
    PLANT;

}
