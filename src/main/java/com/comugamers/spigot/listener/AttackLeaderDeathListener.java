package com.comugamers.spigot.listener;

import com.comugamers.spigot.service.IEquipoService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;

public class AttackLeaderDeathListener implements Listener {

    private final IEquipoService equipoService;

    public AttackLeaderDeathListener(IEquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (equipoService.isAttackLeader(player.getUniqueId())) {
            equipoService.stopAttackTimer();
        }
    }
}
