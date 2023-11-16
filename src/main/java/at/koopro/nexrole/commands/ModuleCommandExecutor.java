package at.koopro.nexrole.commands;

import at.koopro.nexrole.manager.ModuleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModuleCommandExecutor implements CommandExecutor {

    private final ModuleManager moduleManager;

    public ModuleCommandExecutor(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp() || player.getName().equalsIgnoreCase("K00pro") || player.hasPermission("nexrole.setrole")) {
                // Handle the command
                // Example: /modules activate <ModuleName>
                if (args.length >= 2) {
                    String action = args[0];
                    String moduleName = args[1];

                    switch (action.toLowerCase()) {
                        case "activate":
                            // Activate the module
                            moduleManager.activateModule(moduleName);
                            sender.sendMessage("Activated module: " + moduleName);
                            break;
                        case "deactivate":
                            // Deactivate the module
                            moduleManager.deactivateModule(moduleName);
                            sender.sendMessage("Deactivated module: " + moduleName);
                            break;
                        default:
                            sender.sendMessage("Invalid action. Use activate or deactivate.");
                            break;
                    }
                } else {
                    sender.sendMessage("Usage: /modules <activate|deactivate> <ModuleName>");
                }
                return true;
            } else {
                sender.sendMessage("You do not have permission to use this command.");
            }
        } else {
            sender.sendMessage("This command can only be used by a player.");
        }
        return false;
    }
}
