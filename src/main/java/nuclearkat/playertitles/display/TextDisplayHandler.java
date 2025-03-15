package nuclearkat.playertitles.display;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import nuclearkat.playertitles.title.Title;
import nuclearkat.playertitles.util.ColorUtil;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TextDisplayHandler {

    private final Map<UUID, TextDisplay> activeTextDisplays = new HashMap<>();
    private final Map<UUID, Title> activeTitles = new HashMap<>();
    private final PlayerTitlesPlugin plugin;

    public TextDisplayHandler(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    public void displayTitle(Player player, Title title) {
        World world = player.getWorld();
        Location location = player.getEyeLocation();

        String tagContents = ColorUtil.parsePlaceholders(player, title.getTagContents() + "<reset> ");

        TextDisplay textDisplay = world.spawn(location.clone(), TextDisplay.class, display -> {
            Vector3f offset = new Vector3f(0, 0.15f, 0);
            AxisAngle4f rotation = new AxisAngle4f();
            Vector3f scale = new Vector3f(0.75f, 0.75f, 0.75f);
            Transformation transformation = new Transformation(offset, rotation, scale, rotation);

            display.setText(tagContents + player.getDisplayName());
            display.setBillboard(Display.Billboard.CENTER);
            display.setBackgroundColor(Color.fromARGB(0x80333333));
            display.setCustomNameVisible(false);
            display.setPersistent(false);
            display.setSeeThrough(false);
            display.setShadowed(false);
            display.setInvulnerable(true);

            display.setTransformation(transformation);
        });

        player.sendMessage(ChatColor.GREEN + "You have just equipped the " + ColorUtil.parsePlaceholders(player, title.getDisplayName()) + ChatColor.GREEN + " title!");
        this.activeTitles.put(player.getUniqueId(), title);

        if (this.plugin.getTitleConfig().getBoolean("title.display.display-above-head")) {
            this.activeTextDisplays.put(player.getUniqueId(), textDisplay);
            player.addPassenger(textDisplay);
            return;
        }

        textDisplay.remove();
    }

    public void removeTextDisplays(UUID playerId) {
        TextDisplay textDisplay = this.activeTextDisplays.remove(playerId);
        if (textDisplay == null){
            return;
        }
        textDisplay.remove();
        this.activeTitles.remove(playerId);
    }

    public Map<UUID, TextDisplay> getActiveTextDisplays() {
        return Collections.unmodifiableMap(activeTextDisplays);
    }

    public Map<UUID, Title> getActiveTitles() {
        return Collections.unmodifiableMap(activeTitles);
    }

    public boolean hasActiveDisplay(UUID playerId){
        return this.activeTextDisplays.containsKey(playerId);
    }

    public boolean hasActiveTitle(UUID playerId){
        return this.activeTitles.containsKey(playerId);
    }

    public Title getActiveTitle(UUID playerId){
        if (!this.hasActiveTitle(playerId)){
            return null;
        }
        return this.activeTitles.get(playerId);
    }

    public TextDisplay getActiveDisplay(UUID playerId){
        if (!hasActiveDisplay(playerId)){
            return null;
        }
        return this.activeTextDisplays.get(playerId);
    }

    public void clearTitleEntries(){
        this.activeTitles.clear();
    }

    public void clearDisplayEntries(){
        for (TextDisplay display : this.activeTextDisplays.values()){
            display.remove();
        }
        this.activeTextDisplays.clear();
    }

    public void reload() {
        this.clearDisplayEntries();
        this.clearTitleEntries();
    }

}
