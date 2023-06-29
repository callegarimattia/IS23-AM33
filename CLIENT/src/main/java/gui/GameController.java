package gui;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController {
    @FXML
    private ImageView player1ShelfImage;
    @FXML
    private ImageView player2ShelfImage;
    @FXML
    private ImageView player3ShelfImage;
    @FXML
    private GridPane player1ShelfGrid;
    @FXML
    private GridPane player2ShelfGrid;
    @FXML
    private GridPane player3ShelfGrid;
    @FXML
    private Button pickButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label player3;
    public Label player2;
    public Label player1;
    private Client client;
    @FXML
    private GridPane mainBoard;

    public void init(Client client) {
        this.client = client;
    }

    private void startGame() {
        initializeMainBoard();
        showShelfs();
        initializeShelfs();
        showCommonGoals();
        showPersonalGoal();
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

    private void showShelfs() {
        int gameSize = client.getData().getPlayers().size();
        if (gameSize < 4) {
            player3ShelfImage.setVisible(false);
            player3ShelfGrid.setVisible(false);
            player3.setVisible(false);
        }
        if (gameSize < 3) {
            player2ShelfImage.setVisible(false);
            player2ShelfGrid.setVisible(false);
            player2.setVisible(false);
        }
    }

    private void initializeShelfs() {

    }

    private void showCommonGoals() {

    }

    private void showPersonalGoal() {

    }

    private void endgame() {

    }

    @FXML
    private void handleExitActionEvent(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handlePickActionEvent(ActionEvent event) {

    }

}
