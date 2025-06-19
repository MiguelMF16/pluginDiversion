package com.comugamers.spigot.service;

import com.comugamers.spigot.service.impl.EquipoServiceImpl;
import com.google.inject.ImplementedBy;
import org.bukkit.entity.Player;

@ImplementedBy(EquipoServiceImpl.class)
public interface IEquipoService {
	boolean createTeam(String id, String displayName, Player player);
	void addMember();
	void leaveTeam();
}
