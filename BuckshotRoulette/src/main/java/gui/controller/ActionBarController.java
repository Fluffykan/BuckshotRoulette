package gui.controller;

public class ActionBarController {
    private BattleScreenController battleScreenController;
    public void shootDealer() {
            battleScreenController.shoot(BattleScreenController.Target.DEALER);
    }
    public void shootSelf() {
        battleScreenController.shoot(BattleScreenController.Target.PLAYER);
    }
    public void cock() {
        battleScreenController.cock();
    }
    public void showItemBar() {
        battleScreenController.showItemBar();
    }
    public void quit() {
        battleScreenController.quit();
    }
    public void setBattleScreen(BattleScreenController battleScreenController) {
        // this is here to allow the child class controller (which is the action bar)
        // to access methods in the parent class controller (which is the borderpane it is contained in)
        this.battleScreenController = battleScreenController;
    }
}
