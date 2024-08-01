package nuclearkat.playertitles.display;

import nuclearkat.playertitles.title.Title;
import nuclearkat.playertitles.util.ColorUtil;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TextDisplayHandler {

    private final Map<UUID, TextDisplay> activeTextDisplays = new HashMap<>();

    public void displayTitle(Player player, Title title) {
        World world = player.getWorld();
        Location location = player.getEyeLocation();
        String titleContents = ColorUtil.convertLegacyColorCodes(title.getTagContents());

        TextDisplay textDisplay = world.spawn(location.clone(), TextDisplay.class);

        Vector3f offset = new Vector3f(0, 0.15f, 0);
        AxisAngle4f rotation = new AxisAngle4f();
        Vector3f scale = new Vector3f(0.75f, 0.75f, 0.75f);
        Transformation transformation = new Transformation(offset, rotation, scale, rotation);

        textDisplay.setText(ColorUtil.convertLegacyColorCodes(titleContents));
        textDisplay.setBillboard(Display.Billboard.CENTER);
        textDisplay.setBackgroundColor(Color.fromARGB(0x80333333));
        textDisplay.setCustomNameVisible(false);
        textDisplay.setPersistent(false);
        textDisplay.setSeeThrough(false);
        textDisplay.setShadowed(false);
        textDisplay.setInvulnerable(true);

        textDisplay.setTransformation(transformation);

        this.activeTextDisplays.put(player.getUniqueId(), textDisplay);
        player.sendMessage(ChatColor.GREEN + "You have just equipped the " + ColorUtil.convertLegacyColorCodes(title.getDisplayName()) + ChatColor.GREEN + " title!");
        player.addPassenger(textDisplay);
    }

    public void removeTextDisplays(UUID playerId) {
        TextDisplay textDisplay = this.activeTextDisplays.remove(playerId);
        if (textDisplay == null){
            return;
        }
        textDisplay.remove();
    }
}
