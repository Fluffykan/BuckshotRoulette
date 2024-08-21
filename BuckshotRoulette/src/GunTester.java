import org.junit.Test;
import static org.junit.Assert.*;

public class GunTester {
    @Test
    public void ReloadTest() {
        Gun gun = Gun.newGun();
        assertEquals(3, gun.roundsLeft());

        gun.reload();
        assertEquals(5, gun.roundsLeft());

        gun.reload();
        assertEquals(5, gun.roundsLeft());

        gun.reload();
        assertEquals(7, gun.roundsLeft());

        gun.reload();
        assertEquals(7, gun.roundsLeft());
    }

    @Test
    public void FireTest() {
        Gun gun = Gun.newGun();

        while (!gun.isEmpty()) {
            int dmg = gun.fire();
            assertTrue(dmg == 1 || dmg == 0);
        }

    }

    @Test
    public void SawedOffTest() {
        Gun gun = Gun.customGun(new int[] {1, 1, 0, 0});

        gun.sawOff();
        assertEquals(2, gun.fire());
        assertEquals(1, gun.fire());

        gun.sawOff();
        assertEquals(0, gun.fire());
        assertEquals(0, gun.fire());
    }


}
