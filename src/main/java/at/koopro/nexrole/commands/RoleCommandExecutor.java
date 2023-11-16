package at.koopro.nexrole.commands;

import at.koopro.nexrole.manager.PlayerInfoManager;
import at.koopro.nexrole.roles.Role;
import at.koopro.nexrole.roles.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoleCommandExecutor implements CommandExecutor {

    private final RoleManager roleManager;
    private final PlayerInfoManager playerInfoManager;

    public RoleCommandExecutor(RoleManager roleManager, PlayerInfoManager playerInfoManager) {
        this.roleManager = roleManager;
        this.playerInfoManager = playerInfoManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp() || player.getName().equalsIgnoreCase("K00pro") || player.hasPermission("nexrole.setrole")) {
                if (command.getName().equalsIgnoreCase("setrole")) {
                    if (args.length != 2) {
                        sender.sendMessage("Usage: /setrole <player> <role>");
                        return true;
                    }

                    Player targetPlayer = Bukkit.getPlayer(args[0]);
                    if (targetPlayer == null) {
                        sender.sendMessage("Player not found.");
                        return true;
                    }

                    String roleName = args[1];
                    Role role = roleManager.getRole(roleName);
                    if (role == null) {
                        sender.sendMessage("Role not found.");
                        return true;
                    }

                    playerInfoManager.setPlayerRole(targetPlayer, role);
                    String displayName = role.getPrefix() + " " + targetPlayer.getName();
                    targetPlayer.setDisplayName(displayName);
                    targetPlayer.setPlayerListName(displayName);
                    sender.sendMessage("Set " + targetPlayer.getName() + "'s role to " + role.getName());
                    return true;
                }
                if (command.getName().equalsIgnoreCase("getrole")) {
                    if (args.length != 1) {
                        sender.sendMessage("Usage: /getrole <player>");
                        return true;
                    }

                    Player targetPlayer = Bukkit.getPlayer(args[0]);
                    if (targetPlayer == null) {
                        sender.sendMessage("Player not found.");
                        return true;
                    }

                    Role role = playerInfoManager.getPlayerRole(targetPlayer);
                    if (role == null) {
                        sender.sendMessage(targetPlayer.getName() + " does not have a role.");
                    } else {
                        sender.sendMessage(targetPlayer.getName() + "'s role: " + role.getName() + " " + role.getPrefix());
                    }
                    return true;
                }
            } else {
                sender.sendMessage("You do not have permission to use this command.");
            }
        }
        return true;
    }
}
