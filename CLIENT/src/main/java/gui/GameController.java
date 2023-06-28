package gui;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameController {
    public Button pick;
    public Button exit;
    public Label player3;
    public Label player2;
    public Label player1;
    private Client client;
    @FXML
    private GridPane mainBoard;

    public void init(Client client) {
        this.client = client;
        client.getData().gameStatusProperty().addListener((observable, oldValue, newValue) -> initializeMainBoard());
    }

    private void initializeMainBoard() {
        int tileSize = 9 * 9;
        Image[][] mainBoard = new Image[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                mainBoard[i][j] = client.getData().getMainBoard()[i][j].toImage();
            }
        }
        this.mainBoard.setHgap(2);
        this.mainBoard.setVgap(2);
        for (int y = 0; y < mainBoard.length; y++) {
            for (int x = 0; x < mainBoard[y].length; x++) {
                if (mainBoard[x][y] == null) return;
                ImageView imageView = new ImageView(mainBoard[x][y]);
                imageView.setFitWidth(tileSize);
                imageView.setFitHeight(tileSize);
                this.mainBoard.add(imageView, x, y);
            }
        }
    }

    @FXML
    private void exitOnClick() {

    }

    @FXML
    private void pickOnClick() {
    }

}
