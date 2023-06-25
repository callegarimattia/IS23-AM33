package gui;

import client.Displayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiApplication extends Application implements Displayer {

    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 200);
        LoginController loginController = (LoginController) fxmlLoader.getController();
        loginController.init(this);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    @Override
    public void shutDown() {
        //  chiude il ciclo while che è in attesa di aggiornamenti
    }
}