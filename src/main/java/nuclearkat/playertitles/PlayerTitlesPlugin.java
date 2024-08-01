package nuclearkat.playertitles;

import nuclearkat.playertitles.command.DisableTitleCommand;
import nuclearkat.playertitles.command.OpenTitlesGuiCommand;
import nuclearkat.playertitles.command.SaveTitlesCommand;
import nuclearkat.playertitles.command.TitleCreationCommand;
import nuclearkat.playertitles.display.TextDisplayHandler;
import nuclearkat.playertitles.gui.TitlesGUI;
import nuclearkat.playertitles.listener.PlayerQuitListener;
import nuclearkat.playertitles.listener.TitlesGuiListener;
import nuclearkat.playertitles.title.TitleManager;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerTitlesPlugin extends JavaPlugin {

    private FileManager fileManager;
    private TitleManager titleManager;
    private TitlesGUI titlesGUI;
    private TextDisplayHandler textDisplayHandler;
    private final NamespacedKey titleContentsKey = new NamespacedKey(this, "titleContents");
    private final NamespacedKey titleKey = new NamespacedKey(this, "title");

    @Override
    public void onEnable() {
        initComponents();
        fileManager.loadTitlesFromFolder();
        initCommands();
        initListeners();
    }

    private void initComponents(){
        this.fileManager = new FileManager(this);
        this.titleManager = new TitleManager(this);
        this.titlesGUI = new TitlesGUI(this);
        this.textDisplayHandler = new TextDisplayHandler();
    }

    private void initCommands() {
        getCommand("createtitle").setExecutor(new TitleCreationCommand(this));
        getCommand("titles").setExecutor(new OpenTitlesGuiCommand(this));
        getCommand("disabletitle").setExecutor(new DisableTitleCommand(this));
        getCommand("savetitles").setExecutor(new SaveTitlesCommand(this));
    }

    private void initListeners() {
        getServer().getPluginManager().registerEvents(new TitlesGuiListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
    }

    @Override
    public void onDisable() {

    }

    public TextDisplayHandler getTextDisplayHandler() {
        if (textDisplayHandler == null){
            textDisplayHandler = new TextDisplayHandler();
        }

        return textDisplayHandler;
    }

    public TitleManager getTitleManager() {
        if (titleManager == null){
            titleManager = new TitleManager(this);
        }

        return titleManager;
    }

    public TitlesGUI getTitlesGUI() {
        if (titlesGUI == null){
            titlesGUI = new TitlesGUI(this);
        }

        return titlesGUI;
    }

    public FileManager getFileManager() {
        if (fileManager == null){
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

}
