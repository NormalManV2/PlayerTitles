package nuclearkat.playertitles.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ColorUtil {

    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer
            .builder()
            .character('§')
            .hexCharacter('#')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static String convertLegacyColorCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', LEGACY_SERIALIZER.serialize(MINI_MESSAGE.deserialize(text)));
    }

    public static String parsePlaceholders(Player player, String text) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            text = PlaceholderAPI.setPlaceholders(player, text);
        }
        return convertLegacyColorCodes(text);
    }
}
