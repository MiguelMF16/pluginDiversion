package com.comugamers.spigot.service.anuncio.impl;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.comugamers.quanta.annotations.Service;
import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.service.anuncio.IAnuncioService;

@Service
public class AnuncioServiceImpl implements IAnuncioService {

    @Autowired
    private Plugin plugin;

    private final List<String> mensajes = Arrays.asList(
            "&6&lBienvenidos a &c&lDefiende el basti\u00f3n&6&l, &fno olvides seguir el directo en &d&ntwitch.tv/lajaulavs",
            "&eRecuerda que puedes conseguir boletos usando &a!chicklin &een el canal de &bLaJaulaVS&e, canjea &6Soy un real &ey ve a la web para obtener tu primera &a!chicklin",
            "&bSigue el evento en &d&ntwitch.tv/lajaulavs &by te ver\u00e1s como &6&lprotagonista &bparticipando"
    );

    private int intervaloMinutos = 20;
    private BukkitTask tarea;
    private int indice = 0;

    @Override
    public void iniciar() {
        programarTarea();
    }

    @Override
    public void cambiarIntervalo(int minutos) {
        if (minutos <= 0) {
            minutos = 1;
        }
        this.intervaloMinutos = minutos;
        if (this.tarea != null) {
            this.tarea.cancel();
        }
        programarTarea();
    }

    /**
     * Programa la tarea repetitiva que env\u00eda los mensajes.
     */
    private void programarTarea() {
        long ticks = this.intervaloMinutos * 60L * 20L;
        this.tarea = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
            String texto = mensajes.get(indice);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', texto));
            indice = (indice + 1) % mensajes.size();
        }, ticks, ticks);
    }
}
