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

@Service
public class EquipoServiceImpl implements IEquipoService{

	@Autowired
	private Plugin plugin;

	@Autowired
	private IEquipoRepository repository;

	@Override
	public boolean createTeam(String id, String displayName, Player player) {

		if (repository.existsById(id)) {
			return false;
		}

        if (repository.existByDisplayName(displayName)) {
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

}
