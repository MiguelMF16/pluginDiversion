package com.comugamers.spigot.listener;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.service.IEquipoService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class BastionBoundaryListener implements Listener {

    @Autowired
    private IEquipoService equipoService;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        TeamDataEntity team = equipoService.getTeamByMember(player.getUniqueId());
        if (team == null) {
            return;
        }
        if (!equipoService.isTeamRestricted(team.getId())) {
            return;
        }
        int[] bastion = equipoService.getBastion(team.getId());
        if (bastion == null) {
            return;
        }
        int minX = Math.min(bastion[0], bastion[2]);
        int maxX = Math.max(bastion[0], bastion[2]);
        int minZ = Math.min(bastion[1], bastion[3]);
        int maxZ = Math.max(bastion[1], bastion[3]);

        Location to = event.getTo();
        if (to == null) {
            return;
        }

        double x = to.getX();
        double z = to.getZ();
        if (x < minX || x > maxX || z < minZ || z > maxZ) {
            event.setCancelled(true);
            player.sendMessage("No puedes salir de tu bastion.");
        }
    }
}
