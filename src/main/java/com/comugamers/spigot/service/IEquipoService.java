package com.comugamers.spigot.service;

import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.service.impl.EquipoServiceImpl;
import com.google.inject.ImplementedBy;
import org.bukkit.entity.Player;
import java.util.UUID;

@ImplementedBy(EquipoServiceImpl.class)
public interface IEquipoService {
        boolean createTeam(String id, String displayName, Player player);
        void addMember();
        void leaveTeam();

        TeamDataEntity getTeamByLeader(UUID leader);
        void setBastionCorner(UUID leader, int x, int z, boolean first);
        void removeBastion(UUID leader);
        int[] getBastion(String teamId);
}
