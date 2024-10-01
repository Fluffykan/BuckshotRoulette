package app;

public class HackerDealer extends Dealer {

    private HackerDealer() {
        super("DEALER", Main.DEALER_HEALTH);
    }

    public static HackerDealer newHackerDealer() {
        return new HackerDealer();
    }

    /**
     * used to let the dealer take an action
     * @return a boolean indicating whether the dealer can take another action
     */
    @Override
    public void takeAction(Gun gun, Player player) {
        // use magnifying glasses whenever possible as they have no use to the dealer
        int magGlass = getMagGlass();
        if (magGlass > -1) {
            useItem(magGlass, gun, player);
        }
        // use cigarettes while health is below maximum
        int cig = getCig();
        while (health < Main.DEALER_HEALTH && cig > -1) {
            useItem(cig, gun, player);
            cig = getCig();
        }
        // use beer while the next round is a blank
        int beer = getBeer();
        while (beer > -1 && gun.peek() == 0) {
            useItem(beer, gun, player);
            beer = getBeer();
        }
        // use the saw whenever next round is live
        int saw = getSaw();
        if (saw > -1 && gun.peek() == 1) {
            useItem(saw, gun, player);
        }
        // handcuff the opposing player whenever possible
        int cuff = getCuff();
        if (cuff > -1 && player.canMove()) {
            useItem(cuff, gun, player);
        }

        // if next round is a blank, shoot the dealer
        // else if it is a live, shoot the opposing player
        if (gun.peek() == 0) {
            shoot(this, gun);
            // dealer can take another turn if it shoots itself with a blank
        } else {
            shoot(player, gun);
            // dealer cannot take another turn if it shoots the opposing player
            hasAnotherTurn = false;
        }
    }

}
