package at.koopro.nexrole;

import at.koopro.nexrole.commands.*;
import at.koopro.nexrole.listeners.*;
import at.koopro.nexrole.manager.*;
import at.koopro.nexrole.modules.deathcounter.DeathCounterModule;
import at.koopro.nexrole.modules.status.StatusCommandTabCompleter;
import at.koopro.nexrole.modules.status.StatusModule;
import at.koopro.nexrole.modules.test.TestModule;
import at.koopro.nexrole.roles.RoleManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NexRole extends JavaPlugin {

    private ModuleManager moduleManager;
    private ConfigurationManager configManager;
    private DeathCounterModule deathCounterModule;
    private PlayerInfoManager playerInfoManager;
    private StatusModule statusModule; // Add StatusModule field

    @Override
    public void onEnable() {
        getLogger().info("NexRole has been enabled!");

        configManager = new ConfigurationManager(this);
        configManager.loadConfigs();

        RoleManager roleManager = new RoleManager(this);
        this.playerInfoManager = new PlayerInfoManager(this, roleManager);

        RoleCommandExecutor roleCommandExecutor = new RoleCommandExecutor(roleManager, playerInfoManager);
        RoleCommandTabCompleter roleCommandTabCompleter = new RoleCommandTabCompleter(roleManager);

        moduleManager = new ModuleManager(this, configManager);

        registerModules(); // Register all modules including the new StatusModule

        // Set up command executors and tab completers
        setupCommands(roleCommandExecutor, roleCommandTabCompleter);

        // Register event listeners
        registerEventListeners();
    }

    private void registerModules() {
        TestModule testModule = new TestModule();
        moduleManager.registerModule(testModule);
        this.getCommand("testcommand").setExecutor(testModule);

        deathCounterModule = new DeathCounterModule(this, playerInfoManager);
        moduleManager.registerModule(deathCounterModule);
        deathCounterModule.activate();

        statusModule = new StatusModule(this, playerInfoManager, new RoleManager(this)); // Initialize StatusModule
        moduleManager.registerModule(statusModule); // Register StatusModule
        // Optionally activate the module based on configuration or conditions
    }

    private void setupCommands(RoleCommandExecutor roleCommandExecutor, RoleCommandTabCompleter roleCommandTabCompleter) {
        getCommand("setrole").setExecutor(roleCommandExecutor);
        getCommand("setrole").setTabCompleter(roleCommandTabCompleter);
        getCommand("getrole").setExecutor(roleCommandExecutor);

        ModuleCommandExecutor commandExecutor = new ModuleCommandExecutor(moduleManager);
        this.getCommand("modules").setExecutor(commandExecutor);
        this.getCommand("modules").setTabCompleter(new ModuleTabCompleter(moduleManager));

        // Set up command and tab completer for the StatusModule
        StatusCommandTabCompleter statusCommandTabCompleter = new StatusCommandTabCompleter(new RoleManager(this));
        getCommand("status").setExecutor(statusModule);
        getCommand("status").setTabCompleter(statusCommandTabCompleter);
    }

    private void registerEventListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerInfoManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(playerInfoManager), this);
        getServer().getPluginManager().registerEvents(deathCounterModule, this);
        getServer().getPluginManager().registerEvents(new ChatListener(playerInfoManager), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("NexRole has been disabled.");
    }
}
