package com.comugamers.spigot.command;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.command.CommandSender;

@Command("getbastion")
public class GetBastionCommand extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(CommandSender sender, String teamId) {
        int[] coords = equipoService.getBastion(teamId);
        if (coords == null) {
            sender.sendMessage("Ese equipo no tiene bastion definido.");
            return;
        }
        sender.sendMessage("Bastion de " + teamId + ": (" + coords[0] + ", " + coords[1] + ") -> (" + coords[2] + ", " + coords[3] + ")");
    }
}
