package gg.lajaulavs.bastion.command;

import gg.lajaulavs.bastion.BastionSolitarioPlugin;
import gg.lajaulavs.bastion.RoundManager;
import gg.lajaulavs.bastion.TerritoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Comando principal /bs
 */
public class BastionCommand implements CommandExecutor {
    private final BastionSolitarioPlugin plugin;

    public BastionCommand(BastionSolitarioPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;
        if (args[0].equalsIgnoreCase("asignar") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("Jugador no encontrado");
                return true;
            }
            plugin.getRoundManager().assign(target, args[2]);
            sender.sendMessage("Asignado " + target.getName() + " a " + args[2]);
            return true;
        }
        if (args[0].equalsIgnoreCase("startRound")) {
            plugin.getRoundManager().startRound();
            return true;
        }
        return false;
    }
}
