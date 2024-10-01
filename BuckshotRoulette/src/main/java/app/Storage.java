package app;

public class Storage {
    private final String SAVE_FILE_LOCATION = "./data/saveState.txt";

    public boolean save() {
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
        return true;
    }

    public boolean load() {
        return true;
    }
}
