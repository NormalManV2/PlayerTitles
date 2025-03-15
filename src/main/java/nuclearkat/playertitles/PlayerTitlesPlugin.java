package nuclearkat.playertitles;

import nuclearkat.playertitles.command.*;
import nuclearkat.playertitles.display.TextDisplayHandler;
import nuclearkat.playertitles.gui.TitlesGUI;
import nuclearkat.playertitles.listener.PlayerChatListener;
import nuclearkat.playertitles.listener.PlayerKickListener;
import nuclearkat.playertitles.listener.PlayerQuitListener;
import nuclearkat.playertitles.listener.TitlesGuiListener;
import nuclearkat.playertitles.listener.WorldChangeListener;
import nuclearkat.playertitles.title.TitleManager;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class PlayerTitlesPlugin extends JavaPlugin {

    private FileManager fileManager;
    private TitleManager titleManager;
    private TitlesGUI titlesGUI;
    private TextDisplayHandler textDisplayHandler;
    private final NamespacedKey titleContentsKey = new NamespacedKey(this, "titleContents");
    private final NamespacedKey titleKey = new NamespacedKey(this, "title");
    private YamlConfiguration titleConfig;
    private final Path configPath = getDataFolder().toPath().resolve("config.yml");


    @Override
    public void onEnable() {
        initComponents();
        this.fileManager.loadTitlesFromFolder();
        saveDefaultConfig();
        initConfig();
        initCommands();
        initListeners();
    }

    private void initConfig() {
        if (Files.notExists(this.configPath)) {
            try {
                Files.createDirectories(this.configPath.getParent());
                Files.copy(getResource("config.yml"), this.configPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                getLogger().severe("Could not create config file: " + e.getMessage());
            }
        }
        this.titleConfig = YamlConfiguration.loadConfiguration(this.configPath.toFile());
    }


    private void initComponents() {
        this.fileManager = new FileManager(this);
        this.titleManager = new TitleManager(this);
        this.titlesGUI = new TitlesGUI(this);
        this.textDisplayHandler = new TextDisplayHandler(this);
    }

    private void initCommands() {
        getCommand("createtitle").setExecutor(new TitleCreationCommand(this));
        getCommand("titles").setExecutor(new OpenTitlesGuiCommand(this));
        getCommand("disabletitle").setExecutor(new DisableTitleCommand(this));
        getCommand("savetitles").setExecutor(new SaveTitlesCommand(this));
        getCommand("deleteTitle").setExecutor(new DeleteTitleCommand(this));
        getCommand("reloadTitles").setExecutor(new ReloadCommand(this));
    }

    private void initListeners() {
        getServer().getPluginManager().registerEvents(new TitlesGuiListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new WorldChangeListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerKickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
    }

    @Override
    public void onDisable() {
        this.textDisplayHandler.clearDisplayEntries();
        this.textDisplayHandler.clearTitleEntries();
        this.titleManager.saveTitlesToFolder();
    }

    public TextDisplayHandler getTextDisplayHandler() {
        if (textDisplayHandler == null) {
            textDisplayHandler = new TextDisplayHandler(this);
        }

        return textDisplayHandler;
    }

    public TitleManager getTitleManager() {
        if (titleManager == null) {
            titleManager = new TitleManager(this);
        }

        return titleManager;
    }

    public TitlesGUI getTitlesGUI() {
        if (titlesGUI == null) {
            titlesGUI = new TitlesGUI(this);
        }

        return titlesGUI;
    }

    public FileManager getFileManager() {
        if (fileManager == null) {
            fileManager = new FileManager(this);
        }

        return fileManager;
    }

    public NamespacedKey getTitleKey() {
        return titleKey;
    }

    public NamespacedKey getTitleContentsKey() {
        return titleContentsKey;
    }

    public YamlConfiguration getTitleConfig() {
        return this.titleConfig;
    }

    public Path getConfigPath() {
        return this.configPath;
    }

    public void reload() {
        this.onDisable();
        this.onEnable();
    }

}
