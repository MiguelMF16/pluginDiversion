package com.comugamers.spigot.listener;

import com.comugamers.spigot.service.IEquipoService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;

public class AttackJoinListener implements Listener {

    private final IEquipoService equipoService;

    public AttackJoinListener(IEquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        equipoService.handlePlayerJoin(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        equipoService.handlePlayerQuit(player);
    }
}
