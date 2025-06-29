package gg.lajaulavs.bastion;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import gg.lajaulavs.bastion.command.BastionCommand;

public class BastionSolitarioPlugin extends JavaPlugin {
    private TerritoryManager territoryManager;
    private RoundManager roundManager;
    private TeamManager teamManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        territoryManager = new TerritoryManager(this);
        teamManager = new TeamManager();
        roundManager = new RoundManager(this, territoryManager, teamManager);
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

    public TeamManager getTeamManager() {
        return teamManager;
    }
}
