package com.comugamers.spigot.command;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.service.IEquipoService;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.entity.Player;
import org.bukkit.Location;

@Command("setzonaataque")
public class SetZonaAtaqueCommand extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(Player player, String teamId) {
        TeamDataEntity team = equipoService.getTeamById(teamId);
        if (team == null) {
            player.sendMessage("El equipo especificado no existe.");
            return;
        }
        Location loc = player.getLocation();
        equipoService.setAttackZone(teamId, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        player.sendMessage("Zona de ataque establecida para el equipo " + teamId + ".");
    }
}
