package com.comugamers.spigot.service.impl;

import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.repository.IEquipoRepository;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.UUID;

import com.comugamers.quanta.annotations.Service;
import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;

import java.util.Map;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

@Service
public class EquipoServiceImpl implements IEquipoService{

        @Autowired
        private Plugin plugin;

        @Autowired
        private IEquipoRepository repository;

    private final Set<String> restrictedTeams = new HashSet<>();
    // Invitaciones pendientes: jugador -> id del equipo
    private final Map<UUID, String> pendingInvites = new HashMap<>();
    private String attackTarget;
    private BossBar attackBossBar;
    private BukkitTask attackTask;
    private long attackStart;
    private UUID attackLeader;

	@Override
	public boolean createTeam(String id, String displayName, Player player) {

		if (repository.existsById(id)) {
			return false;
		}

        if (repository.existsByDisplayName(displayName)) {
			return false;
        }

		TeamDataEntity entity = new TeamDataEntity();
		entity.setId(id);
		entity.setDisplayName(displayName);
		entity.setLeader(player.getUniqueId());
                repository.save(entity);

                return true;
    }

    @Override
    public boolean inviteMember(Player leader, Player target) {
        TeamDataEntity team = repository.findByLeader(leader.getUniqueId());
        if (team == null) {
            return false;
        }
        if (getTeamByMember(target.getUniqueId()) != null) {
            return false;
        }
        int currentSize = team.getPlayers() == null ? 1 : team.getPlayers().size() + 1;
        if (currentSize >= 4) {
            return false;
        }
        pendingInvites.put(target.getUniqueId(), team.getId());
        target.sendMessage("Has sido invitado al equipo " + team.getDisplayName() + ". Usa /equipo aceptar o /equipo rechazar.");
        return true;
    }

    @Override
    public boolean acceptInvite(Player player) {
        String teamId = pendingInvites.remove(player.getUniqueId());
        if (teamId == null) {
            return false;
        }
        TeamDataEntity team = getTeamById(teamId);
        if (team == null) {
            return false;
        }
        if (team.getPlayers() == null) {
            team.setPlayers(new ArrayList<>());
        }
        int currentSize = team.getPlayers().size() + 1;
        if (currentSize >= 4) {
            return false;
        }
        team.getPlayers().add(player.getUniqueId());
        repository.save(team);
        player.sendMessage("Te has unido al equipo " + team.getDisplayName());
        return true;
    }

    @Override
    public boolean rejectInvite(Player player) {
        return pendingInvites.remove(player.getUniqueId()) != null;
    }

    @Override
    public boolean kickMember(Player leader, Player target) {
        TeamDataEntity team = repository.findByLeader(leader.getUniqueId());
        if (team == null || team.getPlayers() == null) {
            return false;
        }
        boolean removed = team.getPlayers().remove(target.getUniqueId());
        if (removed) {
            repository.save(team);
            target.sendMessage("Has sido expulsado del equipo " + team.getDisplayName());
        }
        return removed;
    }

    @Override
    public void leaveTeam(Player player) {
        TeamDataEntity team = getTeamByMember(player.getUniqueId());
        if (team == null) {
            return;
        }
        if (player.getUniqueId().equals(team.getLeader())) {
            return; // El líder no puede abandonar su propio equipo
        }
        if (team.getPlayers() != null) {
            team.getPlayers().remove(player.getUniqueId());
            repository.save(team);
        }
    }


    @Override
    public TeamDataEntity getTeamByLeader(UUID leader) {
        return repository.findByLeader(leader);
    }

    @Override
    public TeamDataEntity getTeamByMember(UUID playerId) {
        for (TeamDataEntity team : repository.findAll()) {
            if (playerId.equals(team.getLeader())) {
                return team;
            }
            if (team.getPlayers() != null && team.getPlayers().contains(playerId)) {
                return team;
            }
        }
        return null;
    }

    @Override
    public TeamDataEntity getTeamById(String teamId) {
        Optional<TeamDataEntity> opt = repository.findById(teamId);
        return opt.orElse(null);
    }

    @Override
    public void setBastionCorner(UUID leader, int x, int z, boolean first) {
        TeamDataEntity team = repository.findByLeader(leader);
        if (team == null) {
            return;
        }
        if (first) {
            team.setBastion1X(x);
            team.setBastion1Z(z);
        } else {
            team.setBastion2X(x);
            team.setBastion2Z(z);
        }
        repository.save(team);
    }

    @Override
    public void removeBastion(UUID leader) {
        TeamDataEntity team = repository.findByLeader(leader);
        if (team == null) {
            return;
        }
        team.setBastion1X(null);
        team.setBastion1Z(null);
        team.setBastion2X(null);
        team.setBastion2Z(null);
        repository.save(team);
    }

    @Override
    public int[] getBastion(String teamId) {
        Optional<TeamDataEntity> opt = repository.findById(teamId);
        if (opt.isEmpty()) {
            return null;
        }
        TeamDataEntity team = opt.get();
        if (team.getBastion1X() == null || team.getBastion1Z() == null ||
            team.getBastion2X() == null || team.getBastion2Z() == null) {
            return null;
        }
        return new int[]{team.getBastion1X(), team.getBastion1Z(), team.getBastion2X(), team.getBastion2Z()};
    }

    @Override
    public void setAttackZone(String teamId, int x, int y, int z) {
        Optional<TeamDataEntity> opt = repository.findById(teamId);
        if (opt.isEmpty()) {
            return;
        }
        TeamDataEntity team = opt.get();
        team.setAttackX(x);
        team.setAttackY(y);
        team.setAttackZ(z);
        repository.save(team);
    }

    @Override
    public void setAttackTarget(String teamId) {
        this.attackTarget = teamId;
    }

    @Override
    public String getAttackTarget() {
        return attackTarget;
    }

    @Override
    public void startAttackPhase(String teamId) {
        Optional<TeamDataEntity> opt = repository.findById(teamId);
        if (opt.isEmpty()) {
            return;
        }
        TeamDataEntity team = opt.get();
        if (team.getAttackX() == null || team.getAttackY() == null || team.getAttackZ() == null) {
            return;
        }

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.isOp()) {
                continue;
            }

            TeamDataEntity playerTeam = getTeamByMember(player.getUniqueId());
            if (playerTeam != null && playerTeam.getId().equals(teamId)) {
                int[] bastion = getBastion(teamId);
                if (bastion == null) {
                    continue;
                }
                int minX = Math.min(bastion[0], bastion[2]);
                int maxX = Math.max(bastion[0], bastion[2]);
                int minZ = Math.min(bastion[1], bastion[3]);
                int maxZ = Math.max(bastion[1], bastion[3]);
                Location safe = findSafeLocation(player.getWorld(), minX, maxX, minZ, maxZ);
                player.teleport(safe);
            } else {
                int y = player.getWorld().getHighestBlockYAt(team.getAttackX(), team.getAttackZ()) + 1;
                player.teleport(new Location(player.getWorld(), team.getAttackX() + 0.5, y, team.getAttackZ() + 0.5));
            }
        }

        startAttackTimer(team);
    }

    private void startAttackTimer(TeamDataEntity team) {
        attackLeader = team.getLeader();
        attackStart = System.currentTimeMillis();
        attackBossBar = Bukkit.createBossBar("Tiempo: 0s", BarColor.RED, BarStyle.SOLID);
        attackBossBar.setVisible(true);
        for (Player p : Bukkit.getOnlinePlayers()) {
            attackBossBar.addPlayer(p);
        }
        attackTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long seconds = (System.currentTimeMillis() - attackStart) / 1000;
            attackBossBar.setTitle("Tiempo: " + seconds + "s");
        }, 0L, 20L);
    }

    private Location findSafeLocation(World world, int minX, int maxX, int minZ, int maxZ) {
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                int y = world.getHighestBlockYAt(x, z) + 1;
                if (y >= world.getMaxHeight() - 1) {
                    continue;
                }
                if (world.getBlockAt(x, y, z).getType().isAir() &&
                    world.getBlockAt(x, y + 1, z).getType().isAir()) {
                    return new Location(world, x + 0.5, y, z + 0.5);
                }
            }
        }
        int centerX = (minX + maxX) / 2;
        int centerZ = (minZ + maxZ) / 2;
        int centerY = world.getHighestBlockYAt(centerX, centerZ) + 1;
        return new Location(world, centerX + 0.5, centerY, centerZ + 0.5);
    }

    @Override
    public void stopAttackTimer() {
        if (attackTask != null) {
            attackTask.cancel();
            attackTask = null;
        }
        if (attackBossBar != null) {
            attackBossBar.removeAll();
            attackBossBar.setVisible(false);
            attackBossBar = null;
        }
        attackLeader = null;
    }

    @Override
    public boolean isAttackLeader(UUID uuid) {
        return attackLeader != null && attackLeader.equals(uuid);
    }

    @Override
    public void setTeamRestricted(String teamId, boolean restricted) {
        if (restricted) {
            restrictedTeams.add(teamId);
        } else {
            restrictedTeams.remove(teamId);
        }
    }

    @Override
    public boolean isTeamRestricted(String teamId) {
        return restrictedTeams.contains(teamId);
    }

}
