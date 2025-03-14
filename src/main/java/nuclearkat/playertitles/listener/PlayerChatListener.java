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
    private final TextDisplayHandler handler;
    private final PlayerTitlesPlugin plugin;

    public PlayerChatListener(PlayerTitlesPlugin plugin) {
        this.handler = plugin.getTextDisplayHandler();
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        if (!plugin.getTitleConfig().getBoolean("title.display.display-in-chat")) {
            return;
        }

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!handler.hasActiveDisplay(playerUUID)) {
            return;
        }

        String playerName = player.getName();
        String message = event.getMessage();
        Title title = this.handler.getActiveTitle(playerUUID);
        String tagContents = ColorUtil.convertLegacyColorCodes(title.getTagContents());

        String formattedMessage = String.format("%s %s: %s", tagContents + "<reset>", playerName, message);

        event.setFormat(formattedMessage);
    }
}
