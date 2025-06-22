package com.comugamers.spigot.command;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.command.CommandSender;

@Command("bloquearbastion")
public class BloquearBastionCommand extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(CommandSender sender, String teamId) {
        equipoService.setTeamRestricted(teamId, true);
        sender.sendMessage("Los jugadores del equipo " + teamId + " no pueden salir de su bastion.");
    }
}
