package nuclearkat.playertitles.title;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TitleManager {

    private final PlayerTitlesPlugin plugin;
    private final Map<String, Title> titles = new HashMap<>();

    public TitleManager(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    public void saveTitlesToFolder() {
        for (Title title : titles.values()) {
            plugin.getFileManager().saveTitleToFolder(title);
        }
    }

    public void createTitle(String key, String displayName, String tagContents, String permission, NamespacedKey titleKey, NamespacedKey titleContentsKey, String... lore) {
        Title title = new Title(key, displayName, tagContents, permission, titleKey, titleContentsKey, lore);
        this.addTitle(key, title);
        plugin.getFileManager().saveTitleToFolder(title);
    }

    public void addTitle(String titleKey, Title title) {
        if (title == null) {
            return;
        }
        this.titles.put(titleKey, title);
        plugin.getTitlesGUI().updateTitlesList();
    }

    public void removeTitle(String titleKey) {
        Title title = this.titles.remove(titleKey);
        plugin.getFileManager().removeTitleFromFolder(title);
        plugin.getTitlesGUI().updateTitlesList();
    }

    public Title getTitle(String titleKey) {
        return this.titles.get(titleKey);
    }

    public void applyTitle(String titleKey, Player player) {
        Title title = this.titles.get(titleKey);
        if (title == null) {
            plugin.getLogger().severe("Title is null or cannot be found!");
            return;
        }
        plugin.getTextDisplayHandler().removeTextDisplays(player.getUniqueId());
        plugin.getTextDisplayHandler().displayTitle(player, title);
    }

    public Map<String, Title> getTitlesMap() {
        return Collections.unmodifiableMap(this.titles);
    }

    public void reload() {
        this.titles.clear();
    }

}
