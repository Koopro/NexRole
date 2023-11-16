package at.koopro.nexrole.listeners;

import at.koopro.nexrole.modules.deathcounter.DeathCounterModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEventListener implements Listener {

    private final DeathCounterModule deathCounterModule;

    public DeathEventListener(DeathCounterModule deathCounterModule) {
        this.deathCounterModule = deathCounterModule;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        deathCounterModule.onPlayerDeath(event);
    }
}
