package com.comugamers.spigot.command;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.service.IEquipoService;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.entity.Player;

@Command("eliminarbastion")
public class EliminarBastionCommand extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(Player player) {
        TeamDataEntity team = equipoService.getTeamByLeader(player.getUniqueId());
        if (team == null || !player.getUniqueId().equals(team.getLeader())) {
            player.sendMessage("No eres lider de ning\u00fan equipo.");
            return;
        }
        equipoService.removeBastion(player.getUniqueId());
        player.sendMessage("Bastion eliminado.");
    }
}
