package gui;

import client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiApplication extends Application {

    Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 275);
        LoginController loginController = (LoginController) fxmlLoader.getController();
        loginController.setClient(client);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}