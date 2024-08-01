package nuclearkat.playertitles.command;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenTitlesGuiCommand implements CommandExecutor {

    private final PlayerTitlesPlugin plugin;

    public OpenTitlesGuiCommand(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player player)) {
            return false;
        }

        plugin.getTitlesGUI().openNameTagGUI(player, 0);
        return true;
    }
}
