package at.koopro.nexrole.manager;

import at.koopro.nexrole.NexRole;
import at.koopro.nexrole.modules.Module;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
    private final Map<String, Module> modules = new HashMap<>();
    private final ConfigurationManager configManager;

    public ModuleManager(NexRole nexRole, ConfigurationManager configManager) {
        this.configManager = configManager;
    }

    public void registerModule(Module module) {
        modules.put(module.getName(), module);
        // Initialize module state based on the saved configuration
        FileConfiguration config = configManager.getModulesConfig();
        boolean isEnabled = config.getBoolean(module.getName() + ".enabled", false);
        if (isEnabled) {
            module.activate();
        }
    }

    public void activateModule(String name) {
        Module module = modules.get(name);
        if (module != null && !module.isActive()) {
            module.activate();
            updateModuleStateInConfig(name, true);
        }
    }

    public void deactivateModule(String name) {
        Module module = modules.get(name);
        if (module != null && module.isActive()) {
            module.deactivate();
            updateModuleStateInConfig(name, false);
        }
    }

    private void updateModuleStateInConfig(String moduleName, boolean isActive) {
        configManager.getModulesConfig().set(moduleName + ".enabled", isActive);
        configManager.saveModulesConfig();
    }

    public void activateAll() {
        for (Module module : modules.values()) {
            if (!module.isActive()) {
                module.activate();
            }
        }
        updateAllModuleStatesInConfig(true);
    }

    public void deactivateAll() {
        for (Module module : modules.values()) {
            if (module.isActive()) {
                module.deactivate();
            }
        }
        updateAllModuleStatesInConfig(false);
    }

    private void updateAllModuleStatesInConfig(boolean isActive) {
        for (String moduleName : modules.keySet()) {
            configManager.getModulesConfig().set(moduleName + ".enabled", isActive);
        }
        configManager.saveModulesConfig();
    }

    public List<String> getModuleNames() {
        return new ArrayList<>(modules.keySet());
    }

    public Module getModule(String name) {
        return modules.get(name);
    }

    public boolean isModuleActive(String name) {
        Module module = modules.get(name);
        return module != null && module.isActive();
    }

    public Map<String, Module> getModules() {
        return modules;
    }
}
