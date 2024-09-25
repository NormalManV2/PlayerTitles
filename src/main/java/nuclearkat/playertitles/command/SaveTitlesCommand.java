package nuclearkat.playertitles.command;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SaveTitlesCommand implements CommandExecutor {

    private final PlayerTitlesPlugin plugin;

    public SaveTitlesCommand(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }

        if (!player.hasPermission("title.command.save_titles")){
            return false;
        }

        plugin.getTitleManager().saveTitlesToFolder();

        return true;
    }
}
