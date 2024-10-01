package app;

import java.util.LinkedList;
import java.util.Queue;

public final class Gun {
    private final int[] MAG_SIZE_PER_RELOAD = {3, 5, 5, 7};
    private int RELOAD_POINTER = 0;
    private final Queue<Integer> magazine = new LinkedList<>();
    private int damageMultiplier = 1;

    private Gun(int[] newMag) {
        for (int i : newMag) {
            magazine.add(i);
        }
    }

    public static Gun newGun() {
        Gun gun = new Gun(new int[] {});
        gun.reload(); // immediately load the first magazine of rounds
        return gun;
    }

    /**
     * doubles the damage of the next shot
     */
    public void sawOff() {
        damageMultiplier = 2;
    }

    /**
     * @return damage dealt to the person being shot
     */
    public int fire() {
        if (magazine.isEmpty()) {
            // should never happen
            System.err.println("Empty Magazine");
            System.exit(1);
        }
        int dmg =  magazine.remove() * damageMultiplier;
        if (damageMultiplier > 1) {
            // reset the damage multiplier to 1 after firing
            damageMultiplier = 1;
        }
        return dmg;
    }

    /**
     * used to cock the gun
     * @return the type of round ejected, live or blank
     */
    public int cock() {
        if (magazine.isEmpty()) {
            // should never happen
            System.err.println("Empty Magazine");
            System.exit(1);
        }
        int ejected = magazine.remove();
        return ejected;
    }

    /**
     * reloads the gun with the predetermined number of rounds
     */
    public void reload() {
        System.out.println("\nReloading...");
        // clear magazine in the event reloading with some rounds in mag
        magazine.clear();

        for (int i = 0; i < MAG_SIZE_PER_RELOAD[RELOAD_POINTER]; i++) {
            magazine.add(Main.rng.nextInt(2));
        }
        int blank = 0;
        int live = 0;
        for (int i : magazine) {
            if (i == 0) {
                blank++;
            } else {
                live++;
            }
        }
        if (blank < magazine.size() / 3 || live < magazine.size() / 3) {
            // ensure that there is a good mix of blanks and live in the magazine
            reload();
            return;
        }
        String message = String.format("%d rounds loaded, %d blank, %d live\n\n", blank + live, blank, live);
        System.out.printf(message);
        Main.log(message);

        if (RELOAD_POINTER < MAG_SIZE_PER_RELOAD.length - 1) {
            RELOAD_POINTER++;
        }
    }

    /**
     * @return the next round in chamber for the player to decide next action
     */
    public int peek() {
        return magazine.peek() == null ? -1 : magazine.peek();
    }

    public boolean isEmpty() {
        return magazine.isEmpty();
    }

    // FOR TESTING
    /**
     * factory method
     * @param magConfig is the sequence of rounds to be loaded in the gun
     * @return a new gun meeting the specified configuration
     */
    protected static Gun customGun(int[] magConfig) {
        return new Gun(magConfig);
    }

    /**
     *
     * @return the number of rounds left in the magazine
     */
    protected int roundsLeft() {
        return magazine.size();
    }

    /**
     *
     * @return the Queue<Integer> which is the magazine
     */
    protected Queue<Integer> getMag() {
        return magazine;
    }

    /**
     * sets the magazine to the specified order of rounds when loading from a save file
     *  if it is less than or equal to the current maximum number of rounds allowed in the magazine
     * @param magConfig is the order of rounds to be loaded in the magazine
     */
    protected void setMagazine(int[] magConfig) {
        if (magConfig.length <= MAG_SIZE_PER_RELOAD[RELOAD_POINTER]) {
            magazine.clear();
            for (int i : magConfig) {
                magazine.add(i);
            }
        }
    }

    protected int getRELOAD_POITNER() {
        return RELOAD_POINTER;
    }
    /**
     * sets RELOAD_POINTER to i when loading from a save file, if i is a valid value
     * @param i is the new value of RELOAD_POINTER
     */
    protected void setRELOAD_POINTER(int i) {
        if (i > 0 && i < MAG_SIZE_PER_RELOAD.length) {
            RELOAD_POINTER = i;
        }
    }

    protected int getDamageMultiplier() {
        return damageMultiplier;
    }
    /**
     * sets damageMultiplier to i when loading from a save file, if i is a valid value (i.e. 0 or 1)
     * @param i is the new value of damageMultiplier
     */
    protected void setDamageMultiplier(int i) {
        if (i == 1 || i == 2) {
            damageMultiplier = i;
        }
    }

}