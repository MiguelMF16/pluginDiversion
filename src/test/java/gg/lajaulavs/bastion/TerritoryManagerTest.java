package gg.lajaulavs.bastion;

import com.github.seeseemelk.mockbukkit.MockBukkit;
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
}
