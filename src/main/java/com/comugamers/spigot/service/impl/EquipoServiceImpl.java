package com.comugamers.spigot.service.impl;

import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.repository.IEquipoRepository;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comugamers.quanta.annotations.Service;
import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;

import java.util.Map;

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

}
