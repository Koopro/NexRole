package at.koopro.nexrole.commands;

import at.koopro.nexrole.roles.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RoleCommandTabCompleter implements TabCompleter {

    private final RoleManager roleManager;

    public RoleCommandTabCompleter(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("setrole") && args.length == 1){
            List<String> list = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()){
                list.add(p.getName());
            }
            return list;
        }
        if (command.getName().equalsIgnoreCase("setrole") && args.length == 2) {
            // Add role names to suggestions
            suggestions.addAll(roleManager.getRoleNames());
        }
        return suggestions;
    }
}
