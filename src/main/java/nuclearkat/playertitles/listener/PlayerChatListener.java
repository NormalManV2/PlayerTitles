package nuclearkat.playertitles.listener;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import nuclearkat.playertitles.display.TextDisplayHandler;
import nuclearkat.playertitles.title.Title;
import nuclearkat.playertitles.util.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class PlayerChatListener implements Listener {
    private final PlayerTitlesPlugin plugin;

    public PlayerChatListener(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        if (!plugin.getTitleConfig().getBoolean("title.display.display-in-chat")) {
            return;
        }

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!plugin.getTextDisplayHandler().hasActiveTitle(playerUUID)) {
            return;
        }

        String playerName = player.getName();
        String message = event.getMessage();
        Title title = this.plugin.getTextDisplayHandler().getActiveTitle(playerUUID);
        String tagContents = ColorUtil.parsePlaceholders(player, title.getTagContents() + "<reset> ");

        String formattedMessage = String.format("%s%s: %s", tagContents, playerName, message);

        event.setFormat(formattedMessage);
    }
}
