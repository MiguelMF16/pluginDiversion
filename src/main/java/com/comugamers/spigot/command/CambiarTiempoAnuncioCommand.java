package com.comugamers.spigot.command;

import org.bukkit.command.CommandSender;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.anuncio.IAnuncioService;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Arg;

@Command("cambiartiempoanuncio")
public class CambiarTiempoAnuncioCommand extends BaseCommand {

    @Autowired
    private IAnuncioService anuncioService;

    /**
     * Ejecuta el comando para cambiar el intervalo entre anuncios.
     *
     * @param sender  el remitente del comando
     * @param minutos intervalo en minutos
     */
    @Default
    public void ejecutar(CommandSender sender, @Arg("minutos") int minutos) {
        this.anuncioService.cambiarIntervalo(minutos);
        sender.sendMessage("Intervalo de anuncios actualizado a " + minutos + " minutos.");
    }
}
