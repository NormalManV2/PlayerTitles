package nuclearkat.playertitles.title;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import nuclearkat.playertitles.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Title {

    private final String key;
    private final String displayName;
    private final String tagContents;
    private final List<String> lore;
    private final String permission;
    private final NamespacedKey titleContentsKey;
    private final NamespacedKey titleKey;

    public Title(String key, String displayName, String tagContents, String permission, NamespacedKey titleKey, NamespacedKey titleContentsKey, String... lore) {
        this.key = key;
        this.displayName = displayName;
        this.tagContents = tagContents;
        this.lore = new ArrayList<>();
        this.lore.addAll(Arrays.asList(lore));
        this.permission = permission;
        this.titleContentsKey = titleContentsKey;
        this.titleKey = titleKey;
    }

    public String getKey() {
        return this.key;
    }

    public String getTagContents() {
        return this.tagContents;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public List<String> getLore() {
        return Collections.unmodifiableList(this.lore);
    }

    public String getPermission() {
        return this.permission;
    }

    public ItemStack build() {
        ItemStack builtItem = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = builtItem.getItemMeta();
        itemMeta.setDisplayName(ColorUtil.convertLegacyColorCodes(displayName));
        List<String> formattedLore = new ArrayList<>();

        for (String lore : this.lore) {
            formattedLore.add(ColorUtil.convertLegacyColorCodes(lore));
        }

        itemMeta.setLore(formattedLore);
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        dataContainer.set(titleContentsKey, PersistentDataType.STRING, tagContents);
        dataContainer.set(titleKey, PersistentDataType.STRING, key);

        builtItem.setItemMeta(itemMeta);

        return builtItem;
    }

    public void removeFromFile(File file) throws IOException {
        if (!file.delete()) throw new IOException("Failed to delete file: " + file.getAbsolutePath());
    }

    public void saveToFile(File file) throws IOException {
        YamlConfiguration config = new YamlConfiguration();

        config.set("key", key);
        config.set("displayName", displayName);
        config.set("tagContents", tagContents);
        config.set("lore", lore);
        config.set("permission", permission);

        File directory = file.getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create directory: " + directory.getAbsolutePath());
        }

        config.save(file);
        Bukkit.getLogger().info("Title saved to file: " + file.getAbsolutePath());
    }

    public static Title loadFromFile(File file, PlayerTitlesPlugin plugin) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = config.getString("key");
        String displayName = config.getString("displayName");
        String tagContents = config.getString("tagContents");
        List<String> lore = config.getStringList("lore");
        String permission = config.getString("permission");

        NamespacedKey titleKey = plugin.getTitleKey();
        NamespacedKey titleContentsKey = plugin.getTitleContentsKey();

        return new Title(key, displayName, tagContents, permission, titleKey, titleContentsKey, lore.toArray(new String[0]));
    }
}
