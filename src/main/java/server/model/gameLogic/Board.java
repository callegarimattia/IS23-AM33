package server.model.gameLogic;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private final Tile[][] mainBoard = new Tile[9][9];
    private final int MAX_ROW_NUM = 9;
    private final int MAX_COL_NUM = 9;
    private final TilesBag MyBag;

    public Board(int numOfPlayers) {
        MyBag = new TilesBag();
        try {
            deserializeJSONBoard(numOfPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RandomTiles(MyBag, mainBoard);
    }

    private void deserializeJSONBoard(int numOfPlayers) throws IOException {
        JsonArray matrix;
        InputStream mainBoardJSON = Board.class.getResourceAsStream("/boardJSON/mainBoard.json5");
        assert mainBoardJSON != null;
        String json = CharStreams.toString(new InputStreamReader(mainBoardJSON, StandardCharsets.UTF_8));
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        switch (numOfPlayers) {
            case 4:
                matrix = jsonObject.getAsJsonArray("4players");
                break;
            case 3:
                matrix = jsonObject.getAsJsonArray("3players");
                break;
            case 2:
                matrix = jsonObject.getAsJsonArray("2players");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + numOfPlayers);
        }
        for (int i = 0; i < 9; i++) {
            mainBoard[i] = new Gson().fromJson(matrix.get(i), Tile[].class);
        }
    }

    private void RandomTiles(TilesBag myBag, Tile[][] mainBoard) {
        for (int x = 0; x < 9; x++)
            for (int y = 0; y < 9; y++)
                if (mainBoard[x][y].equals(Tile.EMPTY))
                    mainBoard[x][y] = myBag.pickedTile();
    }

    private boolean areTilesPickable(List<Integer> xPos, List<Integer> yPos) {
        //controlla che siano adiacenti e che abbiano almeno una tile vuota vicina
        ArrayList<Integer> xSort = new ArrayList<>(xPos);
        ArrayList<Integer> ySort = new ArrayList<>(yPos);
        Collections.sort(xSort);
        Collections.sort(ySort);
        for(int i = 0; i < xPos.size(); i++){
            if (!isPickable(new MainBoardCoordinates(xSort.get(i), ySort.get(i)))) return false;
        }

        if (xPos.size() == 1) return true;

        boolean isAligned = true;
        for(int i = 0;  i < xPos.size()-1; i++) {
            if (xPos.get(i) != xPos.get(i + 1)) {
                isAligned = false;
                break;
            }
        }
        if (isAligned) {
            for (int i = 0; i < xPos.size() - 1; i++) {
                if (ySort.get(i) != ySort.get(i + 1) - 1) {
                    isAligned = false;
                    break;
                }
            }
        }
        if (isAligned) {
            return true;
        }

        for (int i = 0; i < yPos.size() - 1; i++) {
            if (yPos.get(i) != yPos.get(i + 1)) return false;
        }
        for (int i = 0; i < xPos.size() - 1; i++) {
            if (xSort.get(i) != xSort.get(i + 1) - 1) return false;
        }
        return true;
    }

    private boolean isPickable(MainBoardCoordinates coordinate) {
        Tile tile = mainBoard[coordinate.getX()][coordinate.getY()];
        // If tile isn't on the board return false
        if (tile.equals(Tile.UNAVAILABLE) || tile.equals(Tile.EMPTY)) return false;
        // Check neighbors for at least one empty
        int empty = 0;
        Tile nearTile = mainBoard[coordinate.getX() - 1][coordinate.getY()];
        if (nearTile.equals(Tile.UNAVAILABLE) || nearTile.equals(Tile.EMPTY)) return true;
        nearTile = mainBoard[coordinate.getX() + 1][coordinate.getY()];
        if (nearTile.equals(Tile.UNAVAILABLE) || nearTile.equals(Tile.EMPTY)) return true;
        nearTile = mainBoard[coordinate.getX()][coordinate.getY() + 1];
        if (nearTile.equals(Tile.UNAVAILABLE) || nearTile.equals(Tile.EMPTY)) return true;
        nearTile = mainBoard[coordinate.getX()][coordinate.getY() - 1];
        return nearTile.equals(Tile.UNAVAILABLE) || nearTile.equals(Tile.EMPTY);
    }

    public ArrayList<Tile> removeTiles(List<MainBoardCoordinates> coordinates) {
        if (coordinates.isEmpty()) return null;
        ArrayList<Tile> pickedTiles = new ArrayList<Tile>();
        for (MainBoardCoordinates coordinates1 : coordinates) {
            pickedTiles.add(mainBoard[coordinates1.getX()][coordinates1.getY()]);
            mainBoard[coordinates1.getX()][coordinates1.getY()] = Tile.EMPTY;
        }
        return pickedTiles;
    }

    public Tile[][] getMainBoard() {
        return mainBoard;
    }
}