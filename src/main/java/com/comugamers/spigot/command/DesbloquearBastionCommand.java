package com.comugamers.spigot.command;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.command.CommandSender;

@Command("desbloquearbastion")
public class DesbloquearBastionCommand extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(CommandSender sender, String teamId) {
        equipoService.setTeamRestricted(teamId, false);
        sender.sendMessage("Los jugadores del equipo " + teamId + " pueden salir de su bastion nuevamente.");
    }
}
