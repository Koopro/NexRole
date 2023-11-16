package at.koopro.nexrole.manager;

import at.koopro.nexrole.roles.Role;
import at.koopro.nexrole.roles.RoleManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerInfoManager {

    private final JavaPlugin plugin;
    private final File playerDataFolder;
    private final RoleManager roleManager;
    public PlayerInfoManager(JavaPlugin plugin, RoleManager roleManager) {
        this.plugin = plugin;
        playerDataFolder = new File(plugin.getDataFolder(), "playerdata");
        this.roleManager = roleManager;
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    public void savePlayerInfo(Player player) {
        File playerFile = getFileForPlayer(player);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        config.set("name", player.getName());
        config.set("uuid", player.getUniqueId().toString());
        config.set("location.world", player.getWorld().getName());
        config.set("location.x", player.getLocation().getX());
        config.set("location.y", player.getLocation().getY());
        config.set("location.z", player.getLocation().getZ());
        config.set("location.dimension", player.getWorld().getEnvironment().toString());
        config.set("location.biome", player.getLocation().getBlock().getBiome().toString());

        saveConfig(config, playerFile);
    }

    public int incrementPlayerDeathCount(Player player) {
        File playerFile = getFileForPlayer(player);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        int deaths = config.getInt("deaths", 0);
        deaths++;
        System.out.println("Incrementing death count in PlayerInfoManager for " + player.getName()); // Debugging line
        config.set("deaths", deaths);
        saveConfig(config, playerFile);
        return deaths;
    }

    public int getPlayerDeathCount(Player player) {
        File playerFile = getFileForPlayer(player);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        return config.getInt("deaths", 0);
    }
    // Inside PlayerInfoManager class

    public void setPlayerRole(Player player, Role role) {
        File playerFile = getFileForPlayer(player);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        config.set("role", role.getName());
        saveConfig(config, playerFile);
    }

    public Role getPlayerRole(Player player) {
        File playerFile = getFileForPlayer(player);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        String roleName = config.getString("role");
        return roleManager.getRole(roleName); // Ensure roleManager is accessible
    }


    private File getFileForPlayer(Player player) {
        return new File(playerDataFolder, player.getUniqueId() + ".yml");
    }

    private void saveConfig(YamlConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
