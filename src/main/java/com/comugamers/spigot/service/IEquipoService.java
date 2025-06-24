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
    TeamDataEntity getTeamByMember(UUID playerId);
    TeamDataEntity getTeamById(String teamId);
    void setBastionCorner(UUID leader, int x, int z, boolean first);
    void removeBastion(UUID leader);
    int[] getBastion(String teamId);
    void setAttackZone(String teamId, int x, int y, int z);
    void startAttackPhase(String teamId);
    void stopAttackTimer();
    boolean isAttackLeader(UUID uuid);
    void setAttackTarget(String teamId);
    String getAttackTarget();
    void setTeamRestricted(String teamId, boolean restricted);
    boolean isTeamRestricted(String teamId);
}
