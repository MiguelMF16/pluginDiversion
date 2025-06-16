package com.comugamers.spigot.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.comugamers.spigot.team.ITeamService;

/**
 * Comando principal para gestion de equipos.
 */
public class TeamCommand implements CommandExecutor {

    private final ITeamService service;

    public TeamCommand(ITeamService service) {
        this.service = service;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Solo jugadores.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            sender.sendMessage("Uso: /team <create|join|leave>" );
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "create":
                if (args.length < 2) {
                    sender.sendMessage("Uso: /team create <nombre>");
                    return true;
                }
                if (service.createTeam(player, args[1])) {
                    sender.sendMessage("Equipo creado");
                } else {
                    sender.sendMessage("No se pudo crear el equipo");
                }
                break;
            case "join":
                if (args.length < 2) {
                    sender.sendMessage("Uso: /team join <nombre>");
                    return true;
                }
                if (service.joinTeam(player, args[1])) {
                    sender.sendMessage("Te uniste al equipo");
                } else {
                    sender.sendMessage("No se pudo unir al equipo");
                }
                break;
            case "leave":
                service.leaveTeam(player);
                sender.sendMessage("Abandonaste tu equipo");
                break;
            default:
                sender.sendMessage("Uso: /team <create|join|leave>");
                break;
        }
        return true;
    }
}
