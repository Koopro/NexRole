package at.koopro.nexrole.modules.status;

import at.koopro.nexrole.roles.RoleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class StatusCommandTabCompleter implements TabCompleter {

    private final RoleManager roleManager;

    public StatusCommandTabCompleter(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("status")) {
            if (args.length == 1) {
                // First argument: "set" or "remove"
                suggestions.add("set");
            } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
                // Second argument: list of role names
                suggestions.addAll(roleManager.getRoleNames());
            }
        }

        return suggestions;
    }
}
