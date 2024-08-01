package nuclearkat.playertitles.listener;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PlayerTitlesPlugin plugin;

    public PlayerQuitListener(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.plugin.getTextDisplayHandler().removeTextDisplays(player.getUniqueId());
    }

}
