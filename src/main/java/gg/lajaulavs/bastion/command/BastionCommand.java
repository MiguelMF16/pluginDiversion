package gg.lajaulavs.bastion.command;

import gg.lajaulavs.bastion.BastionSolitarioPlugin;
import gg.lajaulavs.bastion.RoundManager;
import gg.lajaulavs.bastion.TerritoryManager;
import gg.lajaulavs.bastion.TeamManager;
import org.bukkit.Location;
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
        TerritoryManager territoryManager = plugin.getTerritoryManager();
        RoundManager roundManager = plugin.getRoundManager();
        TeamManager teamManager = plugin.getTeamManager();
        if (args[0].equalsIgnoreCase("asignar") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("Jugador no encontrado");
                return true;
            }
            roundManager.assign(target, args[2]);
            sender.sendMessage("Asignado " + target.getName() + " a " + args[2]);
            return true;
        }
        if (args[0].equalsIgnoreCase("setTeam") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("Jugador no encontrado");
                return true;
            }
            teamManager.setPlayerTeam(target, args[2]);
            sender.sendMessage("Equipo de " + target.getName() + " establecido a " + args[2]);
            return true;
        }
        if (args[0].equalsIgnoreCase("setLeader") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("Jugador no encontrado");
                return true;
            }
            teamManager.setLeader(args[2], target);
            sender.sendMessage("Líder del equipo " + args[2] + " es ahora " + target.getName());
            return true;
        }
        if (args[0].equalsIgnoreCase("marcarAtaque") && args.length == 2 && sender instanceof Player) {
            Player leader = (Player) sender;
            roundManager.markAttack(leader, args[1]);
            return true;
        }
        if (args[0].equalsIgnoreCase("atacar") && sender instanceof Player) {
            Player p = (Player) sender;
            roundManager.chooseAttack(p);
            return true;
        }
        if (args[0].equalsIgnoreCase("defender") && args.length == 2 && sender instanceof Player) {
            Player p = (Player) sender;
            roundManager.chooseDefense(p, args[1]);
            return true;
        }
        if (args[0].equalsIgnoreCase("marcarZona") && args.length == 3 && sender instanceof Player) {
            Player p = (Player) sender;
            String id = args[1];
            Location loc = p.getLocation();
            if (args[2].equalsIgnoreCase("esquina1")) {
                territoryManager.setCorner1(id, loc);
            } else if (args[2].equalsIgnoreCase("esquina2")) {
                territoryManager.setCorner2(id, loc);
            } else {
                sender.sendMessage("Debes indicar esquina1 o esquina2");
                return true;
            }
            territoryManager.saveTerritories();
            sender.sendMessage("Zona " + id + " actualizada");
            return true;
        }
        if (args[0].equalsIgnoreCase("startRound")) {
            roundManager.startRound();
            return true;
        }
        if (args[0].equalsIgnoreCase("forzarVictoria") && args.length == 2) {
            roundManager.forceWin(args[1]);
            return true;
        }
        if (args[0].equalsIgnoreCase("cancelarRonda")) {
            roundManager.cancelRound();
            return true;
        }
        return false;
    }
}
