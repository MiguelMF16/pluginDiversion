package gg.lajaulavs.bastion;

import com.github.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TerritoryManagerTest {
    private BastionSolitarioPlugin plugin;

    @BeforeEach
    public void setup() {
        MockBukkit.mock();
        plugin = MockBukkit.load(BastionSolitarioPlugin.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testDefaultGeneration() {
        TerritoryManager manager = plugin.getTerritoryManager();
        Assertions.assertEquals(20, manager.getTerritories().size());
    }

    @Test
    public void testSetCorners() {
        TerritoryManager manager = plugin.getTerritoryManager();
        Territory t = manager.getTerritory("A1");
        Location loc1 = new Location(plugin.getServer().getWorlds().get(0), 10, 5, 10);
        Location loc2 = new Location(plugin.getServer().getWorlds().get(0), 20, 5, 20);
        manager.setCorner1("A1", loc1);
        manager.setCorner2("A1", loc2);
        Assertions.assertEquals(loc1, t.getCorner1());
        Assertions.assertEquals(loc2, t.getCorner2());
    }
}
