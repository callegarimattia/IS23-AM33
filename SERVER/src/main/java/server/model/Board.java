package server.model;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.MainBoardCoordinates;
import common.Tile;
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
        int line = coordinate.getRow();
        int col = coordinate.getCol();
        if (mainBoard[line][col].equals(Tile.EMPTY) || mainBoard[line][col].equals(Tile.UNAVAILABLE))
            return false;
        if (line == 0 || line == 8 || col == 0 || col == 8 )
            return true;
        return mainBoard[line + 1][col].equals(Tile.EMPTY) || mainBoard[line + 1][col].equals(Tile.UNAVAILABLE) ||
                mainBoard[line - 1][col].equals(Tile.EMPTY) || mainBoard[line - 1][col].equals(Tile.UNAVAILABLE) ||
                mainBoard[line][col + 1].equals(Tile.EMPTY) || mainBoard[line][col + 1].equals(Tile.UNAVAILABLE) ||
                mainBoard[line][col - 1].equals(Tile.EMPTY) || mainBoard[line][col - 1].equals(Tile.UNAVAILABLE);
    }


    //  è importante che siano ordinate: coordinates[0] deve essere la prima ad essere pickata
    public ArrayList<Tile> removeTiles(List<MainBoardCoordinates> coordinates) throws NotPickableException {
        if (coordinates == null) return null;
        if (coordinates.isEmpty()) return null;
        for (MainBoardCoordinates coord : coordinates)
            if (!this.isPickable(coord)){
                System.out.println("[" + coord.getRow()+"]["+coord.getCol()+"] is not pickable");
                throw new NotPickableException();
            }

        ArrayList<Tile> pickedTiles = new ArrayList<>();
        for (MainBoardCoordinates XY : coordinates) {
            pickedTiles.add(mainBoard[XY.getRow()][XY.getCol()]);
            mainBoard[XY.getRow()][XY.getCol()] = Tile.EMPTY;
        }
        return pickedTiles;
    }

    public Tile[][] getMainBoard() {
        return mainBoard;
    }

    public Tile[][] copyMatrix() {
        Tile[][] copy = new Tile[9][9];
        for (int x = 0; x < 9; x++)
            for (int y = 0; y < 9; y++)
                copy[x][y] = mainBoard[x][y];
        return copy;
    }

    public List<List<Integer>> toInt() {
        List<List<Integer>> copy = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            List<Integer> lis = new ArrayList<>();
            for (int y = 0; y < 9; y++)
                lis.add(mainBoard[x][y].toInt());
            copy.add(lis);
        }
        return copy;
    }

    public void refresh(){  // server debug purpose only
        for(int i = 0; i < 9; i++){
            for (int j = 0; j<9; j++)
                System.out.printf("%20s ", mainBoard[i][j]);
            System.out.println();
        }
    }

}