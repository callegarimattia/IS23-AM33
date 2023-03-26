package model;

public interface GamesHandlerInterface {
    boolean pickTiles(User user, int posX1, int posX2, int posY1, int posY2);

    boolean insertTiles(User user, int column, Tile tile1, Tile tile2, Tile tile3);

}
