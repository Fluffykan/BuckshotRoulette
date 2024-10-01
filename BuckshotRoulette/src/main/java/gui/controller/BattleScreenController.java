package gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BattleScreenController implements Initializable {
    @FXML
    private ProgressBar playerHpBar, dealerHpBar;
    @FXML
    private Button shoot_self, shoot_dealer, use_item, cock, exit;
    @FXML
    private BorderPane borderPane;
    private final double MAX_HEALTH = 5.0;
    ItemBarController itemBarController;
    ActionBarController actionBarController;
    FlowPane itemBar;
    HBox actionBar;
    private int playerHealth = 5;
    private int dealerHealth = 5;
    enum Target {PLAYER, DEALER}

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        dealerHpBar.setProgress(1);
        playerHpBar.setProgress(1);
        try {
            // initialize actionBar
            FXMLLoader actionBarLoader = new FXMLLoader(getClass().getResource("/view/components/battle_screen/ActionBar.fxml"));
            actionBar = actionBarLoader.load();
            ActionBarController actionBarController = actionBarLoader.getController();
            actionBarController.setBattleScreen(this);

            // initialize itemBar
            FXMLLoader itemBarLoader = new FXMLLoader(getClass().getResource("/view/components/battle_screen/ItemBar.fxml"));
            itemBar = itemBarLoader.load();
            ItemBarController itemBarController =itemBarLoader.getController();
            itemBarController.setBattleScreenController(this);

            // display the actionBar
            borderPane.setBottom(actionBar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void shoot(Target target) {
        if (target == Target.DEALER) {
            dealerHealth--;
            dealerHpBar.setProgress(dealerHealth / MAX_HEALTH);
        } else {
            playerHealth--;
            playerHpBar.setProgress(playerHealth / MAX_HEALTH);
        }
    }

    public void useItem(int index) {
        // TODO: implement function to use an item at the index-th position
    }

    public void cock() {
        // TODO: implement function to cock gun and report the type of round ejected
    }

    public void showItemBar() {
        borderPane.setBottom(itemBar);
    }

    public void showActionBar() {
        borderPane.setBottom(actionBar);
    }

    public void quit() {
        // TODO: implement save and quit function
    }
}
