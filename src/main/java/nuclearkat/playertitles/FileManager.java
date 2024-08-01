package nuclearkat.playertitles;

import nuclearkat.playertitles.title.Title;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private final PlayerTitlesPlugin plugin;
    private final File titlesFolder;

    public FileManager(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
        this.titlesFolder = new File(plugin.getDataFolder(), "Titles");
        initTitlesFolder();
    }

    private void initTitlesFolder() {
        if (!titlesFolder.exists()) {
            if (titlesFolder.mkdirs()) {
                plugin.getLogger().info("Created Titles folder: " + titlesFolder.getAbsolutePath());
            } else {
                plugin.getLogger().severe("Failed to create Titles folder: " + titlesFolder.getAbsolutePath());
            }
        }
    }

    public void saveTitleToFolder(Title title) {
        File file = new File(titlesFolder, title.getKey() + ".yml");
        try {
            title.saveToFile(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save title to file: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public void loadTitlesFromFolder() {
        File[] files = titlesFolder.listFiles((dir, name) -> name.endsWith(".yml"));

        if (files == null) {
            return;
        }

        for (File file : files) {
            try {
                Title title = Title.loadFromFile(file, plugin);
                String key = title.getKey();
                plugin.getTitleManager().addTitle(key, title);
                Bukkit.getLogger().info("Loaded title: " + key + " from " + file.getName() + " to memory!");
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to load title from file: " + file.getName());
                e.printStackTrace();
            }
        }
    }
}
