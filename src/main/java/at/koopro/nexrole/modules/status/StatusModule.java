package at.koopro.nexrole.modules.status;


import at.koopro.nexrole.manager.PlayerInfoManager;
import at.koopro.nexrole.modules.Module;
import at.koopro.nexrole.roles.Role;
import at.koopro.nexrole.roles.RoleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class StatusModule implements Module, CommandExecutor {

    private final JavaPlugin plugin;
    private final PlayerInfoManager playerInfoManager;
    private final RoleManager roleManager;
    private boolean isActive;

    public StatusModule(JavaPlugin plugin, PlayerInfoManager playerInfoManager, RoleManager roleManager) {
        this.plugin = plugin;
        this.playerInfoManager = playerInfoManager;
        this.roleManager = roleManager;
        this.isActive = false;
    }

    @Override
    public void activate() {
        isActive = true;
        plugin.getCommand("status").setExecutor(this);
    }

    @Override
    public void deactivate() {
        isActive = false;
        plugin.getCommand("status").setExecutor(null); // Remove the command executor
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public String getName() {
        return "Status";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!isActive) {
            sender.sendMessage("The status module is not active.");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            String roleName = args[1];
            Role role = roleManager.getRole(roleName);
            if (role == null) {
                player.sendMessage("Role not found.");
                return true;
            }
            playerInfoManager.setPlayerRole(player, role);
            updatePlayerDisplayName(player, role);
            player.sendMessage("Your role has been set to " + roleName);
        } else {
            player.sendMessage("Usage: /status set <roleName>");
        }

        return true;
    }

    private void updatePlayerDisplayName(Player player, Role role) {
        String displayName = role.getPrefix() + " " + player.getName();
        player.setDisplayName(displayName);
        player.setPlayerListName(displayName); // Optional: also update the name in the player list (tab list)
    }
}

