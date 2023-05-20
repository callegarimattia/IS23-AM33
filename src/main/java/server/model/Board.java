package server.model;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.exceptions.NotPickableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int MAX_ROW_NUM = 9;
    private final int MAX_COL_NUM = 9;
    private final Tile[][] mainBoard = new Tile[MAX_ROW_NUM][MAX_COL_NUM];
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
        InputStream mainBoardJSON = Board.class.getResourceAsStream("/JSONs/mainBoard.json5");
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

    private boolean isPickable(MainBoardCoordinates coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        if (mainBoard[x][y].equals(Tile.EMPTY) || mainBoard[x][y].equals(Tile.UNAVAILABLE))
            return false;
        if(x==0 || x==8 || y==0 || y==8)
            return true;
        return mainBoard[x + 1][y].equals(Tile.EMPTY) || mainBoard[x + 1][y].equals(Tile.UNAVAILABLE) ||
                mainBoard[x - 1][y].equals(Tile.EMPTY) || mainBoard[x - 1][y].equals(Tile.UNAVAILABLE) ||
                mainBoard[x][y + 1].equals(Tile.EMPTY) || mainBoard[x][y + 1].equals(Tile.UNAVAILABLE) ||
                mainBoard[x][y - 1].equals(Tile.EMPTY) || mainBoard[x][y - 1].equals(Tile.UNAVAILABLE);
    }


    //  è importante che siano ordinate: coordinates[0] deve essere la prima ad essere pickata
    public ArrayList<Tile> removeTiles(List<MainBoardCoordinates> coordinates) throws NotPickableException {
        if(coordinates == null) return null;
        if (coordinates.isEmpty()) return null;
        for (MainBoardCoordinates XY : coordinates)
            if(!this.isPickable(XY))
                throw new NotPickableException();
        ArrayList<Tile> pickedTiles = new ArrayList<>();
        for (MainBoardCoordinates XY : coordinates) {
            pickedTiles.add(mainBoard[XY.getX()][XY.getY()]);
            mainBoard[XY.getX()][XY.getY()] = Tile.EMPTY;
        }
        return pickedTiles;
    }

    public Tile[][] getMainBoard() {
        return mainBoard;
    }

    public Tile[][] copyMatrix (){
        Tile[][] copy = new Tile[9][9];
        for (int x = 0; x < 9; x++)
            for (int y = 0; y < 9; y++)
                copy[x][y] = mainBoard[x][y];
        return copy;
    }
}