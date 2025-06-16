package com.comugamers.spigot.team;

import org.bukkit.entity.Player;

/**
 * Funciones de gestion de equipos.
 */
public interface ITeamService {

    /**
     * Crea un equipo con el jugador como lider.
     */
    boolean createTeam(Player leader, String name);

    /**
     * Agrega a un jugador a un equipo existente.
     */
    boolean joinTeam(Player player, String teamName);

    /**
     * Elimina a un jugador de su equipo actual.
     */
    void leaveTeam(Player player);

    /**
     * Obtiene el equipo de un jugador.
     */
    Team getTeam(Player player);

    /**
     * Inicia la fase de defensores excluyendo al equipo indicado.
     */
    void startDefenderPhase(String excludedTeam);

    /**
     * Finaliza la fase de defensores y restaura los equipos previos.
     */
    void endDefenderPhase();
}
