package at.koopro.nexrole.listeners;

import at.koopro.nexrole.manager.PlayerInfoManager;
import at.koopro.nexrole.roles.Role;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final PlayerInfoManager playerInfoManager;

    Player player;

    public ChatListener(PlayerInfoManager playerInfoManager) {
        this.playerInfoManager = playerInfoManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Role role = playerInfoManager.getPlayerRole(event.getPlayer());
        if (role != null) {
            String prefix = role.getPrefix();
            String name = event.getPlayer().getName();
            String message = event.getMessage();
            event.setFormat(prefix + " <" + name + "> " + message); // Include the role prefix in the chat format
        }
    }
}
