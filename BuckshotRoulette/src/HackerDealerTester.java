import org.junit.Test;
import static org.junit.Assert.*;
public class HackerDealerTester {
    @Test
    public void InitialiseTest() {
        HackerDealer dealer = HackerDealer.newHackerDealer();

        assertEquals(Main.DEALER_HEALTH, dealer.getHealth());
        assertNotNull(dealer.inventory);

    }
    @Test
    public void ShootTest() {
        HackerDealer dealer = HackerDealer.newHackerDealer();
        Gun gun = Gun.customGun(new int[] {1, 1, 0, 0, 1});
        int currHealth = Main.DEALER_HEALTH;

        gun.sawOff();
        dealer.shoot(dealer, gun);
        currHealth -= 2;
        assertEquals(currHealth, dealer.getHealth());

        dealer.shoot(dealer, gun);
        currHealth -= 1;
        assertEquals(currHealth, dealer.getHealth());

        dealer.shoot(dealer, gun);
        assertEquals(currHealth, dealer.getHealth());

        gun.sawOff();
        dealer.shoot(dealer, gun);
        assertEquals(currHealth, dealer.getHealth());
    }

    @Test
    public void ResetTest() {
        HackerDealer dealer = HackerDealer.newHackerDealer();
        Gun gun = Gun.customGun(new int[] {1, 1, 1, 1, 1});
        assertEquals(Main.DEALER_HEALTH, dealer.getHealth());

        dealer.shoot(dealer, gun);
        dealer.shoot(dealer, gun);

        dealer.reset();
        assertEquals(Main.DEALER_HEALTH, dealer.getHealth());
    }

    @Test
    public void TakeActionTest() {
        HackerDealer dealer = HackerDealer.newHackerDealer();
        HackerDealer opponent = HackerDealer.newHackerDealer();
        Gun gun = Gun.customGun(new int[] {1, 1, 0, 1, 0, 0, 0, 0});
        dealer.setInventory(new String[] {"BEER", "HANDCUFF", "CIGARETTE", "SAW", "MAGNIFYING GLASS"});

        // Expected actions taken:
        // 1. Handcuff opponent
        // 2. Saw off gun
        // 3. Magnifying glass used
        // 4. dealer shoots opponent for 2 dmg
        dealer.takeAction(gun, opponent);
        assertEquals("BEER", dealer.inventory.items[0]);
        assertEquals(Inventory.USED, dealer.inventory.items[1]);
        assertEquals("CIGARETTE", dealer.inventory.items[2]);
        assertEquals(Inventory.USED, dealer.inventory.items[3]);
        assertEquals(Inventory.USED, dealer.inventory.items[4]);
        // Expected results:
        // 1. opponent loses 2 health
        // 2. opponent breaks free of handcuffs
        assertEquals(Main.DEALER_HEALTH, dealer.getHealth());
        assertEquals(Main.DEALER_HEALTH - 2, opponent.getHealth());
        assertTrue(opponent.canMove());

        dealer.setInventory(new String[] {"CIGARETTE", "BEER"});
        dealer.shoot(dealer, gun); // dealer shoots himself with live round to lose health
        // Expected actions:
        // 1. dealer heals up with CIGARETTE
        // 2. dealer uses BEER to eject blank round
        // 3. dealer shoots opponent for 1 dmg
        dealer.takeAction(gun, opponent);
        assertEquals(Inventory.USED, dealer.inventory.items[0]);
        assertEquals(Inventory.USED, dealer.inventory.items[1]);
        // Expected results:
        // 1. opponent loses 1 health
        assertEquals(Main.DEALER_HEALTH, dealer.getHealth());
        assertEquals(Main.DEALER_HEALTH - 3, opponent.getHealth());
        assertTrue(opponent.canMove());

        dealer.setInventory(new String[] {"HANDCUFF"});
        // Expected actions:
        // 1. dealer handcuffs opponent
        // 2. dealer shoots himself
        dealer.takeAction(gun, opponent);
        assertEquals(Inventory.USED, dealer.inventory.items[0]);
        // Expected results:
        // 1. opponent cannot move due to the handcuffs
        // 2. dealer shoots himself with a blank, so nobody loses health
        assertEquals(Main.DEALER_HEALTH, dealer.getHealth());
        assertEquals(Main.DEALER_HEALTH - 3, opponent.getHealth());
        assertFalse(opponent.canMove());
    }

    @Test
    public void HandcuffTest() {
        HackerDealer dealer = HackerDealer.newHackerDealer();
        assertTrue(dealer.canMove());
        dealer.handcuff();
        assertFalse(dealer.canMove());
    }

    @Test
    public void MovementControlTest() {
        HackerDealer dealer = HackerDealer.newHackerDealer();
        assertTrue(dealer.hasAnotherTurn());
        Gun gun = Gun.customGun(new int[] {1});
        HackerDealer otherDealer = HackerDealer.newHackerDealer();

        dealer.takeAction(gun, otherDealer);
        assertFalse(dealer.hasAnotherTurn);
    }
}
