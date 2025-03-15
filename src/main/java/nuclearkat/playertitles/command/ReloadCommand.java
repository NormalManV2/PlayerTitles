package nuclearkat.playertitles.command;

import nuclearkat.playertitles.PlayerTitlesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    private final PlayerTitlesPlugin plugin;

    public ReloadCommand(PlayerTitlesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)){
            return false;
        }

        if (!player.hasPermission("titles.command.reload")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You do not have permission to use this command!"));
            return true;
        }

        this.plugin.reload();

        return true;
    }
}
