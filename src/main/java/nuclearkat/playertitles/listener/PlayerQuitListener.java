package nuclearkat.playertitles.listener;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final PlayerTitlesPlugin plugin;

    public PlayerQuitListener(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        this.plugin.getTextDisplayHandler().removeTextDisplays(playerId);
    }
}
