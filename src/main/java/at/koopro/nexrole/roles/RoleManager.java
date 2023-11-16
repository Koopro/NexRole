package at.koopro.nexrole.roles;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoleManager {

    private final JavaPlugin plugin;
    private final Map<String, Role> roles = new HashMap<>();

    public RoleManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadRoles();
    }

    private void loadRoles() {
        File rolesFile = new File(plugin.getDataFolder(), "roleconf.yml");
        if (!rolesFile.exists()) {
            plugin.saveResource("roleconf.yml", false);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(rolesFile);
        for (String key : config.getConfigurationSection("roles").getKeys(false)) {
            String prefix = config.getString("roles." + key + ".prefix");
            roles.put(key, new Role(key, prefix));
        }
    }

    public Role getRole(String roleName) {
        return roles.get(roleName);
    }

    public Set<String> getRoleNames() {
        return roles.keySet(); // This returns a Set of all role names
    }

    // No setPlayerRole method here
}
