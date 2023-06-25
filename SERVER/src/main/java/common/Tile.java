package common;

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

    public int toInt() {
        switch (this) {
            case EMPTY:
                return 0;
            case UNAVAILABLE:
                return 1;
            case BOOK:
                return 2;
            case GAME:
                return 3;
            case FRAME:
                return 4;
            case PLANT:
                return 5;
            case TROPHY:
                return 6;
            case CAT:
                return 7;
        }
        return -666;
    }

    public Tile toTile(int x) {
        switch (x) {
            case 0:
                return EMPTY;
            case 1:
                return UNAVAILABLE;
            case 2:
                return BOOK;
            case 3:
                return GAME;
            case 4:
                return FRAME;
            case 5:
                return PLANT;
            case 6:
                return TROPHY;
            case 7:
                return CAT;
        }
        return UNAVAILABLE;
    }

    public String toString(){
        switch (this){
            case EMPTY:
                return ("\u001B[37m" + "EMPTY" + "\u001B[0m");
            case UNAVAILABLE:
                return ("\u001B[37m" + "UNAVAILABLE" + "\u001B[0m");
            case CAT:
                return ("\u001B[32m" + "CAT" + "\u001B[0m");
            case TROPHY:
                return ("\u001B[36m" + "TROPHY" + "\u001B[0m");
            case PLANT:
                return ("\u001B[35m" + "PLANT" + "\u001B[0m");
            case FRAME:
                return ("\u001B[34m" + "FRAME" + "\u001B[0m");
            case GAME:
                return ("\u001B[33m" + "GAME" + "\u001B[0m");
            case BOOK:
                return ("\u001B[31m" + "BOOK" + "\u001B[0m");
        }

        return "null";
    }

}
