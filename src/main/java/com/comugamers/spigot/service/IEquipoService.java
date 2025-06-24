package com.comugamers.spigot.service;

import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.service.impl.EquipoServiceImpl;
import com.google.inject.ImplementedBy;
import org.bukkit.entity.Player;
import java.util.UUID;

@ImplementedBy(EquipoServiceImpl.class)
public interface IEquipoService {
        boolean createTeam(String id, String displayName, Player player);
        boolean inviteMember(Player leader, Player target);
        boolean acceptInvite(Player player);
        boolean rejectInvite(Player player);
        boolean kickMember(Player leader, Player target);
        void leaveTeam(Player player);

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

    /**
     * Agrega un jugador al contador de ataque si hay uno activo.
     *
     * @param player Jugador a agregar.
     */
    void addPlayerToAttackBar(Player player);

    /**
     * Inicializa el scoreboard y los equipos de PvP.
     */
    void initScoreboard();

    /**
     * Reasigna a todos los jugadores online a los equipos del scoreboard
     * según el equipo que está defendiendo actualmente.
     */
    void refreshTeams();

    /**
     * Actualiza la pertenencia de un jugador al unirse al servidor.
     *
     * @param player Jugador que se une.
     */
    void handlePlayerJoin(Player player);

    /**
     * Elimina a un jugador de los equipos al desconectarse.
     *
     * @param player Jugador que se va.
     */
    void handlePlayerQuit(Player player);
}
