package app;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final Random rng = new Random();
    public static boolean roundEnded = false;
    public static int currRound = 1;
    private static StringBuilder log = new StringBuilder();
    protected static Scanner sc = new Scanner(System.in); // to be used only within the package for app.Human

    // fields for the player, bot and gun
    private static Player bot = null;
    private static Player user = null;
    private static Gun gun = null;

    // CHANGE THE FIELDS BELOW TO MOD GAME //
    public static final int ITEMS_PER_ROUND = 6;
    public static final int ITEMS_PER_RELOAD = 1;
    public static final int USER_HEALTH = 5;
    public static final int DEALER_HEALTH = 5;
    public static final int MAX_ROUNDS = 3;
    // END OF MODIFIABLE FIELDS //

    public static void clearSaveState() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("SaveState.txt"));
            writer.write("");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveGame(Player user, Player bot, Gun gun) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("SaveState.txt"));
            System.out.println("Saving game...");

            // Save File format:
            // 1. userName\n
            // 2. userHealth\n
            // 3. userItemSlot1,userItemSlot2,...,userItemSlot(app.Inventory.INVENTORY_SIZE)\n
            // 4. userHasAnotherTurn\n
            // 5. userIsHandcuffed\n
            // 6. botHealth\n
            // 7. botItemSlot1,botItemSlot2,...,botItemSlot(app.Inventory.INVENTORY_SIZE)\n
            // 8. botHasAnotherTurn\n
            // 9. botIsHandcuffed\n
            // 10. RELOAD_POINTER\n
            // 11. damageMultiplier\n
            // 12. gunRound1,gunRound2,...,gunRoundN
            // 13. gameRoundNumber

            // save user
            writer.write(user.getName() + "\n"); // 1
            writer.write(user.getHealth() + "\n"); // 2
            StringBuilder inventory = new StringBuilder();
            for (String s : user.inventory.items) {
                inventory.append(s).append(",");
            }
            inventory.deleteCharAt(inventory.length() - 1);
            writer.write(inventory.toString() + "\n"); // 3
            writer.write(user.hasAnotherTurn() + "\n"); // 4
            writer.write(!user.canMove() + "\n"); // 5

            // save bot
            writer.write(bot.getHealth() + "\n"); // 6
            StringBuilder botInventory = new StringBuilder();
            for (String s : bot.inventory.items) {
                botInventory.append(s).append(",");
            }
            botInventory.deleteCharAt(botInventory.length() - 1);
            writer.write(botInventory.toString() + "\n"); // 7
            writer.write(bot.hasAnotherTurn() + "\n"); // 8
            writer.write(!bot.canMove() + "\n"); // 9

            // save gun
            StringBuilder magazine = new StringBuilder();
            for (Integer i : gun.getMag()) {
                magazine.append(i).append(",");
            }
            magazine.deleteCharAt(magazine.length() - 1);
            writer.write(gun.getRELOAD_POITNER() + "\n"); // 10
            writer.write(gun.getDamageMultiplier() + "\n"); // 11
            writer.write(magazine.toString() + "\n"); // 12

            // save currRound
            writer.write(currRound + "\n"); // 13


            writer.close();
            System.out.println("Saved game...");

        } catch (IOException e) {
            System.out.printf("Soemthing went wrong while saving... \nError: " + Arrays.toString(e.getStackTrace()));
        }
    }

    public static boolean loadGame() {
        // Save File format:
        // 1. userName\n
        // 2. userHealth\n
        // 3. userItemSlot1,userItemSlot2,...,userItemSlot(app.Inventory.INVENTORY_SIZE)\n
        // 4. userHasAnotherTurn\n
        // 5. userIsHandcuffed\n
        // 6. botHealth\n
        // 7. botItemSlot1,botItemSlot2,...,botItemSlot(app.Inventory.INVENTORY_SIZE)\n
        // 8. botHasAnotherTurn\n
        // 9. botIsHandcuffed\n
        // 10. RELOAD_POINTER\n
        // 11. damageMultiplier\n
        // 12. gunRound1,gunRound2,...,gunRoundN
        // 13. gameRoundNumber
        try {
            BufferedReader reader = new BufferedReader(new FileReader("SaveState.txt"));

            // config user
            user = Human.newHuman(reader.readLine()); // 1
            user.setHealth(Integer.parseInt(reader.readLine())); // 2
            // convert csv to String[]
            String[] userinv = reader.readLine().split(","); // 3
            user.setInventory(userinv);
            user.setHasAnotherTurn(Boolean.parseBoolean(reader.readLine())); // 4
            if (Boolean.parseBoolean(reader.readLine())) { user.handcuff();}

            // config bot
            bot = HackerDealer.newHackerDealer();
            bot.setHealth(Integer.parseInt(reader.readLine()));
            String[] botInv = reader.readLine().split(",");
            bot.setInventory(botInv);
            bot.setHasAnotherTurn(Boolean.parseBoolean(reader.readLine()));
            if (Boolean.parseBoolean(reader.readLine())) { bot.handcuff();}

            // config gun
            gun = Gun.customGun(new int[] {});
            gun.setRELOAD_POINTER(Integer.parseInt(reader.readLine()));
            gun.setDamageMultiplier(Integer.parseInt(reader.readLine()));
            String[] magazine = reader.readLine().split(",");
            gun.setMagazine(Arrays.stream(magazine).mapToInt(Integer::parseInt).toArray());

            // config game round
            currRound = Integer.parseInt(reader.readLine());
            reader.close();
            return true;

        } catch (IOException e) {
            System.out.print("Unable to load game");
            return false;
        }
    }
    public static void log(String s) {
        log.append(s);
    }

    private static void endRound(Player user, Player bot, Gun gun) {
        user.reset();
        bot.reset();
        user.drawItems();
        bot.drawItems();
        gun.reload();
        currRound++;
    }

    public static void main(String[] args) {
        boolean loadedGame = false;
        boolean hasSaveFile = new File("SaveState.txt").length() != 0;
        // initialise bot, user and gun first to be assigned a value either by loadGame or by the user later on
        // can be initialised as null because it will definitely be assigned a value later on, i.e. not be null


        System.out.println("Welcome to BuckshoOt Roulette");
        // checks if there is a save file, and if user wants to laod save file
        if (hasSaveFile) {
            System.out.println("Load Saved Game?\n1. Yes\n2. No");
            boolean validInput = false;
            while (!validInput) {
                while (!sc.hasNextInt()) {
                    System.out.println("Key in any number");
                    sc.next();
                }
                int decision = sc.nextInt();
                switch (decision) {
                    case 1:
                        validInput = true;
                        loadedGame = Main.loadGame();
                        break;
                    case 2:
                        validInput = true;
                        try {
                            new BufferedWriter(new FileWriter("SaveState.txt")).write("");
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                        break;
                    default:
                        break;

                }
            }
        }

        if (!loadedGame) {
            System.out.println("Key in any number");
            while (!sc.hasNextInt()) {
                System.out.println("Key in any number");
                sc.next();
            }
            Main.rng.setSeed(sc.nextInt());

            System.out.println("Enter your name:");
            while (!sc.hasNext()) {
                System.out.println("Enter your name:");
                sc.next();
            }
            String name = sc.next();

            // initialise a new app.Human app.Player, bot and gun
            user = Human.newHuman(name);
            user.drawItems();
            bot = HackerDealer.newHackerDealer();
            bot.drawItems();
            gun = Gun.newGun();

            System.out.printf("Welcome %s, let the games begin :)\n", user.getName());
        }

        while (currRound < MAX_ROUNDS) {
            System.out.printf("\nRound %s\nHealth:\napp.Dealer : %d\n%s : %d\n\n",
                    currRound, bot.getHealth(), user.getName(), user.getHealth());
            roundEnded = false;
            // set the hasNextTurn field of user and bot to true
            user.startOfNextTurn();
            bot.startOfNextTurn();

            System.out.printf("%s's turn\n", user.getName());
            if (!user.canMove()) {
                System.out.printf("%s is handcuffed and cannot move\n", bot.getName());
                // handcuff only disables player for 1 turn
                user.removeHandcuff(true);
            } else {
                while (user.hasAnotherTurn()) {
                    user.takeAction(gun, bot);
                }
            }

            if (roundEnded) {
                if (user.isDead()) {
                    System.out.println("Game Over... Good try :)");
                    System.exit(0);
                } else {
                    endRound(user, bot, gun);
                    continue;
                }
            }

            System.out.printf("%s's turn\n", bot.getName());
            if (!bot.canMove()) {
                System.out.printf("%s is handcuffed and cannot move\n", bot.getName());
                // handcuff only disables player for 1 turn
                bot.removeHandcuff(true);
            } else {
                while (bot.hasAnotherTurn()) {
                    bot.takeAction(gun, user);
                }
            }

            if (roundEnded) {
                if (user.isDead()) {
                    System.out.println("Game Over... Good try :)");
                    System.exit(0);
                } else {
                    endRound(user, bot, gun);
                }
            }
        }

        System.out.printf("Congratulations %s, you beat the dealer\n\n", user.getName());
        System.out.println(log);
        clearSaveState();
        System.exit(0);
    }
}