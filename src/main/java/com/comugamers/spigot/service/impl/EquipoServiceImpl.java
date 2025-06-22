package com.comugamers.spigot.service.impl;

import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.repository.IEquipoRepository;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.UUID;

import com.comugamers.quanta.annotations.Service;
import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;

import java.util.Map;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

@Service
public class EquipoServiceImpl implements IEquipoService{

        @Autowired
        private Plugin plugin;

        @Autowired
        private IEquipoRepository repository;

    private final Set<String> restrictedTeams = new HashSet<>();
    private String attackTarget;

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
	public void addMember() {

	}

    @Override
    public void leaveTeam() {

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
                int targetX = (minX + maxX) / 2;
                int targetZ = (minZ + maxZ) / 2;
                int targetY = player.getWorld().getHighestBlockYAt(targetX, targetZ) + 1;
                player.teleport(new org.bukkit.Location(player.getWorld(), targetX + 0.5, targetY, targetZ + 0.5));
            } else {
                int y = player.getWorld().getHighestBlockYAt(team.getAttackX(), team.getAttackZ()) + 1;
                player.teleport(new org.bukkit.Location(player.getWorld(), team.getAttackX() + 0.5, y, team.getAttackZ() + 0.5));
            }
        }
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
