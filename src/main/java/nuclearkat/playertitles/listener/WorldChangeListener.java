package nuclearkat.playertitles.listener;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import nuclearkat.playertitles.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.UUID;

public class WorldChangeListener implements Listener {

    private final PlayerTitlesPlugin plugin;

    public WorldChangeListener(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onWorldSwitch(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (!plugin.getTextDisplayHandler().hasActiveDisplay(playerId)){
            return;
        }

        Title title = plugin.getTextDisplayHandler().getActiveTitle(playerId);
        plugin.getTextDisplayHandler().removeTextDisplays(playerId);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            plugin.getTextDisplayHandler().displayTitle(player, title);
        }, 20);

    }
}
