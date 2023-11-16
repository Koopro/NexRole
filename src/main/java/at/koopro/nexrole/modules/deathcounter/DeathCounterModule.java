package at.koopro.nexrole.modules.deathcounter;

import at.koopro.nexrole.manager.PlayerInfoManager;
import at.koopro.nexrole.modules.Module;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;

public class DeathCounterModule implements Module, Listener {

    private final PlayerInfoManager playerInfoManager;
    private final JavaPlugin plugin;
    private Objective deathObjective;
    private boolean isActive;

    public DeathCounterModule(JavaPlugin plugin, PlayerInfoManager playerInfoManager) {
        this.plugin = plugin;
        this.playerInfoManager = playerInfoManager;
        this.isActive = false;
    }

    @Override
    public void activate() {
        this.isActive = true;
        setupScoreboard();
        initializePlayersDeathCount();
    }

    @Override
    public void deactivate() {
        this.isActive = false;
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public String getName() {
        return "DeathCounter";
    }

    private void setupScoreboard() {
        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();
        deathObjective = scoreboard.getObjective("deaths");

        if (deathObjective == null) {
            deathObjective = scoreboard.registerNewObjective("deaths", "dummy", "Deaths");
            deathObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        }
    }

    private void initializePlayersDeathCount() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int deaths = playerInfoManager.getPlayerDeathCount(player);
            updateDeathCount(player, deaths);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!this.isActive) return;

        Player player = event.getEntity();
        plugin.getLogger().info("Player death detected: " + player.getName()); // Use logger
        int deaths = playerInfoManager.incrementPlayerDeathCount(player);
        updateDeathCount(player, deaths);
    }

    private void updateDeathCount(Player player, int deaths) {
        Score score = deathObjective.getScore(player.getName());
        score.setScore(deaths);
    }
}
