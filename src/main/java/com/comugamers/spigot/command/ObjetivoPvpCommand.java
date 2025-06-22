package com.comugamers.spigot.command;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.service.IEquipoService;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.command.CommandSender;

@Command("objetivopvp")
public class ObjetivoPvpCommand extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(CommandSender sender, String teamId) {
        TeamDataEntity team = equipoService.getTeamById(teamId);
        if (team == null) {
            sender.sendMessage("El equipo especificado no existe.");
            return;
        }
        equipoService.setAttackTarget(teamId);
        sender.sendMessage("PVP habilitado contra el equipo " + teamId + ".");
    }
}
