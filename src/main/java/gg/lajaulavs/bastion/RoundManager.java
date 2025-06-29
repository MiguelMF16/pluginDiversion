package gg.lajaulavs.bastion;

import java.util.*;

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
    private final TeamManager teamManager;

    private final Map<Player, String> assignments = new HashMap<>();
    private final Map<String, String> teamAttackTarget = new HashMap<>();
    private final Set<UUID> attackers = new HashSet<>();
    private final Map<UUID, String> defenders = new HashMap<>();
    private BukkitTask roundTask;

    public RoundManager(BastionSolitarioPlugin plugin, TerritoryManager territoryManager, TeamManager teamManager) {
        this.plugin = plugin;
        this.territoryManager = territoryManager;
        this.teamManager = teamManager;
    }

    public void assign(Player player, String territoryId) {
        assignments.put(player, territoryId);
    }

    public void markAttack(Player leader, String territoryId) {
        if (!teamManager.isLeader(leader)) {
            leader.sendMessage("No eres líder de ningún equipo");
            return;
        }
        String team = teamManager.getTeam(leader);
        teamAttackTarget.put(team, territoryId);
        for (Player member : teamManager.getOnlineMembers(team)) {
            member.sendMessage("El líder " + leader.getName() + " marcó " + territoryId + " como objetivo de ataque");
        }
    }

    public void chooseAttack(Player player) {
        String team = teamManager.getTeam(player);
        if (team == null) {
            player.sendMessage("No tienes equipo asignado");
            return;
        }
        String target = teamAttackTarget.get(team);
        if (target == null) {
            player.sendMessage("Tu líder aún no ha marcado zona de ataque");
            return;
        }
        attackers.add(player.getUniqueId());
        defenders.remove(player.getUniqueId());
        // Asignar automáticamente la zona de ataque al jugador
        assign(player, target);
        for (Player member : teamManager.getOnlineMembers(team)) {
            member.sendMessage(player.getName() + " atacará " + target);
        }
    }

    public void chooseDefense(Player player, String territoryId) {
        String team = teamManager.getTeam(player);
        if (team == null) {
            player.sendMessage("No tienes equipo asignado");
            return;
        }
        Territory t = territoryManager.getTerritory(territoryId);
        if (t == null || t.getOwnerTeam() == null || !t.getOwnerTeam().equalsIgnoreCase(team)) {
            player.sendMessage("Esta zona no es de tu equipo");
            return;
        }
        defenders.put(player.getUniqueId(), territoryId);
        attackers.remove(player.getUniqueId());
        // Asignar directamente la zona elegida para defender
        assign(player, territoryId);
        for (Player member : teamManager.getOnlineMembers(team)) {
            member.sendMessage(player.getName() + " defenderá " + territoryId);
        }
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
        attackers.clear();
        defenders.clear();
        teamAttackTarget.clear();
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
        attackers.clear();
        defenders.clear();
        teamAttackTarget.clear();
    }
}
