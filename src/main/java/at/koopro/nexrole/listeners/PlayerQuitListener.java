package at.koopro.nexrole.listeners;

import at.koopro.nexrole.manager.PlayerInfoManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PlayerInfoManager playerInfoManager;

    public PlayerQuitListener(PlayerInfoManager playerInfoManager) {
        this.playerInfoManager = playerInfoManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerInfoManager.savePlayerInfo(event.getPlayer());
    }
}
