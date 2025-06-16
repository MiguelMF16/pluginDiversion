package com.comugamers.spigot.team;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Implementacion en memoria del servicio de equipos.
 */
public class TeamServiceImpl implements ITeamService {

    private final Map<String, Team> teams = new HashMap<>();
    private final Map<UUID, Team> playerTeams = new HashMap<>();

    // Datos temporales para la fase de defensores
    private final Map<UUID, Team> previous = new HashMap<>();
    private final Set<UUID> defenders = new HashSet<>();

    @Override
    public boolean createTeam(Player leader, String name) {
        if (playerTeams.containsKey(leader.getUniqueId()) || teams.containsKey(name)) {
            return false;
        }
        Team team = new Team(name, leader.getUniqueId());
        teams.put(name, team);
        playerTeams.put(leader.getUniqueId(), team);
        return true;
    }

    @Override
    public boolean joinTeam(Player player, String teamName) {
        Team team = teams.get(teamName);
        if (team == null || team.getMembers().size() >= 4) {
            return false;
        }
        if (playerTeams.containsKey(player.getUniqueId())) {
            return false;
        }
        team.getMembers().add(player.getUniqueId());
        playerTeams.put(player.getUniqueId(), team);
        return true;
    }

    @Override
    public void leaveTeam(Player player) {
        Team team = playerTeams.remove(player.getUniqueId());
        if (team != null) {
            team.getMembers().remove(player.getUniqueId());
            if (team.getMembers().isEmpty()) {
                teams.remove(team.getName());
            }
        }
    }

    @Override
    public Team getTeam(Player player) {
        return playerTeams.get(player.getUniqueId());
    }

    @Override
    public void startDefenderPhase(String excludedTeam) {
        defenders.clear();
        previous.clear();
        Team exempt = teams.get(excludedTeam);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (exempt != null && exempt.getMembers().contains(player.getUniqueId())) {
                continue;
            }
            Team current = playerTeams.get(player.getUniqueId());
            previous.put(player.getUniqueId(), current);
            if (current != null) {
                current.getMembers().remove(player.getUniqueId());
                if (current.getMembers().isEmpty()) {
                    teams.remove(current.getName());
                }
            }
            playerTeams.remove(player.getUniqueId());
            defenders.add(player.getUniqueId());
        }
    }

    @Override
    public void endDefenderPhase() {
        for (UUID id : defenders) {
            Team prev = previous.get(id);
            if (prev != null) {
                playerTeams.put(id, prev);
                prev.getMembers().add(id);
            } else {
                playerTeams.remove(id);
            }
        }
        defenders.clear();
        previous.clear();
    }
}
