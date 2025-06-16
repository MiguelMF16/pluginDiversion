package com.comugamers.spigot.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comugamers.spigot.team.ITeamService;

/**
 * Comando para administrar la fase de defensores.
 */
public class DefendersCommand implements CommandExecutor {

    private final ITeamService service;
    private final Plugin plugin;

    public DefendersCommand(ITeamService service, Plugin plugin) {
        this.service = service;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("defenders.admin")) {
            sender.sendMessage("Sin permiso");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("Uso: /defenders <start|end> [equipo]");
            return true;
        }
        if (args[0].equalsIgnoreCase("start")) {
            if (args.length < 2) {
                sender.sendMessage("Uso: /defenders start <equipo>");
                return true;
            }
            service.startDefenderPhase(args[1]);
            plugin.getServer().broadcastMessage("Fase de defensores iniciada");
        } else if (args[0].equalsIgnoreCase("end")) {
            service.endDefenderPhase();
            plugin.getServer().broadcastMessage("Fase de defensores terminada");
        } else {
            sender.sendMessage("Uso: /defenders <start|end> [equipo]");
        }
        return true;
    }
}
