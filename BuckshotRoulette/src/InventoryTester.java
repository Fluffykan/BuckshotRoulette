import org.junit.Test;
import static org.junit.Assert.*;

public class InventoryTester {

    @Test
    public void TestGenerateItems() {
        Inventory i = new Inventory();
        assertEquals(0, i.numContents());

        i.generateItems(Main.ITEMS_PER_ROUND);
        assertEquals(Main.ITEMS_PER_ROUND, i.numContents());

        i.generateItems(Main.ITEMS_PER_ROUND);
        i.generateItems(Main.ITEMS_PER_ROUND);
        i.generateItems(Main.ITEMS_PER_ROUND);
        assertEquals(Math.min(Inventory.INVENTORY_SIZE, Main.ITEMS_PER_ROUND * 3), i.numContents());
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestGetterPart1() {
        Inventory i = Inventory.newInventory();
        i.getItem(-1);
        i.getItem(Inventory.INVENTORY_SIZE + 1);
    }

    @Test
    public void TestGetterPart2() {
        Inventory i = Inventory.newInventory();
        assertEquals(Inventory.USED, i.getItem(0));

        i.generateItems(Main.ITEMS_PER_ROUND);
        i.getItem(0);
        assertEquals(Main.ITEMS_PER_ROUND - 1, i.numContents());

        assertEquals(i.items[1], i.getItem(1));
    }

    @Test
    public void TestDealerInv() {
        DealerInventory i = new DealerInventory();

        i.generateItems(Main.ITEMS_PER_ROUND);
        assertEquals(i.numContents(), Main.ITEMS_PER_ROUND);

        i.generateItems(Main.ITEMS_PER_ROUND);


        int item = i.getBeerPos();
        while (item > -1) {
            assertEquals("BEER", i.getItem(item));
            item = i.getBeerPos();
        }

        item = i.getCigPos();
        while (item > -1) {
            assertEquals("CIGARETTE", i.getItem(item));
            item = i.getCigPos();
        }

        item = i.getCuffPos();
        while (item > -1) {
            assertEquals("HANDCUFF", i.getItem(item));
            item = i.getCuffPos();
        }

        item = i.getSawPos();
        while (item > -1) {
            assertEquals("SAW", i.getItem(item));
            item = i.getSawPos();
        }

        item = i.getMagGlassPos();
        while (item > -1) {
            assertEquals("MAGNIFYING GLASS", i.getItem(item));
            item = i.getMagGlassPos();
        }

        assertEquals(0, i.numContents());

    }

}
