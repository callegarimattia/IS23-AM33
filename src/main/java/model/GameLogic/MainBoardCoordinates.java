package model.GameLogic;

public class MainBoardCoordinates {
    private int x;
    private int y;

    public MainBoardCoordinates(int x, int y) {
        setXY(x, y);
    }

    private boolean setXY(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9) return false;
        this.x = x;
        this.y = y;
        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
