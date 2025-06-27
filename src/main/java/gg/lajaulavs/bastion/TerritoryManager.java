package gg.lajaulavs.bastion;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Gestiona los territorios del evento Bastión Solitario.
 */
public class TerritoryManager {
    private final BastionSolitarioPlugin plugin;
    private final Map<String, Territory> territories = new LinkedHashMap<>();
    private final File file;

    public TerritoryManager(BastionSolitarioPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "territories.yml");
        loadTerritories();
    }

    private void loadTerritories() {
        if (!file.exists()) {
            createDefaultTerritories();
            saveTerritories();
            return;
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        World world = Bukkit.getWorlds().get(0);
        for (String key : cfg.getKeys(false)) {
            Location c1 = cfg.getLocation(key + ".c1", new Location(world, 0, 0, 0));
            Location c2 = cfg.getLocation(key + ".c2", new Location(world, 0, 0, 0));
            Territory t = new Territory(key, c1, c2);
            t.setOwnerTeam(cfg.getString(key + ".owner"));
            t.setPoints(cfg.getInt(key + ".points"));
            t.setLastClaimedTick(cfg.getLong(key + ".lastClaim"));
            territories.put(key, t);
        }
    }

    private void createDefaultTerritories() {
        World world = Bukkit.getWorlds().get(0);
        Location spawn = world.getSpawnLocation();
        int index = 0;
        char[] cols = {'A','B','C','D','E'};
        for (int z = 0; z < 4; z++) {
            for (int x = 0; x < 5; x++) {
                String id = cols[x] + String.valueOf(z + 1);
                int cx = spawn.getBlockX() + (x - 2) * 64;
                int cz = spawn.getBlockZ() + (z - 1) * 64;
                Location c1 = new Location(world, cx, 0, cz);
                Location c2 = new Location(world, cx + 63, world.getMaxHeight(), cz + 63);
                territories.put(id, new Territory(id, c1, c2));
                index++;
            }
        }
    }

    public Map<String, Territory> getTerritories() { return territories; }

    public Territory getTerritory(String id) { return territories.get(id); }

    public void saveTerritories() {
        FileConfiguration cfg = new YamlConfiguration();
        for (Map.Entry<String, Territory> entry : territories.entrySet()) {
            String key = entry.getKey();
            Territory t = entry.getValue();
            cfg.set(key + ".c1", t.getCorner1());
            cfg.set(key + ".c2", t.getCorner2());
            cfg.set(key + ".owner", t.getOwnerTeam());
            cfg.set(key + ".points", t.getPoints());
            cfg.set(key + ".lastClaim", t.getLastClaimedTick());
        }
        try {
            cfg.save(file);
        } catch (Exception e) {
            plugin.getLogger().severe("No se pudo guardar territories.yml" );
        }
    }
}
