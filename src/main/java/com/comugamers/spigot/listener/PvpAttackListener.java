package com.comugamers.spigot.listener;

import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.service.IEquipoService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;

public class PvpAttackListener implements Listener {

    private final IEquipoService equipoService;

    public PvpAttackListener(IEquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        String target = equipoService.getAttackTarget();
        if (target == null) {
            return;
        }

        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();
        TeamDataEntity attackerTeam = equipoService.getTeamByMember(attacker.getUniqueId());
        TeamDataEntity victimTeam = equipoService.getTeamByMember(victim.getUniqueId());
        String attackerId = attackerTeam != null ? attackerTeam.getId() : null;
        String victimId = victimTeam != null ? victimTeam.getId() : null;

        if (target.equals(attackerId) || target.equals(victimId)) {
            return; // allow attacks involving the target team
        }

        event.setCancelled(true);
    }
}
