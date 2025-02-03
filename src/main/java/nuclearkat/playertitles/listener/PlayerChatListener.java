package nuclearkat.playertitles.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import nuclearkat.playertitles.PlayerTitlesPlugin;
import nuclearkat.playertitles.display.TextDisplayHandler;
import nuclearkat.playertitles.util.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private final TextDisplayHandler handler;

    public PlayerChatListener(PlayerTitlesPlugin plugin) {
        this.handler = plugin.getTextDisplayHandler();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (handler.hasActiveDisplay(player.getUniqueId())) {
            String startingBracket = ColorUtil.convertLegacyColorCodes("<gray>[</gray>");
            String endingBracket = ColorUtil.convertLegacyColorCodes("<gray>]</gray> ");
            event.setMessage(startingBracket + ColorUtil.convertLegacyColorCodes(handler.getActiveTitle(player.getUniqueId()).getTagContents()) + endingBracket + event.getMessage());
        }
    }
}
