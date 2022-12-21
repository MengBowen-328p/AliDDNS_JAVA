package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class GUI extends Application {
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene((Parent) FXMLLoader.load(getClass().getResource("GUI.fxml")));
//        Controller controller = new Controller();
        stage.getIcons().add(new Image("file:ali_ddns/src/resources/logo.png"));
        stage.setTitle("AliYun DDNS Control Panel!!!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
