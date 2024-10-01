package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {


    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("BuckshotRoulette");
            Image image = new Image(getClass().getResourceAsStream("/images/icon.jpg"));
            stage.getIcons().add(image);


            Parent root = FXMLLoader.load(getClass().getResource("/view/StartScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
