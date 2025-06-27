package gg.lajaulavs.bastion;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BastionSolitarioPlugin extends JavaPlugin {
    private TerritoryManager territoryManager;
    private RoundManager roundManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        territoryManager = new TerritoryManager(this);
        roundManager = new RoundManager(this, territoryManager);
        getCommand("bs").setExecutor(new BastionCommand(this));
    }

    @Override
    public void onDisable() {
        territoryManager.saveTerritories();
    }

    public TerritoryManager getTerritoryManager() {
        return territoryManager;
    }

    public RoundManager getRoundManager() {
        return roundManager;
    }
}
