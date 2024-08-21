import java.util.Scanner;

public class Human extends Player{
    private Human(String name){
        super(name, Main.USER_HEALTH, new Inventory());
    }

    public static Human newHuman(String name) {
        return new Human(name);
    }

    /**
     * to reset the health of the player after each round
     */
    @Override
    public void reset() {
        super.health = Main.USER_HEALTH;
        removeHandcuff(false);
    }

    /**
     * prompts the user for which action they want to take
     *
     * @param gun is the gun used in the game
     * @param player is the opposing player
     * @return whether the player can take another action after the current action
     */
    @Override
    public void takeAction(Gun gun, Player player) {
        Scanner sc = Main.sc;
        System.out.println(
                "What would you like to do? :\n" +
                        "1. Shoot\n" +
                        "2. Use item\n" +
                        "3. Check inventory\n" +
                        "4. Save Game\n" +
                        "5. Exit Game");
        while (!sc.hasNextInt()) {
            System.out.println(
                    "What would you like to do? :\n" +
                            "1. Shoot\n" +
                            "2. Use item\n" +
                            "3. Check inventory\n" +
                            "4. Save Game\n" +
                            "5. Exit Game");
            sc.next();
        }
        int action = sc.nextInt();
        switch (action) {
            case 1:
                System.out.println("Who to shoot?\n" +
                        "1. Yourself\n" +
                        "2. Dealer\n\n" +
                        "0. Cancel action");
                while (!sc.hasNextInt()) {
                    System.out.println("Who to shoot?\n" +
                            "1. Yourself\n" +
                            "2. Dealer\n\n" +
                            "0. Cancel action");
                    sc.next();
                }
                int response = sc.nextInt();
                switch (response) {
                    case 0:
                        // do nothing as user still has another turn
                        return;
                    case 1:
                        if (shoot(this, gun)) {
                            // by shooting yourself successfully, you cannot take another turn
                            hasAnotherTurn = false;
                            return;
                        } else {
                            // by shooting yourself unsuccessfully, you can take another turn
                            return;
                        }
                    case 2:
                        // by shooting the opponent, you cannot take another turn
                        shoot(player, gun);
                        hasAnotherTurn = false;
                        return;
                    default:
                        // if it is any other input, do nothing and give the player another turn
                        return;
                }
            case 2:
                System.out.println(this.inventory.toString());
                System.out.printf("%d. Cancel\n", inventory.INVENTORY_SIZE);
                System.out.println("Which item slot would you like to use?");
                while (!sc.hasNextInt()) {
                    System.out.println("Which item slot would you like to use?");
                    sc.next();
                }
                useItem(sc.nextInt(), gun, player);
                // player can take another turn by trying to use an item
                return;
            case 3:
                System.out.println(this.inventory.toString());
                // player can take another turn by checking their inventory
                return;
            case 4:
                Main.saveGame(this, player, gun);
                // player can take another turn by saving the game
                return;
            case 5:
                Main.saveGame(this, player, gun);
                System.out.print("Goodbye");
                System.exit(0);
            default:
                System.out.println("Invalid action");
                // player can take another turn by giving an invalid input
                return;
        }
    }
}
