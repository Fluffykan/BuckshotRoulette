package gui.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import java.io.IOException;

public class StartScreenController {
    @FXML
    Button start, help, exit;
    Stage stage;
    Scene scene;

    public void exit(ActionEvent event) {
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

    public void start(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = stage.getScene();
        Parent root = FXMLLoader.load(getClass().getResource("/view/BattleScreen.fxml"));
        scene.setRoot(root);
        stage.show();
    }
}
