package gg.lajaulavs.bastion;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Maneja el ciclo de rondas y teleporta a los jugadores a sus territorios.
 */
public class RoundManager {
    private final BastionSolitarioPlugin plugin;
    private final TerritoryManager territoryManager;
    private final Map<Player, String> assignments = new HashMap<>();
    private BukkitTask roundTask;

    public RoundManager(BastionSolitarioPlugin plugin, TerritoryManager territoryManager) {
        this.plugin = plugin;
        this.territoryManager = territoryManager;
    }

    public void assign(Player player, String territoryId) {
        assignments.put(player, territoryId);
    }

    public void startRound() {
        // Teleporta a cada jugador asignado
        for (Map.Entry<Player, String> entry : assignments.entrySet()) {
            Player player = entry.getKey();
            Territory t = territoryManager.getTerritory(entry.getValue());
            if (t != null) {
                player.teleport(t.getCenter());
            }
        }

        roundTask = new BukkitRunnable() {
            int time = 180;
            @Override
            public void run() {
                if (time <= 0) {
                    endRound();
                    cancel();
                    return;
                }
                time--;
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    private void endRound() {
        // Resumen simple
        Bukkit.broadcastMessage("La ronda ha terminado.");
        assignments.clear();
    }

    /** Detiene la ronda otorgando la victoria al equipo indicado */
    public void forceWin(String team) {
        if (roundTask != null) {
            roundTask.cancel();
            roundTask = null;
        }
        Bukkit.broadcastMessage("La ronda ha sido forzada. Ganador: " + team);
        endRound();
    }

    /** Cancela la ronda sin declarar ganador */
    public void cancelRound() {
        if (roundTask != null) {
            roundTask.cancel();
            roundTask = null;
        }
        Bukkit.broadcastMessage("La ronda ha sido cancelada.");
        assignments.clear();
    }
}
