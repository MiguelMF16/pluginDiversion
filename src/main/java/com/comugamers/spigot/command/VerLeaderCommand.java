package com.comugamers.spigot.command;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.service.IEquipoService;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

@Command("verleader")
public class VerLeaderCommand extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(CommandSender sender, String teamId) {
        TeamDataEntity team = equipoService.getTeamById(teamId);
        if (team == null) {
            sender.sendMessage("El equipo especificado no existe.");
            return;
        }
        UUID leaderId = team.getLeader();
        String leaderName;
        OfflinePlayer offline = Bukkit.getOfflinePlayer(leaderId);
        if (offline != null && offline.getName() != null) {
            leaderName = offline.getName();
        } else {
            leaderName = leaderId.toString();
        }
        sender.sendMessage("El lider de " + teamId + " es " + leaderName + ".");
    }
}
