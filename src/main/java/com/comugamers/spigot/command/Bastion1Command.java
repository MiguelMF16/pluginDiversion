package com.comugamers.spigot.command;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.entity.Player;
import org.bukkit.Location;

@Command("bastion1")
public class Bastion1Command extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(Player player) {
        Location loc = player.getLocation();
        equipoService.setBastionCorner(player.getUniqueId(), loc.getBlockX(), loc.getBlockZ(), true);
        player.sendMessage("Primera esquina del bastion establecida.");
    }
}
