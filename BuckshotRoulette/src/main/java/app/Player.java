package app;

public abstract class Player {
    private String name;
    protected int health;
    protected final Inventory inventory;
    private boolean isHandcuffed = false;
    protected boolean hasAnotherTurn = true;

    public Player(String name, int health, Inventory inventory) {
        this.health = health;
        this.name = name;
        this.inventory = inventory;
    }

    // STATUS CHECK METHODS

    /**
     * @return a boolean indicating if the player is dead
     */
    public final boolean isDead() {
        return health < 1;
    }

    /**
     * @return a boolean indicating if the player can take their turn
     */
    public final boolean canMove() {
        return !isHandcuffed;
    }

    /**
     *
     * @return a boolean indicating if the palyer can take another turn after making an action
     */
    public final boolean hasAnotherTurn() {
        return hasAnotherTurn;
    }

    // STATUS MODIFIER METHODS

    /**
     * used to reset hasAnotherTurn to true after both the dealer and player has moved
     */
    public final void startOfNextTurn() {
        hasAnotherTurn = true;
    }
    /**
     * used to handcuff the player
     */
    public final void handcuff() {
        isHandcuffed = true;
    }

    /**
     * used to remove the handcuff on the player
     * @param printMsg is a boolean indicating whether there is a need to print and log the message
     */
    public final void removeHandcuff(boolean printMsg) {
        isHandcuffed = false;
        if (printMsg) {
            Main.log(String.format("%s broke free from handcuffs and can move next turn\n", name));
            System.out.printf("%s broke free from handcuffs and can move next turn\n", name);
        }
    }

    /**
     * used to reset the health of the player after each round
     */
    public abstract void reset(); // TO BE IMPLEMENTED
    public abstract void takeAction(Gun gun, Player player);

    // ACTION METHODS

    /**
     * used to draw x items after each reload
     */
    public final void drawItems() {
        inventory.generateItems(Main.ITEMS_PER_ROUND);
    }

    public final void drawItemsAfterReload() {
        inventory.generateItems(Main.ITEMS_PER_RELOAD);
    }
    /**
     * used to shoot the opposing player
     *
     * @param p is the opposing player
     * @param gun is the gun used in app.Main
     * @return a boolean indicating whether a successful shot is made
     */
    public final boolean shoot(Player p, Gun gun) {
        StringBuilder message = new StringBuilder();
        int dmg = gun.fire();
        p.health -= dmg;
        message.append(String.format("%s shot %s for %d damage\n", this.name, (p == this) ? "themself" : p.name, dmg));
        if (p.isDead()) {
            Main.roundEnded = true;
            message.append(String.format("%s died\n", p.name));
        }

        System.out.print(message.toString());
        Main.log(message.toString());


        if (dmg > 0 && p.isHandcuffed && !p.isDead()) {
            // only remove handcuffs on the player if they were handcuffed and shot for some damage
            p.removeHandcuff(true);
        }

        // auto reloads the gun if it is empty
        if (gun.isEmpty()) {
            gun.reload();
            this.drawItemsAfterReload();
            p.drawItemsAfterReload();
        }
        return dmg > 0;
    }

    /**
     * used to allow the player to use an item
     *
     * @param i is the position of the item in the inventory
     * @param gun is the gun used in app.Main
     * @param p is the opposing player
     * @return a boolean indicating if an item is successfully used
     */
    public final boolean useItem(int i, Gun gun, Player p) throws IllegalArgumentException {
        String item = null;
        if (i == Inventory.INVENTORY_SIZE) {
            return false;
        }
        try {
            item = inventory.getItem(i); // might throw IllegalArgumentException here
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid item slot number");
            return false;
        }
        if (item.equals(Inventory.USED)) {
            System.out.printf("No item to use at slot %d\n", i);
            return false;
        } else {
            StringBuilder message = new StringBuilder();

            switch(item) {
                case "SAW":
                    message.append(String.format("%s used SAW... gun has been sawed off\n", name));
                    gun.sawOff();
                    break;
                case "BEER":
                    message.append(String.format("%s used BEER... %s round ejected\n",
                            name, ((gun.cock() > 0) ? "live" : "blank")));
                    break;
                case "CIGARETTE":
                    if (health < 5) {
                        message.append(String.format("%s USED CIGARETTE... %s recovered 1 health with CIGARETTE\n", name, name));
                        health++;
                    } else {
                        message.append(String.format
                                ("%s used CIGARETTE... health is full... cannot heal... the cigarette burns up...\n", name));
                    }
                    break;
                case "HANDCUFF":
                    message.append(String.format("%s used HANDCUFFS... %s has been handcuffed\n", name, p.name));
                    p.handcuff();
                    break;
                case "MAGNIFYING GLASS":
                    message.append(String.format("%s used MAGNIFYING GLASS... next round is %s\n", name, ((gun.peek() == 0) ? "blank" : "live")));
                    break;
                default:
                    return false;
            }
            System.out.print(message.toString());
            Main.log(message.toString());
            if (gun.isEmpty()) {
                gun.reload();
                this.drawItemsAfterReload();
                p.drawItemsAfterReload();
            }
            return true;
        }
    }

    /**
     * used to print out the inventory of the player
     */
    public final void viewInventory() {
        System.out.printf("%s's app.Inventory\n", name);
        if (inventory == null) {
            System.out.println("No app.Inventory");
        } else {
            System.out.println(inventory);
        }
    }

    public final String getName() {
        return name;
    }


    // FOR TESTING PURPOSES / LOADING SAVE STATE
    protected int getHealth() {
        return health;
    }
    protected void setInventory(String[] items) {
        inventory.clearInventory();

        for (int i = 0; i < Inventory.INVENTORY_SIZE && i < items.length; i++) {
            inventory.add(items[i]);
        }
    }
    protected void setHealth(int prevhealth) {
        health = prevhealth;
    }
    protected void setHasAnotherTurn(boolean b) {
        hasAnotherTurn = b;
    }
}
