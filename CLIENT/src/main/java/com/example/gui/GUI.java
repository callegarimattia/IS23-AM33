package com.example.gui;

import com.example.gui.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.example.gui.client.clientModel.DataModel;

import java.io.IOException;

public class GUI extends Application {
    Client client;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        FXMLLoader lobbyListLoader = new FXMLLoader(getClass().getResource("views/LobbyList.fxml"));
        root.setCenter(lobbyListLoader.load());
        LobbyListController lobbyListController = lobbyListLoader.getController();

        DataModel model = new DataModel();
        lobbyListController.initModel(model);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("MyShelfie");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
