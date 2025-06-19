package com.comugamers.spigot.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.IEquipoService;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.entity.Player;

@Command("equipo")
public class EquipoCommand extends BaseCommand{

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(CommandSender sender, String subcommand, String[] arguments){

        Player player = (Player) sender;

        if(player.isEmpty()){
          sender.sendMessage("Este comando debe ser ejecutado desde el juego.");
          return;
        }

        if(subcommand.isEmpty()){
            sender.sendMessage("Debes especificar un subcomando.");
            return;
        }

        if(subcommand.equals("crear")){
            String id = arguments[1];
            String displayName = arguments[1];

            if(id.isEmpty() || displayName.isEmpty()){
                sender.sendMessage("Debes especificar un nombre para el equipo.");
                return;
            }

            if(equipoService.createTeam(id, displayName, player)){
                sender.sendMessage("Equipo creado correctamente ["+displayName+"]");
                return;
            }else{
                sender.sendMessage("Equipo ya existente");
                return;
            }
        }




    }

}