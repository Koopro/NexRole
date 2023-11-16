package at.koopro.nexrole.modules.test;

import at.koopro.nexrole.modules.Module;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestModule implements Module, CommandExecutor {

    private boolean active = false;

    @Override
    public void activate() {
        active = true;
        // Additional activation logic (if needed)
    }

    @Override
    public void deactivate() {
        active = false;
        // Additional deactivation logic (if needed)
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getName() {
        return "TestModule";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!active) {
            sender.sendMessage("This command is currently disabled.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("testcommand")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("Test command executed!");
                return true;
            }
        }
        return false;
    }
}
