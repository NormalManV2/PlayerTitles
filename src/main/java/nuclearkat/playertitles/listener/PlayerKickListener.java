package nuclearkat.playertitles.listener;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.UUID;

public class PlayerKickListener implements Listener {

    private final PlayerTitlesPlugin plugin;

    public PlayerKickListener(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        this.plugin.getTextDisplayHandler().removeTextDisplays(playerId);
    }

}
