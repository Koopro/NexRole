package at.koopro.nexrole.manager;

import at.koopro.nexrole.NexRole;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationManager {

    private final NexRole plugin;
    private File modulesFile;
    private FileConfiguration modulesConfig;

    public ConfigurationManager(NexRole plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        createModuleConfig();
    }

    private void createModuleConfig() {
        modulesFile = new File(plugin.getDataFolder(), "modules.yml");
        if (!modulesFile.exists()) {
            modulesFile.getParentFile().mkdirs();
            plugin.saveResource("modules.yml", false);
        }
        modulesConfig = YamlConfiguration.loadConfiguration(modulesFile);
    }

    public FileConfiguration getModulesConfig() {
        return modulesConfig;
    }

    public void saveModulesConfig() {
        try {
            modulesConfig.save(modulesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
