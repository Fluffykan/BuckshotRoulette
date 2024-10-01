package gui.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class StartScreen {
    @FXML
    Button start, help, exit;
    Stage stage;


    public void exit(javafx.event.ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Goodbye");
        alert.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(delayOverEvent -> {
            alert.close();
            stage.close();
        });
        delay.play();
    }

    public void start(javafx.event.ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/blank.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
