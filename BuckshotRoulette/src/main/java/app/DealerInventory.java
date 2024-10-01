package app;

import java.util.LinkedList;
import java.util.Queue;

public class DealerInventory extends Inventory {
    private final Queue<Integer> beerPos = new LinkedList<>();
    private final Queue<Integer> cigPos = new LinkedList<>();
    private final Queue<Integer> handcuffPos = new LinkedList<>();
    private final Queue<Integer> sawPos = new LinkedList<>();
    private final Queue<Integer> magGlassPos = new LinkedList<>();

    protected DealerInventory() {
        super();
    }

    public int getBeerPos() {
        if (beerPos.isEmpty()) {
            return -1;
        } else {
            return beerPos.remove();
        }
    }

    public int getCigPos() {
        if (cigPos.isEmpty()) {
            return -1;
        } else {
            return cigPos.remove();
        }
    }

    public int getCuffPos() {
        if (handcuffPos.isEmpty()) {
            return -1;
        } else {
            return handcuffPos.remove();
        }
    }

    public int getSawPos() {
        if (sawPos.isEmpty()) {
            return -1;
        } else {
            return sawPos.remove();
        }
    }

    public int getMagGlassPos() {
        if (magGlassPos.isEmpty()) {
            return -1;
        } else {
            return magGlassPos.remove();
        }
    }

    @Override
    public void generateItems(int numItems) {
        Queue<String> q = new LinkedList<>();

        // generate the required number of items
        for (int j = 0; j < numItems; j++) {
            q.add(ITEM_LIST[Main.rng.nextInt(MAX_ITEM_ID)]);
        }

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (q.isEmpty()) {
                break;
            }

            if (items[i].equals(USED)) {
                String s = q.remove();
                items[i] = s;

            } else {
                break;
            }
        }

        updateQueues();
    }

// FOR TESTING PURPOSES

    /**
     * adds an item into the inventory if the inventory is not full
     *
     * @param s is the new item to be added
     * @return a boolean of whether a new item is successfully added
     */
    protected boolean add(String s) {
        boolean valid = false;
        // check if s is a valid item
        for (String item : Inventory.ITEM_LIST) {
            if (s.equals(item)) {
                valid = true;
            }
        }
        if (valid) {
            // valid now represents whether s is added into the inventory successfully
            valid = false;
            for (int i = 0; i < Inventory.INVENTORY_SIZE; i++) {
                // check for an empty space to add s into the inventory
                if (this.items[i].equals(Inventory.USED)) {
                    items[i] = s;
                    valid = true;
                    // exit the loop after adding the item successfully
                    break;
                }
            }
            updateQueues();
        }
        return valid;
    }

    // FOR LOADING FROM SAVE STATE
    protected void updateQueues() {
        // reset all queues, then update with new positions
        beerPos.clear();
        cigPos.clear();
        magGlassPos.clear();
        handcuffPos.clear();
        sawPos.clear();

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            String item = items[i];
            // identify what item it is, and add to the corresponding linked list
            // holding the index of the item in the array
            switch (item) {
                case "BEER":
                    beerPos.add(i);
                    break;
                case "CIGARETTE":
                    cigPos.add(i);
                    break;
                case "HANDCUFF":
                    handcuffPos.add(i);
                    break;
                case "SAW":
                    sawPos.add(i);
                    break;
                case "MAGNIFYING GLASS":
                    magGlassPos.add(i);
                    break;
            }
        }
    }
}
