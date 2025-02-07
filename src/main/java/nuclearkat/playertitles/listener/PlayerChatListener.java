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

    public PlayerChatListener(PlayerTitlesPlugin plugin) {
        this.handler = plugin.getTextDisplayHandler();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!handler.hasActiveDisplay(playerUUID)) {
            return;
        }

        String playerName = player.getName();
        String message = event.getMessage();
        Title title = this.handler.getActiveTitle(playerUUID);
        String tagContents = title.getTagContents();

        String formattedMessage = String.format("%s %s: %s", tagContents, "<reset>" + playerName, message);

        event.setFormat(ColorUtil.convertLegacyColorCodes(formattedMessage));
    }
}
