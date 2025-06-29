package gg.lajaulavs.bastion;

import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Simple team management.
 */
public class TeamManager {
    private final Map<UUID, String> playerTeams = new HashMap<>();
    private final Map<String, UUID> teamLeaders = new HashMap<>();

    public void setPlayerTeam(Player player, String team) {
        playerTeams.put(player.getUniqueId(), team);
    }

    public String getTeam(Player player) {
        return playerTeams.get(player.getUniqueId());
    }

    public void setLeader(String team, Player player) {
        teamLeaders.put(team, player.getUniqueId());
        setPlayerTeam(player, team);
    }

    public boolean isLeader(Player player) {
        String team = getTeam(player);
        if (team == null) return false;
        UUID leaderId = teamLeaders.get(team);
        return leaderId != null && leaderId.equals(player.getUniqueId());
    }

    public Collection<Player> getOnlineMembers(String team) {
        List<Player> members = new ArrayList<>();
        for (Map.Entry<UUID, String> entry : playerTeams.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(team)) {
                Player p = Bukkit.getPlayer(entry.getKey());
                if (p != null && p.isOnline()) {
                    members.add(p);
                }
            }
        }
        return members;
    }
}
