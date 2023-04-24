package model.gameLogic;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class BoardTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    @DisplayName("Deserialization JSON test")
    void deserializeJSONBoard(int numOfPlayers) throws IOException {
        JsonArray matrix;
        Tile[][] mainBoard = new Tile[9][9];
        System.out.println(mainBoard);
        InputStream mainBoardJSON = Board.class.getResourceAsStream("/boardJSON/mainBoard.json5");
        assert mainBoardJSON != null;
        String json = CharStreams.toString(new InputStreamReader(mainBoardJSON, StandardCharsets.UTF_8));
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        switch (numOfPlayers) {
            case 4:
                matrix = (JsonArray) jsonObject.get("4players");
                break;
            case 3:
                matrix = (JsonArray) jsonObject.get("3players");
                break;
            case 2:
                matrix = (JsonArray) jsonObject.get("2players");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + numOfPlayers);
        }
        for (int i = 0; i < 9; i++) {
            mainBoard[i] = new Gson().fromJson(matrix.get(i), Tile[].class);
        }
        System.out.println(mainBoard);
    }
}