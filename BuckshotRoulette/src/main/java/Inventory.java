import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.*;

public class Inventory {
    public static final String USED = "";
    public static final String[] ITEM_LIST = {"SAW", "BEER", "CIGARETTE", "HANDCUFF", "MAGNIFYING GLASS"};
    public static final int MAX_ITEM_ID = ITEM_LIST.length - 1;
    public static final int INVENTORY_SIZE = 8;
    protected final String[] items;

    protected Inventory() {
        items = new String[INVENTORY_SIZE];
        Arrays.fill(items, USED);
    }

    public static Inventory newInventory() {
        return new Inventory();
    }

    /**
     * used to get and remove an item from the inventory
     * @param id is the position of the item in the array
     * @return a string representing the item
     */
    public String getItem(int id) throws IllegalArgumentException {
        if (id > INVENTORY_SIZE - 1 || id < 0) {
            throw new IllegalArgumentException("id out of bounds");
        }
        String item = items[id];
        items[id] = USED;
        return item;
    }

    /**
     * used to generate Main.ITEMS_PER_ROUND items after every reload
     */
    public void generateItems(int numItems) {
        Queue<String> q = new LinkedList<>();

        // generate the required number of items
        for (int j = 0; j < numItems; j++) {
            q.add(ITEM_LIST[Main.rng.nextInt(MAX_ITEM_ID + 1)]);
        }

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (q.isEmpty()) {
                break;
            }
            if (items[i].equals(USED)) {
                // sequentially add items into the array according to the available space
                items[i] = q.remove();
            }
        }
    }

    /**
     *
     * @return the string representation of the Inventory
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            sb.append(String.format("Slot %d: %s\n", i, items[i]));
        }
        return sb.toString();
    }

    // FOR TESTING PURPOSES

    /**
     *
     * @return number of usable items currently in inventory
     */
    protected int numContents() {
        int count = 0;
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (!items[i].equals(USED)) {
                count++;
            }
        }
        return count;
    }

    /**
     *
     * @param items is the predetermined array of items to be in the new Inventory
     * @return an Inventory containing the predetermined items
     */
    protected static Inventory customInventory(String[] items) {
        Inventory inventory = new Inventory();
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            inventory.items[i] = items[i];
        }
        return inventory;
    }

    /**
     * adds an item into the inventory if the inventory is not full
     *
     * @param s is the new item to be added
     * @return a boolean of whether a new item is successfully added
     */
    protected boolean add(String s) {
        boolean valid = false;
        // check if s is a valid item
        for (String item : ITEM_LIST) {
            if (s.equals(item)) {
                valid = true;
            }
        }
        if (valid) {
            // valid now represents whether s is added into the inventory successfully
            valid = false;
            for (int i = 0; i < INVENTORY_SIZE; i++) {
                // check for an empty space to add s into the inventory
                if (items[i].equals(USED)) {
                    items[i] = s;
                    valid = true;
                    // exit the loop after adding the item
                    break;
                }
            }
        }
        return valid;
    }

    protected void clearInventory() {
        Arrays.fill(items, USED);
    }
}
