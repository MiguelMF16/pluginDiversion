package com.comugamers.spigot.command;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.entity.Player;
import org.bukkit.Location;

@Command("bastion2")
public class Bastion2Command extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(Player player) {
        if (equipoService.getTeamByMember(player.getUniqueId()) == null) {
            player.sendMessage("Debes pertenecer a un equipo para usar este comando.");
            return;
        }
        Location loc = player.getLocation();
        equipoService.setBastionCorner(player.getUniqueId(), loc.getBlockX(), loc.getBlockZ(), false);
        player.sendMessage("Segunda esquina del bastion establecida.");
    }
}
