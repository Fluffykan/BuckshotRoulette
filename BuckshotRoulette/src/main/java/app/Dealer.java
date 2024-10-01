package app;

public abstract class Dealer extends Player {
    public Dealer(String name, int health) {
        super(name, health, new DealerInventory());
    }
    /**
     * resets the health of the dealer at the start of the round
     */
    @Override
    public void reset() {
        health = Main.DEALER_HEALTH;
        removeHandcuff(false);
    }

    public int getBeer() {
        return ((DealerInventory) inventory).getBeerPos();
    }
    public int getCig() {
        return ((DealerInventory) inventory).getCigPos();
    }
    public int getSaw() {
        return ((DealerInventory) inventory).getSawPos();
    }
    public int getCuff() {
        return ((DealerInventory) inventory).getCuffPos();
    }
    public int getMagGlass() {
        return ((DealerInventory) inventory).getMagGlassPos();
    }
    public void updateQueues() {
        ((DealerInventory) inventory).updateQueues();
    }


}
