package at.koopro.nexrole.commands;

import at.koopro.nexrole.manager.ModuleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ModuleTabCompleter implements TabCompleter {

    private final ModuleManager moduleManager;

    public ModuleTabCompleter(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            // First argument: "activate" or "deactivate"
            suggestions.add("activate");
            suggestions.add("deactivate");
        } else if (args.length == 2) {
            // Second argument: list of module names or "all"
            suggestions.addAll(moduleManager.getModuleNames());
            suggestions.add("all");
        }

        return suggestions;
    }
}
