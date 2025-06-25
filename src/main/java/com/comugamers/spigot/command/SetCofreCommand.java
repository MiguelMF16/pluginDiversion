package com.comugamers.spigot.command;

import com.comugamers.quanta.annotations.alias.Autowired;
import com.comugamers.spigot.entity.TeamDataEntity;
import com.comugamers.spigot.service.IEquipoService;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Comando para asignar un cofre al equipo.
 * Solo puede usarlo el líder del equipo o un usuario con permisos de op.
 */
@Command("setcofre")
public class SetCofreCommand extends BaseCommand {

    @Autowired
    private IEquipoService equipoService;

    @Default
    public void execute(Player player, String teamId) {
        TeamDataEntity team = equipoService.getTeamById(teamId);
        if (team == null) {
            player.sendMessage("El equipo especificado no existe.");
            return;
        }
        if (!player.isOp() && !team.getLeader().equals(player.getUniqueId())) {
            player.sendMessage("No eres el líder de ese equipo.");
            return;
        }

        Block block = player.getTargetBlockExact(5);
        if (block == null || block.getType() != Material.CHEST) {
            player.sendMessage("Debes mirar un cofre para seleccionarlo.");
            return;
        }

        Chest chest = (Chest) block.getState();
        ItemStack[] contents = chest.getBlockInventory().getContents();
        equipoService.setTeamChest(team.getId(), contents);
        chest.getBlockInventory().clear();
        block.setType(Material.AIR);
        player.sendMessage("Cofre guardado para el equipo " + team.getId() + ".");
    }
}
