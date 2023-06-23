package gui;
import client.Client;
import client.ClientTCP;
import client.Displayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GuiApplication extends Application implements Displayer {

    Client client;
    Stage stage;

    public GuiApplication() throws IOException {
        this.client = new ClientTCP(this);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 275);
        LoginController loginController = (LoginController) fxmlLoader.getController();
        loginController.setClient(client);
        loginController.setGui(this);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void ansToLogin(int answer) throws IOException {
        if (answer == 1) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Lobbies.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void shutDown() {
        //  chiude il ciclo while che è in attesa di aggiornamenti
    }
}