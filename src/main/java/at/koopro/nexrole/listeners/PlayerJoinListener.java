package at.koopro.nexrole.listeners;

import at.koopro.nexrole.manager.PlayerInfoManager;
import at.koopro.nexrole.roles.Role;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PlayerInfoManager playerInfoManager;

    public PlayerJoinListener(PlayerInfoManager playerInfoManager) {
        this.playerInfoManager = playerInfoManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerInfoManager.savePlayerInfo(player);

        // Get the player's role and update their display name
        Role role = playerInfoManager.getPlayerRole(player);
        if (role != null) {
            String displayName = role.getPrefix() + " " + player.getName();
            player.setDisplayName(displayName);
            player.setPlayerListName(displayName); // Optional: also update the name in the player list (tab list)
        }
    }
}
