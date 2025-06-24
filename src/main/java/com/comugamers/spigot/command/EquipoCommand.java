package com.comugamers.spigot.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.SubCommand;

@Command("equipo")
public class EquipoCommand extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void defaultCommand(CommandSender sender) {
        sender.sendMessage("Debes especificar un subcomando. Usa /equipo crear <id> <nombre_de_muestra>");
    }

    @SubCommand("crear")
    public void crear(CommandSender sender, String id, String displayName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando debe ser ejecutado desde el juego.");
            return;
        }
        Player player = (Player) sender;

        if (id == null || id.isEmpty() || displayName == null || displayName.isEmpty()) {
            sender.sendMessage("Debes especificar un nombre para el equipo.");
            return;
        }

        if (equipoService.createTeam(id, displayName, player)) {
            sender.sendMessage("Equipo creado correctamente [" + displayName + "]");
        } else {
            sender.sendMessage("Equipo ya existente");
        }
    }

    @SubCommand("invitar")
    public void invitar(CommandSender sender, Player objetivo) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando debe ser ejecutado desde el juego.");
            return;
        }
        Player jugador = (Player) sender;
        if (equipoService.inviteMember(jugador, objetivo)) {
            sender.sendMessage("Invitación enviada a " + objetivo.getName());
        } else {
            sender.sendMessage("No se pudo enviar la invitación.");
        }
    }

    @SubCommand("aceptar")
    public void aceptar(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando debe ser ejecutado desde el juego.");
            return;
        }
        Player jugador = (Player) sender;
        if (equipoService.acceptInvite(jugador)) {
            sender.sendMessage("Has aceptado la invitación.");
        } else {
            sender.sendMessage("No tienes invitaciones pendientes.");
        }
    }

    @SubCommand("rechazar")
    public void rechazar(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando debe ser ejecutado desde el juego.");
            return;
        }
        Player jugador = (Player) sender;
        if (equipoService.rejectInvite(jugador)) {
            sender.sendMessage("Has rechazado la invitación.");
        } else {
            sender.sendMessage("No tienes invitaciones pendientes.");
        }
    }

    @SubCommand("expulsar")
    public void expulsar(CommandSender sender, Player objetivo) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando debe ser ejecutado desde el juego.");
            return;
        }
        Player jugador = (Player) sender;
        if (equipoService.kickMember(jugador, objetivo)) {
            sender.sendMessage("Has expulsado a " + objetivo.getName());
        } else {
            sender.sendMessage("No se pudo expulsar al jugador.");
        }
    }
}
