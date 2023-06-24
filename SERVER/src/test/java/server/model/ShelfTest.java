package server.model;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import common.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.model.personalGoals.PersonalGoalDrawer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShelfTest {
    Tile[][][] shelfs = null;
    Shelf myShelf;
    @BeforeEach
    void init() throws IOException {
        InputStream json = PersonalGoalDrawer.class.getResourceAsStream("/JSONs/PersonalGoals4Drawer.json");
        assert json != null;
        json = Tile.class.getResourceAsStream("/JSONs/Shelfs4ShelfTest.json");
        assert json != null;
        String data = CharStreams.toString(new InputStreamReader(json, StandardCharsets.UTF_8));
        shelfs = new Gson().fromJson(data, Tile[][][].class);
        myShelf = new Shelf();

    }

    @Test
    @DisplayName("Cluster Score test on empty matrix")
    void EmptyMatrixTester() {
        Tile[][] testMatrix = shelfs[0];
        assertEquals(0, myShelf.clusterScore(testMatrix));
    }

    @Test
    @DisplayName("Cluster Score test on 2 points matrix")
    void Matrix2PointsTester() {
        Tile[][] testMatrix = shelfs[1];
        assertEquals(2, myShelf.clusterScore(testMatrix));
    }

    @Test
    @DisplayName("Cluster Score test on 4 points matrix")
    void Matrix4PointsTester() {
        Tile[][] testMatrix = shelfs[2];
        assertEquals(4, myShelf.clusterScore(testMatrix));
    }

    @Test
    @DisplayName("Cluster Score test on 3 points matrix")
    void Matrix3PointsTester() {
        Tile[][] testMatrix = shelfs[3];
        assertEquals(3, myShelf.clusterScore(testMatrix));
    }

    @Test
    @DisplayName("Cluster Score test on 9 points matrix")
    void Matrix9PointsTester() {
        Tile[][] testMatrix = shelfs[4];
        assertEquals(9, myShelf.clusterScore(testMatrix));
    }

    @Test
    @DisplayName("Cluster Score test on 5 points matrix")
    void Matrix5PointsTester() {
        Tile[][] testMatrix = shelfs[5];
        assertEquals(5, myShelf.clusterScore(testMatrix));
    }


    @Test
    @DisplayName("Cluster Score test on 8 points matrix")
    void Matrix8PointsTester() {
        Tile[][] testMatrix = shelfs[6];
        assertEquals(8, myShelf.clusterScore(testMatrix));
    }

    @Test
    @DisplayName("Cluster Score test on 20 points matrix")
    void Matrix20PointsTester() {
        Tile[][] testMatrix = shelfs[7];
        for(int x = 0; x < 6; x++){
            for (int y = 0; y < 5; y++)
                System.out.print(testMatrix[x][y] + "   ");
            System.out.println();
        }
        assertEquals(20, myShelf.clusterScore(testMatrix));
    }


}
